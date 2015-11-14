package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.business.Product;
import fr.SOA.shopping3000.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by user on 09/11/2015.
 */
public class CustomRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet");

        rest("/custom")
                .get()
                .to("direct:getCustomParameters")
        ;

        from("direct:getCustomParameters")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .log(LoggingLevel.INFO, "BEFORE MULTICAST")
                .multicast(strat)
//                .parallelProcessing()
                .to("direct:getCustomShoes")
                .to("direct:getCustomShirt")
                .to("direct:getCustomArt")
//                .aggregationStrategy(strat)
                .end()
                .log(LoggingLevel.INFO, "AFTER END")
                .marshal()
                .json(JsonLibrary.Jackson)
        ;

        from("direct:getCustomShoes")
        // TODO renvoyer ArrayList<HashMap<String, ArrayList>> qui represente les parametres de personalisation
        // ex : [ { "Couleur" : [ "jaune", "rouge", ... ] } , { "Taille" : [ "S", "M", ... ] } ]
//                .log(LoggingLevel.INFO, "CUSTOM --- DEBUT")
//                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
//                .setBody(constant(""))
//                .log(LoggingLevel.INFO, "CUSTOM --- MULTICAST START")
//                .multicast()
//                .to(Endpoints.BASE_URL + Endpoints.BASE_SHOES + "/custom" + "/cleats" + Endpoints.BRIDGE)
//                .log(LoggingLevel.INFO, "CUSTOM --- MULTICAST 1 --- UNMARSHALL")
//                .unmarshal()
//                .json(JsonLibrary.Jackson)
//                .process(processShoesCustomParameters)
//                .to(Endpoints.BASE_URL + Endpoints.BASE_SHOES + "/custom" + "/colors" + Endpoints.BRIDGE)
//                .log(LoggingLevel.INFO, "CUSTOM --- MULTICAST 2 --- UNMARSHALL")
////                .unmarshal()
////                .json(JsonLibrary.Jackson, ArrayList.class)
//                .process(processShoesCustomParameters)
//                .to(Endpoints.BASE_URL + Endpoints.BASE_SHOES + "/custom" + "/sizes" + Endpoints.BRIDGE)
//                .log(LoggingLevel.INFO, "CUSTOM --- MULTICAST 3 --- UNMARSHALL")
////                .unmarshal()
////                .json(JsonLibrary.Jackson, ArrayList.class)
//                .process(processShoesCustomParameters)
//                .end()
//                .log(LoggingLevel.INFO, "CUSTOM --- MULTICAST END")
////                .process(processAllShoes)
//                .marshal()
//                .json(JsonLibrary.Jackson)
                .log(LoggingLevel.INFO, "Get custom parameters Shoes")
                .setBody(constant("[ {\"key1\": [ {\"key\" : \"valueA\"} ] }, {\"key2\": [ {\"key\": \"valueB\"} ] } ]"))
        ;

        from("direct:getCustomShirt")
        // TODO renvoyer ArrayList<HashMap<String, ArrayList>> qui represente les parametres de personalisation
        // ex : [ { "Couleur" : [ "jaune", "rouge", ... ] } , { "Taille" : [ "S", "M", ... ] } ]
                .log(LoggingLevel.INFO, "Get custom parameters Shirt")
                .setBody(constant("[ {\"key1\": [ {\"key\" : \"valueA\"} ] }, {\"key2\": [ {\"key\": \"valueB\"} ] } ]"))
        ;

        from("direct:getCustomArt")
        // TODO renvoyer ArrayList<HashMap<String, ArrayList>> qui represente les parametres de personalisation
        // ex : [ { "Couleur" : [ "jaune", "rouge", ... ] } , { "Taille" : [ "S", "M", ... ] } ]
                .log(LoggingLevel.INFO, "Get custom parameters Arts")
                .setBody(constant("[ {\"key1\": [ {\"key\" : \"valueA\"} ] }, {\"key2\": [ {\"key\": \"valueB\"} ] } ]"))
        ;

    }

//    private static Processor processAllShoes = new Processor() {
//        public void process(Exchange exchange) throws Exception {
//
//        }
//    }

    private static Processor processShoesCustomParameters = new Processor() {
        public void process(Exchange exchange) throws Exception {
            ArrayList<String> input = (ArrayList<String>) exchange.getIn().getBody();
            HashMap<String, ArrayList> output = translater(input);
            exchange.getIn().setBody(output);
        }

        private HashMap<String, ArrayList> translater(ArrayList<String> input) {
            HashMap<String, ArrayList> output = new HashMap<String, ArrayList>();

            output.put("TODO", input);

            return output;
        }
    };

    AggregationStrategy strat = new AggregationStrategy() {
        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

            if (oldExchange == null) {
                return newExchange;
            }

            String input = newExchange.getIn().getBody(String.class);
            if ("STOP".equalsIgnoreCase(input)) {
                return oldExchange;
            }

            ArrayList<HashMap<String, ArrayList>> first = oldExchange.getIn().getBody(ArrayList.class);
            ArrayList<HashMap<String, ArrayList>> second = newExchange.getIn().getBody(ArrayList.class);

            ArrayList<HashMap<String, ArrayList>> third = new ArrayList<HashMap<String, ArrayList>>();

            if (first != null) {
                third.addAll(first);
            }
            if (second != null) {
                third.addAll(second);
            }

            if (!third.isEmpty()) {
                newExchange.getIn().setBody(third);
            }

            return newExchange;
        }
    };
}
