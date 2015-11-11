package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.business.Order;
import fr.SOA.shopping3000.flows.business.Product;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class ArrayListAggregationStrategy implements AggregationStrategy {
    private Order order = null;
    public ArrayListAggregationStrategy(Order order) {
        super();
        this.order = order;
    }

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        //TODO BUILD ORDER INTEAD OF STRING
        Message newIn = newExchange.getIn();
        Object newBody = newIn.getBody();
        //Map<String, Object> map = null;
        String aggregation = null;
        if (oldExchange == null) {
            //map = new HashMap<String, Object>();
            aggregation = "";
            //map.put("" + map.keySet().size(),newBody);
            order.setTotPrice(((Product)newBody).getPrice());
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
            order.setTotPrice(order.getTotPrice() + ((Product)newBody).getPrice());
            in.setBody(aggregation);
            return oldExchange;
        }
    }

}