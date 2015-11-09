package fr.SOA.shopping3000.flows;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;
import java.util.HashMap;

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
                .multicast()
                .parallelProcessing()
                .to("activemq:getCustomShoes", "activemq:getCustomShirt", "activemq:getCustomArt")
                .aggregationStrategy(strat)
                .marshal()
                .json(JsonLibrary.Jackson)
        ;

        from("activemq:getCustomShoes")
        // TODO renvoyer ArrayList<HashMap<String, ArrayList>> qui represente les parametres de personalisation
        // ex : [ { "Couleur" : [ "jaune", "rouge", ... ] } , { "Taille" : [ "S", "M", ... ] } ]
        ;

        from("activemq:getCustomShirt")
        // TODO renvoyer ArrayList<HashMap<String, ArrayList>> qui represente les parametres de personalisation
        // ex : [ { "Couleur" : [ "jaune", "rouge", ... ] } , { "Taille" : [ "S", "M", ... ] } ]
        ;

        from("activemq:getCustomArt")
        // TODO renvoyer ArrayList<HashMap<String, ArrayList>> qui represente les parametres de personalisation
        // ex : [ { "Couleur" : [ "jaune", "rouge", ... ] } , { "Taille" : [ "S", "M", ... ] } ]
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

            ArrayList<HashMap<String, ArrayList>> third = new ArrayList<HashMap<String, ArrayList>>();
            third.addAll(first);
            third.addAll(second);

            newExchange.getIn().setBody(third);

            return newExchange;
        }
    };
}
