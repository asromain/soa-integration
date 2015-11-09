package fr.SOA.shopping3000.flows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class ArrayListAggregationStrategy implements AggregationStrategy {

    public ArrayListAggregationStrategy() {
        super();
    }

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        //TODO BUILD COMMAND INTEAD OF STRING
        Message newIn = newExchange.getIn();
        Object newBody = newIn.getBody();
        //Map<String, Object> map = null;
        String aggregation = null;
        if (oldExchange == null) {
            //map = new HashMap<String, Object>();
            aggregation = "";
            //map.put("" + map.keySet().size(),newBody);
            aggregation += newBody.toString();
            //newIn.setBody(map);
            newIn.setBody(aggregation);
            return newExchange;
        } else {
            Message in = oldExchange.getIn();
            aggregation = in.getBody(String.class);
            //map = in.getBody(Map.class);
            //map.put("" + map.keySet().size(), newBody);
            aggregation += newBody.toString();
            in.setBody(aggregation);
            return oldExchange;
        }
    }

}