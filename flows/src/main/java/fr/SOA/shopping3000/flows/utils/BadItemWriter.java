package fr.SOA.shopping3000.flows.utils;

import fr.SOA.shopping3000.flows.business.Product;

import java.util.List;

/**
 * Created by zeibetsu on 13/11/2015.
 */
public class BadItemWriter {
    public Product writeBadItem(Product body) {
        body.setShop("shopError");
        return body;
    }
}