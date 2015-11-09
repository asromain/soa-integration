package fr.SOA.shopping3000.flows.utils;

import fr.SOA.shopping3000.flows.business.Order;

/**
 * Created by zeibetsu on 09/11/2015.
 */
public class OrderWriterJson {
    public String writeJson(Order order) {
        StringBuilder b = new StringBuilder();
        /*b.append("  id: " + product.getId() + "\n");
        b.append("  name: " + product.getName() + "\n");
        b.append("  price: " + product.getPrice() + "\n");*/
        return b.toString();
    }
}
