package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.business.Order;
import fr.SOA.shopping3000.flows.business.Product;
import fr.SOA.shopping3000.flows.services.OrderService;
import fr.SOA.shopping3000.flows.utils.Database;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.SOA.shopping3000.flows.utils.Endpoints.CSV_INPUT_DIRECTORY;


/**
 * Created by user on 29/10/2015.
 */
public class OrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet");

        /*
         * REST
         */
        rest("/order").description("Client rest service")
                .consumes("application/json").produces("application/json")

                .post("/{client_id}/{order_address}").description("Create order")
                .to("direct:orderCreate")

                .put("/addproduct/{order_id}/{product_id}").description("Add product to order")
                .to("direct:orderAddProduct")

                .put("/valid/{order_id}").description("Valid order")
                .to("direct:orderValid")
        ;

        /*
         * FROM
         */

        from("direct:orderCreate")
                .log("create an empty order")
                .bean(OrderService.class, "createOrder(${header.client_id},${header.order_address})")
        ;

        from("direct:orderAddProduct")
                .log("Add a product to a order")
                .process(addProduct)
        ;

        from("direct:orderValid")
                .log("valid a order")
                .process(orderToCsv)
                .log("drop csv in the order process file")
                .to(CSV_INPUT_DIRECTORY + "?fileName=${header.order_id}.csv")
        ;

    }

    private static Processor addProduct = new Processor() {

        public void process(Exchange exchange) throws Exception {

            String prodId = (String) exchange.getIn().getHeader("product_id");
            String orderId = (String) exchange.getIn().getHeader("order_id");

            Product currentProduct = Database.getProduct(prodId);
            Order currentOrder = Database.getOrder(orderId);

            if (currentProduct == null) {
                exchange.getIn().setBody("Product not in Database" + prodId);
                return;
            }
            if (currentOrder == null) {
                exchange.getIn().setBody("Order not in Database"+ orderId);
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

    private static Processor orderToCsv = new Processor() {

        private static final String COMMA_DELIMITER = ",";
        private static final String NEW_LINE_SEPARATOR = "\n";

        public void process(Exchange exchange) throws Exception {

            String orderId = (String) exchange.getIn().getHeader("order_id");

            //get order from database
            Order order = Database.getOrder(orderId);

            if (order == null) {
                exchange.getIn().setBody("Order not in Database" + orderId);
                return;
            }

            String csvContent = buildCSV(order);

            exchange.getIn().setBody(csvContent);
        }

        private String buildCSV(Order order) {
            List<String> headers = new ArrayList<String>();
            List<List<String>> products = new ArrayList<List<String>>();

            //headers.add("order_id");
            headers.add("order_address");
            headers.add("product_id");

            int cptBlank = 0;

            for (Map.Entry<String, Map<String, String>> curProd : order.getProductIds().entrySet()) {

                List<String> tmpVarValues = new ArrayList<String>();
                //tmpVarValues.add(order.getId());
                tmpVarValues.add(order.getAddress());
                tmpVarValues.add(curProd.getKey());

                for (int i = 0; i < cptBlank; i++) {
                    tmpVarValues.add("");
                }
                if (curProd.getValue() != null) {
                    for (Map.Entry<String, String> perso : curProd.getValue().entrySet()) {
                        headers.add(perso.getKey());
                        tmpVarValues.add(perso.getValue());
                        cptBlank++;
                    }
                }
                products.add(tmpVarValues);
            }

            String csvHeader = String.join(COMMA_DELIMITER, headers);
            String csvProducts = "";
            for (List<String> p : products) {
                csvProducts += NEW_LINE_SEPARATOR+String.join(COMMA_DELIMITER, p);
            }

            return csvHeader+csvProducts;
        }
    };
}
