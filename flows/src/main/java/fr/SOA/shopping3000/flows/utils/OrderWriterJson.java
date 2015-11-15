package fr.SOA.shopping3000.flows.utils;

import fr.SOA.shopping3000.flows.business.Product;

import java.util.List;

/**
 * Created by zeibetsu on 09/11/2015.
 */
public class OrderWriterJson {
    public String writeJson(List body, String totPrice, String orderId, String orderAddress) {
        StringBuilder b = new StringBuilder();
        b.append("{\"OrderId\": \"" + orderId  + "\",");
        b.append("\"Order Address\": \"" + orderAddress  + "\",");
        b.append("\"total Price\": \"" + totPrice + "\",");
        b.append("\"products\": [");
        for(Object currentObject : body) {
            Product currentProduct = (Product)currentObject;
            b.append("{\"id\": \"" + currentProduct.getId() + "\",");
            b.append("\"shop\": \"" + currentProduct.getShop() + "\",");
            b.append("\"price\": \"" + currentProduct.getPrice() + "\"},");
        }
        b.deleteCharAt(b.length()-1);
        b.append("]}");
        return b.toString();
    }
}
