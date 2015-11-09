package fr.SOA.shopping3000.flows;

import static fr.SOA.shopping3000.flows.utils.Endpoints.*;

import fr.SOA.shopping3000.flows.business.Order;
import fr.SOA.shopping3000.flows.business.Product;
import fr.SOA.shopping3000.flows.utils.Database;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by user on 29/10/2015.
 */
public class CommandRest extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet");

        /*
         * REST
         */

        rest("/command/create/{client_id}/{command_address}")
                .get()
                .to("direct:commandCreate")
        ;

        rest("/command/addproduct/{command_id}/{product_id}")
                .get()
                .to("direct:commandAddProduct")
        ;

        rest("/command/valid/{command_id}")
                .get()
                .to("direct:commandValid")
        ;

        /*
         * FROM
         */

        from("direct:commandCreate")
                .log("create an empty command")
                .process(createOrder)
        ;

        from("direct:commandAddProduct")
                .log("Add a product to a command")
                .process(addProduct)
        ;

        from("direct:commandValid")
                .log("valid a command")
                .process(commandToCsv)
                .log("drop csv in the command file")
                .to(CSV_INPUT_DIRECTORY + "?fileName=${header.command_id}.csv")
        ;
    }


    private static Processor createOrder = new Processor() {

        public void process(Exchange exchange) throws Exception {

            String orderId = Database.genUID();
            String orderClientId = (String) exchange.getIn().getHeader("client_id");
            String orderAddress = (String) exchange.getIn().getHeader("command_address");

            Product p1 = new Product(Database.genUID(), "durex", "boutique 1", 234);
            Product p2 = new Product(Database.genUID(), "plax", "boutique 2", 84);
            p1.setSpecializedAttribute("type", "col v");
            p2.setSpecializedAttribute("color", "bleu");

            //fill database
            Database.createOrder(orderId, orderClientId, orderAddress);
            Database.getOrder(orderId).addProduct(p1.getId(), p1);
            Database.getOrder(orderId).addProduct(p2.getId(), p2);

            exchange.getIn().setBody(orderId);
        }

    };

    private static Processor addProduct = new Processor() {

        public void process(Exchange exchange) throws Exception {

            String prodId = (String) exchange.getIn().getHeader("product_id");
            String commandId = (String) exchange.getIn().getHeader("command_id");

            Product currentProduct = Database.getProduct(prodId);
            Order currentOrder = Database.getOrder(commandId);
            // TODO à verifier si le changement se fait dans la base de donnée
            currentOrder.addProduct(currentProduct.getId(), currentProduct);

            exchange.getIn().setBody("done");
        }

    };

    private static Processor commandToCsv = new Processor() {

        private static final String COMMA_DELIMITER = ",";
        private static final String NEW_LINE_SEPARATOR = "\n";

        public void process(Exchange exchange) throws Exception {

            String commandId = (String) exchange.getIn().getHeader("command_id");

            //get order from database
            Order order = Database.getOrder(commandId);

            String csvContent = buildCSV(order);

            exchange.getIn().setBody(csvContent);
        }

        private String buildCSV(Order order) {
            List<String> headers = new ArrayList<String>();
            List<List<String>> products = new ArrayList<List<String>>();
            // can be smarter
            headers.add("order_id");
            headers.add("order_address");
            headers.add("order_totprice");
            // can be smarter
            headers.add("product_id");
            headers.add("name");
            headers.add("shop");
            headers.add("price");

            int cptBlank = 0;

            for (Product curP : order.getProducts().values()) {
                List<String> tmpVarValues = new ArrayList<String>();
                tmpVarValues.add(order.getId());
                tmpVarValues.add(order.getAddress());
                tmpVarValues.add(String.valueOf(order.getTotPrice()));

                tmpVarValues.add(curP.getId());
                tmpVarValues.add(curP.getName());
                tmpVarValues.add(curP.getShop());
                tmpVarValues.add(String.valueOf(curP.getPrice()));

                for (int i = 0; i < cptBlank; i++) {
                    tmpVarValues.add("");
                }
                for (Map.Entry<String, String> speAtt : curP.getSpecializedAttributes().entrySet()) {
                    headers.add(speAtt.getKey());
                    tmpVarValues.add(speAtt.getValue());
                    cptBlank++;
                }
                products.add(tmpVarValues);
            }
            String csvHeader = String.join(",", headers);
            String csvProducts = "";
            for (List<String> p : products) {
                csvProducts += "\n"+String.join(",", p);
            }

            return csvHeader+csvProducts;
        }


    };



}
