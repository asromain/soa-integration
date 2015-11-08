package fr.SOA.shopping3000.flows;

import static fr.SOA.shopping3000.flows.utils.Endpoints.*;

import fr.SOA.shopping3000.flows.business.Product;
import fr.SOA.shopping3000.flows.utils.ItemWriter;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.CsvDataFormat;

import java.util.Map;

public class HandleCsvFile extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(CSV_INPUT_DIRECTORY)
                .log("Processing ${file:name}")
                .log("  Loading the file as a CSV document")
                .unmarshal(buildCsvFormat())// Body is now a List(Map("navn" -> ..., ...), ...)
                .log("  Splitting the content of the file into atomic lines")
                .split(body())
                .log("  Transforming a CSV line into a Person")
                .process(csv2product)
                .log("  Transferring to the route that handle a given citizen")
                .setProperty("product", body())
                .bean(ItemWriter.class, "write(${property.product})")
                .to(CSV_OUTPUT_DIRECTORY + "?fileName=output.txt")
                //.to(HANDLE_ITEM)   // Async transfer with JMS ( activemq:... )
                //.setBody(simple("Hello world !"));
        ;

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
                } else if(currentEntry.getKey().equals("price")){
                    p.setPrice((String) currentEntry.getValue());
                } else if(currentEntry.getKey().equals("name")){
                    p.setName((String) currentEntry.getValue());
                } else {
                    p.setSpecializedAttribute("gre", "test");
                }
            }
            return p;
        }

        /*private int getMoneyValue(Map<String, Object> data, String field) {
            String rawIncome = (String) data.get(field);
            return Integer.parseInt(rawIncome.replace(",", "").substring(0, rawIncome.length() - 3));
        }*/
    };
}
