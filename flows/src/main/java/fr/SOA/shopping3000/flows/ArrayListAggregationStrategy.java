package fr.SOA.shopping3000.flows;

import fr.SOA.shopping3000.flows.business.Order;
import fr.SOA.shopping3000.flows.business.Product;
import fr.SOA.shopping3000.flows.utils.Database;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;

public class ArrayListAggregationStrategy implements AggregationStrategy {
    public ArrayListAggregationStrategy() {
        super();
    }

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        Message newIn = newExchange.getIn();
        Object newBody = newIn.getBody();
        ArrayList<Object> list = null;
        if (oldExchange == null) {
            list = new ArrayList<Object>();
            Product p = (Product)newBody;
            Double price = p.getPrice();
            newIn.setHeader("totPrice", price);
            list.add(newBody);
            newIn.setBody(list);
            newIn.setHeader("order_address", (Database.getOrder((String)newIn.getHeader("orderId")).getAddress()));
            return newExchange;
        } else {
            Message in = oldExchange.getIn();
            list = in.getBody(ArrayList.class);
            list.add(newBody);
            Product p = ((Product)newBody);
            in.setHeader("totPrice",(Double)in.getHeader("totPrice") + p.getPrice());
            return oldExchange;
        }
    }

}