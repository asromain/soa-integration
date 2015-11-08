package fr.SOA.shopping3000.flows.utils;

import fr.SOA.shopping3000.flows.business.Product;

/**
 * Created by zeibetsu on 02/11/2015.
 */
public class ItemWriter {

    public String write(Product product) {
        StringBuilder b = new StringBuilder();
        b.append("  id: " + product.getId() + "\n");
        b.append("  name: " + product.getName() + "\n");
        b.append("  price: " + product.getPrice() + "\n");
        return b.toString();
    }
}
