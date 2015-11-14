package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.business.Product;
import fr.SOA.shopping3000.flows.utils.Database;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.*;

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
                .multicast()
                .parallelProcessing()
//                .to("activemq:getCustomShoes")
//                .to("activemq:getCustomShirt")
                .to("activemq:getCustomArt")
                .aggregationStrategy(strat)
                .marshal()
                .json(JsonLibrary.Jackson)
        ;

        from("activemq:getCustomShoes")
                // TODO renvoyer ArrayList<HashMap<String, ArrayList>> qui represente les parametres de personalisation
                // ex : [ { "Couleur" : [ "jaune", "rouge", ... ] } , { "Taille" : [ "S", "M", ... ] } ]
                .log(LoggingLevel.INFO, "Get custom parameters Shoes")
        ;

        from("activemq:getCustomShirt")
                // TODO renvoyer ArrayList<HashMap<String, ArrayList>> qui represente les parametres de personalisation
                // ex : [ { "Couleur" : [ "jaune", "rouge", ... ] } , { "Taille" : [ "S", "M", ... ] } ]
                .log(LoggingLevel.INFO, "Get custom parameters Shirt")

        ;

        from("activemq:getCustomArt")
                // TODO renvoyer ArrayList<HashMap<String, ArrayList>> qui represente les parametres de personalisation
                // ex : [ { "Couleur" : [ "jaune", "rouge", ... ] } , { "Taille" : [ "S", "M", ... ] } ]
                .log(LoggingLevel.INFO, "Get custom parameters Arts")
                .process(processGetCustomArtNeed)
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

            ArrayList<HashMap<String, ArrayList>> first = oldExchange.getIn().getBody(ArrayList.class);
            ArrayList<HashMap<String, ArrayList>> second = newExchange.getIn().getBody(ArrayList.class);

            System.out.println("OldExchange "+oldExchange.getIn().getBody(ArrayList.class));
            System.out.println("NewExchange "+newExchange.getIn().getBody(ArrayList.class));

            ArrayList<HashMap<String, ArrayList>> third = new ArrayList<HashMap<String, ArrayList>>();


            for (HashMap<String, ArrayList> elem : first){
                third.add(elem);
            }

//            for (HashMap<String, ArrayList> elem : second){
//                third.add(elem);
//            }
            oldExchange.getIn().setBody(third);
            return oldExchange;
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

}



