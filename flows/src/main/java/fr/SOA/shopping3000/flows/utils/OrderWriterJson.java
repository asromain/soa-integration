package fr.SOA.shopping3000.flows.utils;

import fr.SOA.shopping3000.flows.business.Order;

/**
 * Created by zeibetsu on 09/11/2015.
 */
public class OrderWriterJson {
    public String writeJson(String body) {
        StringBuilder b = new StringBuilder();
        //b.append("  id: " + order.getId() + "\n");
        //b.append("  name: " + order.getIdClient() + "\n");
        //b.append("  price: " + order.getTotPrice() + "\n");
        b.append(body);
        return b.toString();
    }
}
