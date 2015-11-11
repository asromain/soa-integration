package fr.SOA.shopping3000.flows.utils;

import fr.SOA.shopping3000.flows.business.Order;
import fr.SOA.shopping3000.flows.business.Product;

import java.util.List;

/**
 * Created by zeibetsu on 09/11/2015.
 */
public class OrderWriterJson {
    public String writeJson(List body, String totPrice) {
        StringBuilder b = new StringBuilder();
        for(Object currentObject : body) {
            Product currentProduct = (Product)currentObject;
            b.append("  id: " + currentProduct.getId() + "\n");
            //if valid VALIDATED, if no shop ERROR
            //b.append("  status: " + currentProduct.getName() + "\n");
            b.append("  name: " + currentProduct.getName() + "\n");
            b.append("  price: " + currentProduct.getPrice() + "\n\n");
        }
        b.append("\n\n totPrice: " + totPrice);
        return b.toString();
    }
}
