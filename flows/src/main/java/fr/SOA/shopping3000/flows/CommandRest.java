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
import java.util.HashMap;
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

            //fill database
            Database.createOrder(orderId, orderClientId, orderAddress);

            exchange.getIn().setBody(orderId);
        }

    };

    private static Processor addProduct = new Processor() {

        public void process(Exchange exchange) throws Exception {

            String prodId = (String) exchange.getIn().getHeader("product_id");
            String commandId = (String) exchange.getIn().getHeader("command_id");

            Product currentProduct = Database.getProduct(prodId);
            Order currentOrder = Database.getOrder(commandId);

            if (currentProduct == null) {
                exchange.getIn().setBody("Product not in Database" + prodId);
                return;
            }
            if (currentOrder == null) {
                exchange.getIn().setBody("Order not in Database"+ commandId);
                return;
            }

            Map<String, String> persos = new HashMap<String, String>();
            for (Map.Entry<String, List<String>> perso : currentProduct.getPersonalisations().entrySet()) {
                String persoVal = (String) exchange.getIn().getHeader(perso.getKey());
                if (persoVal != null && perso.getValue().contains(persoVal)) {
                    persos.put(perso.getKey(), persoVal);
                }
                else {
                    exchange.getIn().setBody("error on personnalisation"+ perso.getKey());
                    return;
                }
            }
            currentOrder.addProduct(prodId, persos);

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

            if (order == null) {
                exchange.getIn().setBody("Order not in Database" + commandId);
                return;
            }

            String csvContent = buildCSV(order);

            exchange.getIn().setBody(csvContent);
        }

        private String buildCSV(Order order) {
            List<String> headers = new ArrayList<String>();
            List<List<String>> products = new ArrayList<List<String>>();

            headers.add("order_id");
            headers.add("order_address");
            headers.add("product_id");

            int cptBlank = 0;

            for (String curProdId : order.getProductIds().keySet()) {

                List<String> tmpVarValues = new ArrayList<String>();
                tmpVarValues.add(order.getId());
                tmpVarValues.add(order.getAddress());
                tmpVarValues.add(curProdId);

                for (int i = 0; i < cptBlank; i++) {
                    tmpVarValues.add("");
                }
                for (Map<String, String> persos : order.getProductIds().values()) {
                    if (persos != null) {
                        for (Map.Entry<String, String> perso : persos.entrySet()) {
                            headers.add(perso.getKey());
                            tmpVarValues.add(perso.getValue());
                            cptBlank++;
                        }
                    }
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