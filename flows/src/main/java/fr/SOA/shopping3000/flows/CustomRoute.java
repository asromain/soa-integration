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

                /*

                Impossible de traiter les donn�es re�ues par nos webservices apr�s beaucoup d'heures de tentatives
                Ce code est donc mock�

                 */

                .log(LoggingLevel.INFO, "Get custom parameters Shoes")
                .process(processShoesCustomParameters)
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
            ArrayList<HashMap<String, ArrayList>> output = getUrl();
            exchange.getIn().setBody(output);
        }

        public ArrayList<HashMap<String, ArrayList>> getUrl() {

            ArrayList<HashMap<String, ArrayList>> output = new ArrayList<HashMap<String, ArrayList>>();

            ArrayList<String> cleatsData = new ArrayList<String>();
            cleatsData.add("\"0\": [\n" +
                    "    \"FG : Firm Ground\"\n" +
                    "  ],\n" +
                    "  \"1\": [\n" +
                    "    \"SG : Soft Ground\"\n" +
                    "  ],\n" +
                    "  \"2\": [\n" +
                    "    \"H : Hybrid\"\n" +
                    "  ],\n" +
                    "  \"3\": [\n" +
                    "    \"HG : Hard ground\"\n" +
                    "  ],\n" +
                    "  \"4\": [\n" +
                    "    \"AG : Artificial ground\"\n" +
                    "  ],\n" +
                    "  \"5\": [\n" +
                    "    \"AT : Astro turf\"\n" +
                    "  ]");

            HashMap<String, ArrayList> cleats = new HashMap<String, ArrayList>();
            cleats.put("Cleats", cleatsData);

            ArrayList<String> colorsData = new ArrayList<String>();
            colorsData.add("\"0\": [\n" +
                    "    \"Black\"\n" +
                    "  ],\n" +
                    "  \"1\": [\n" +
                    "    \"White\"\n" +
                    "  ],\n" +
                    "  \"2\": [\n" +
                    "    \"Grey\"\n" +
                    "  ],\n" +
                    "  \"3\": [\n" +
                    "    \"Blue\"\n" +
                    "  ],\n" +
                    "  \"4\": [\n" +
                    "    \"Yellow\"\n" +
                    "  ],\n" +
                    "  \"5\": [\n" +
                    "    \"Green\"\n" +
                    "  ],\n" +
                    "  \"6\": [\n" +
                    "    \"Red\"\n" +
                    "  ],\n" +
                    "  \"7\": [\n" +
                    "    \"Pink\"\n" +
                    "  ],\n" +
                    "  \"8\": [\n" +
                    "    \"Orange\"\n" +
                    "  ],\n" +
                    "  \"9\": [\n" +
                    "    \"Brown\"\n" +
                    "  ]");

            HashMap<String, ArrayList> colors = new HashMap<String, ArrayList>();
            colors.put("Colors", colorsData);

            ArrayList<String> sizeData = new ArrayList<String>();
            sizeData.add("\"0\": [\n" +
                    "    \"41\"\n" +
                    "  ],\n" +
                    "  \"1\": [\n" +
                    "    \"41.5\"\n" +
                    "  ],\n" +
                    "  \"2\": [\n" +
                    "    \"42\"\n" +
                    "  ],\n" +
                    "  \"3\": [\n" +
                    "    \"42.5\"\n" +
                    "  ],\n" +
                    "  \"4\": [\n" +
                    "    \"43\"\n" +
                    "  ],\n" +
                    "  \"5\": [\n" +
                    "    \"43.5\"\n" +
                    "  ],\n" +
                    "  \"6\": [\n" +
                    "    \"44\"\n" +
                    "  ],\n" +
                    "  \"7\": [\n" +
                    "    \"44.5\"\n" +
                    "  ],\n" +
                    "  \"8\": [\n" +
                    "    \"45\"\n" +
                    "  ],\n" +
                    "  \"9\": [\n" +
                    "    \"45.5\"\n" +
                    "  ]");

            HashMap<String, ArrayList> size = new HashMap<String, ArrayList>();
            size.put("Sizes", sizeData);

            output.add(cleats);
            output.add(colors);
            output.add(size);
            return output;
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

//            Product product = Database.getProduct("customshirt-1");

            Product product = null;

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



