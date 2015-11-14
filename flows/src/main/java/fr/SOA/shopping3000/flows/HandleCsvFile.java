package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.business.Product;
import fr.SOA.shopping3000.flows.utils.Database;
import fr.SOA.shopping3000.flows.utils.OrderWriterJson;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.Map;

import static fr.SOA.shopping3000.flows.utils.Endpoints.*;

public class HandleCsvFile extends RouteBuilder {
    private static final long BATCH_TIME_OUT = 3000L;
    private static final int MAX_RECORDS = 900;
    //public static Map<String,Order> orders = new HashMap<String,Order>();
    //public static String currentId = "";
    @Override
    public void configure() throws Exception {

        from(CSV_INPUT_DIRECTORY)
                //.log("Processing ${file:id}")
                .log("  Loading the file as a CSV document")
                .unmarshal(buildCsvFormat())// Body is now a List(Map("navn" -> ..., ...), ...)
                .process(newOrderCreation)
                .log("Create new order ${header.orderId}")
                .log("  Splitting the content of the file into atomic lines")
                .split(body())
                .log("  Transforming a CSV line into a Product")
                .process(csv2product)
                .log("  Transferring to the route that handle a given product")
                //.setProperty("item", body())
                .to(HANDLE_ITEM)
                //process each item to add price
                //item good item, not errors
                //.process(addTotalPrice)
                .aggregate(constant(true), batchAggregationStrategy())
                .completionPredicate(batchSizePredicate())
                .completionTimeout(BATCH_TIME_OUT)
                //.setProperty("order", newOrder)
                //.setProperty("totPrice", simple(newOrder.getTotPrice() + ""))
                .bean(OrderWriterJson.class, "writeJson(${body},${header.totPrice},${header.orderId})")
                .to(CSV_OUTPUT_DIRECTORY + "?fileName=output${header.orderId}.txt")
        ;
    }

    private AggregationStrategy batchAggregationStrategy( ) {
        return new ArrayListAggregationStrategy();
    }

    public Predicate batchSizePredicate() {
        return new BatchSizePredicate(MAX_RECORDS);
    }

    // transform a CSV file delimited by commas, skipping the headers and producing a Map as output
    private static CsvDataFormat buildCsvFormat() {
        CsvDataFormat format = new CsvDataFormat();
        format.setDelimiter(",");
        format.setSkipHeaderRecord(true);
        format.setUseMaps(true);
        return format;
    }

    // Process a map<String -> Object> into a person
    /*private static Processor addTotalPrice = new Processor() {
        public void process(Exchange exchange) throws Exception {
            // retrieving the body of the exchanged message
            Product input = (Product) exchange.getIn().getBody();
            if(!input.getShop().equals("shopError"))
                Database.getOrder(currentId).setTotPrice(Database.getOrder(currentId).getTotPrice() + input.getPrice());
            //put order somewhere
        }
    };*/

    // Process a map<String -> Object> into a person
    private static Processor newOrderCreation = new Processor() {
        public void process(Exchange exchange) throws Exception {
            //HandleCsvFile.currentId = Database.genUID();
            String currentId = Database.genUID();
            exchange.getIn().setHeader("orderId", currentId);
            Database.createOrder(currentId, "", "");
        }
    };

    // Process a map<String -> Object> into a person
    private static Processor csv2product = new Processor() {

        public void process(Exchange exchange) throws Exception {
            // retrieving the body of the exchanged message
            Map<String, Object> input = (Map<String, Object>) exchange.getIn().getBody();
            // transforming the input into a person
            Product output =  builder(input);
            // Putting the person inside the body of the message
            exchange.getIn().setBody(output);
        }

        private Product builder(Map<String, Object> data) {
            Product p = new Product();
            for(Map.Entry<String, Object> currentEntry : data.entrySet()){
                if(currentEntry.getKey().equals("id")){
                    p.setId((String)currentEntry.getValue());
                    //TODO getshop from db
                    //get from bd
                    //for each id, his shop
                    //String shop = (String) currentEntry.getValue();
                    String shop = "errorShop";
                    p.setShop(shop);
                } else if(currentEntry.getKey().equals("price")){
                    p.setPrice(Double.parseDouble((String)currentEntry.getValue()));
                } else if(currentEntry.getKey().equals("name")){
                    p.setName((String) currentEntry.getValue());
                } else {
                    p.setSpecializedAttribute(currentEntry.getKey(), (String) currentEntry.getValue());
                }
            }
            return p;
        }
    };
}
