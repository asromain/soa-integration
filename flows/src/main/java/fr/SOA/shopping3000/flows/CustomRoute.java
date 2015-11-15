package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.business.Product;
import fr.SOA.shopping3000.flows.utils.Database;
import fr.SOA.shopping3000.flows.utils.Endpoints;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 09/11/2015.
 */
public class CustomRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("direct:getCustomParameters")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .setBody(constant(""))
                .multicast()
                .parallelProcessing()
                .to("activemq:getCustomShoes")
                .to("activemq:getCustomShirt")
                .to("activemq:getCustomArt")
                .aggregationStrategy(strat)
                .end()
                .marshal()
                .json(JsonLibrary.Jackson)
        ;

        from("activemq:getCustomShoes")
                // TODO renvoyer ArrayList<HashMap<String, ArrayList>> qui represente les parametres de personalisation
                // ex : [ { "Couleur" : [ "jaune", "rouge", ... ] } , { "Taille" : [ "S", "M", ... ] } ]
//                .log(LoggingLevel.INFO, "CUSTOM --- DEBUT")
//                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
//                .setBody(constant(""))
//                .log(LoggingLevel.INFO, "CUSTOM --- MULTICAST START")
//                .multicast()
//                .to(Endpoints.BASE_URL + Endpoints.BASE_SHOES + "/custom" + "/cleats" + Endpoints.BRIDGE)
//                .log(LoggingLevel.INFO, "${body}")
//                .unmarshal()
//                .json(JsonLibrary.Jackson, Map.class)
////                .process(processShoesCustomParameters)
//                .log(LoggingLevel.INFO, "${body}")
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
                .process(processGetCustomArtNeed)
        ;

        from("activemq:getCustomShirt")
                // TODO renvoyer ArrayList<HashMap<String, ArrayList>> qui represente les parametres de personalisation
                // ex : [ { "Couleur" : [ "jaune", "rouge", ... ] } , { "Taille" : [ "S", "M", ... ] } ]

                .log(LoggingLevel.INFO, "Get custom parameters Shirt")

                .multicast()
                .to("direct:customShirtTypes")
                .to("direct:customShirtColors")
                .to("direct:customShirtSymbols")
                .aggregationStrategy(strat)
                .end()
        ;

        from("direct:customShirtTypes")
                .to(Endpoints.BASE_URL + Endpoints.BASE_SHIRT + "/catalog/types" + Endpoints.BRIDGE)

                .unmarshal().json(JsonLibrary.Jackson)

                .process(shirtTranslation)
        ;

        from("direct:customShirtColors")
                .to(Endpoints.BASE_URL + Endpoints.BASE_SHIRT + "/catalog/colors" + Endpoints.BRIDGE)

                .unmarshal().json(JsonLibrary.Jackson)

                .process(shirtTranslation)
        ;

        from("direct:customShirtSymbols")
                .to(Endpoints.BASE_URL + Endpoints.BASE_SHIRT + "/catalog/symbols" + Endpoints.BRIDGE)

                .unmarshal().json(JsonLibrary.Jackson)

                .process(shirtTranslation)
        ;

        from("activemq:getCustomArt")
                // TODO renvoyer ArrayList<HashMap<String, ArrayList>> qui represente les parametres de personalisation
                // ex : [ { "Couleur" : [ "jaune", "rouge", ... ] } , { "Taille" : [ "S", "M", ... ] } ]
                .log(LoggingLevel.INFO, "Get custom parameters Arts")
                .process(processGetCustomArtNeed)
        ;

        restConfiguration().component("servlet");

        rest("/custom")
                .get()
                .to("direct:getCustomParameters")
        ;

    }

    AggregationStrategy strat = new AggregationStrategy() {

        public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

            if (oldExchange == null) {
                return newExchange;
            }

            String input = newExchange.getIn().getBody(String.class);
            if ("STOP".equalsIgnoreCase(input)) {
                return oldExchange;
            }

            ArrayList first = oldExchange.getIn().getBody(ArrayList.class);
            ArrayList second = newExchange.getIn().getBody(ArrayList.class);

            ArrayList third = new ArrayList();

            if (first != null) {
                third.addAll(first);
            }
            if (second != null) {
                third.addAll(second);
            }

            oldExchange.getIn().setBody(third);
            return oldExchange;
        }
    };

    private static Processor processShoesCustomParameters = new Processor() {
        public void process(Exchange exchange) throws Exception {
            HashMap<String, String> input = (HashMap<String, String>) exchange.getIn().getBody();
            exchange.getIn().setBody(input);
        }
    };


    private static Processor processGetCustomArtNeed = new Processor() {

        public void process(Exchange exchange) throws Exception {
            ArrayList<HashMap<String, ArrayList>> output = getUrl();
            exchange.getIn().setBody(output);
        }

        public ArrayList<HashMap<String, ArrayList>> getUrl() {

            ArrayList<HashMap<String, ArrayList>> output = new ArrayList<HashMap<String, ArrayList>>();

            ArrayList<String> url = new ArrayList<String>();
            url.add("\"url/exemple/image.jpg\"");

            HashMap<String, ArrayList> mapUrl = new HashMap<String, ArrayList>();
            mapUrl.put("urlExemple", url);

            output.add(mapUrl);
            return output;
        }
    };

    private static Processor shirtTranslation = new Processor() {

        public void process(Exchange exchange) throws Exception {
            HashMap<String, ArrayList<HashMap<String, String>>> input = (HashMap<String, ArrayList<HashMap<String, String>>>) exchange.getIn().getBody();

            ArrayList<HashMap<String, ArrayList>> output = translater(input);
            exchange.getIn().setBody(output);
        }

        private ArrayList<HashMap<String, ArrayList>> translater(HashMap<String, ArrayList<HashMap<String, String>>> input) {
            Map<String, List<String>> persos = new HashMap<String, List<String>>();

            ArrayList<HashMap<String, ArrayList>> output = new ArrayList<HashMap<String, ArrayList>>();

            Product product = Database.getProduct("customshirt-1");

            if (product == null) {

                String id = "customshirt-1";
                String name = "Tshirt Personnalisable";
                Double prix = (double) 101;
                String shop = "customshirt";

                product = new Product(id, name, shop, prix);
            }
            ArrayList<String> persosForCatalog = new ArrayList<String>();
            for (HashMap.Entry<String, ArrayList<HashMap<String, String>>> entry : input.entrySet()) {
                for (String lhm : entry.getValue().get(0).values()) {
                    persosForCatalog.add(lhm);
                }
                // c'est un hashmap donc ca remplace l'ancien si deja existant
                product.setPersonalisation(entry.getKey(), persosForCatalog);
            }

            HashMap<String, ArrayList> value = (HashMap)product.getPersonalisations();

            output.add(value);
            return output;
        }

    };

}



