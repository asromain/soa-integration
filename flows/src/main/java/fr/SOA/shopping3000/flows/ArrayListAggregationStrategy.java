package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.business.Order;
import fr.SOA.shopping3000.flows.business.Product;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;

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
        ArrayList<Object> list = null;
        //String aggregation = null;
        if (oldExchange == null) {
            //map = new HashMap<String, Object>();
            list = new ArrayList<Object>();
            //aggregation = "";
            //map.put("" + map.keySet().size(),newBody);
            Product p = (Product)newBody;
            //order.setTotPrice(order.getTotPrice() + p.getPrice());
            newIn.setHeader("totPrice", p.getPrice());
            list.add(newBody);
            //order.setTotPrice(((Product)newBody).getPrice());
            //aggregation += newBody.toString();
            //newIn.setBody(map);
            //newIn.setBody(aggregation);
            newIn.setBody(list);
            return newExchange;
        } else {
            Message in = oldExchange.getIn();
            //aggregation = in.getBody(String.class);
            //map = in.getBody(Map.class);
            list = in.getBody(ArrayList.class);
            //map.put("" + map.keySet().size(), newBody);
            //Product p = (Product)newBody;
            //order.setTotPrice(order.getTotPrice() + p.getPrice());
            list.add(newBody);
            in.setHeader("totPrice",(Double)in.getHeader("totPrice") + ((Product)newBody).getPrice());
            //aggregation += newBody.toString();
            //order.setTotPrice(order.getTotPrice() + ((Product)newBody).getPrice());
            //in.setBody(aggregation);
            return oldExchange;
        }
    }

}