package fr.SOA.shopping3000.flows.utils;

import fr.SOA.shopping3000.flows.business.Product;

import java.util.List;

/**
 * Created by zeibetsu on 09/11/2015.
 */
public class OrderWriterJson {
    public String writeJson(List body, String totPrice, String orderId, String orderAddress) {
        StringBuilder b = new StringBuilder();
        for(Object currentObject : body) {
            Product currentProduct = (Product)currentObject;
            b.append("  id: " + currentProduct.getId() + "\n");
            b.append("  shop: " + currentProduct.getShop() + "\n");
            b.append("  price: " + currentProduct.getPrice() + "\n\n");
        }
        b.append("\n\n  OrderId: " + orderId  + "\n");
        b.append("  Order Address: " + orderAddress  + "\n");
        b.append("  total Price: " + totPrice);
        return b.toString();
    }
}
