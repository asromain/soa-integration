package fr.SOA.shopping3000.flows.business;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Florian Rouyer on 26/10/2015.
 */

public class Order implements Serializable {

    private String id;
    private String address;
    private double totPrice;

    private Map<String, Product> products;

    public Order() {
        this.products = new HashMap<String, Product>();
    }

    public Order(String id, String address, double totPrice) {
        this.id = id;
        this.address = address;
        this.totPrice = totPrice;
        this.products = new HashMap<String, Product>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(double totPrice) {
        this.totPrice = totPrice;
    }

    public Product getProduct(String id) {
        return products.get(id);
    }

    public void addProduct(String id, Product product) {
        this.totPrice += product.getPrice();
        this.products.put(id, product);
    }

    public Product deleteProduct(String id) {
        Product curr = products.get(id);
        this.totPrice -= curr.getPrice();
        return products.remove(id);
    }

    public double calculateTotPrice() {
        this.totPrice = 0;
        for (Product curP : this.products.values()) {
            this.totPrice += curP.getPrice();
        }
        return this.totPrice;
    }


    public String toCSV() {
        List<String> headers = new ArrayList<String>();
        List<List<String>> products = new ArrayList<List<String>>();
        // can be smarter
        headers.add("order_id");
        headers.add("order_address");
        headers.add("order_totprice");

        int cptBlank = 0;

        for (Product curP : this.products.values()) {
            List<String> tmpVarValues = new ArrayList<String>();
            tmpVarValues.add(this.id);
            tmpVarValues.add(this.address);
            tmpVarValues.add(String.valueOf(this.totPrice));
            for (int i = 0; i < cptBlank; i++) {
                tmpVarValues.add("");
            }
            for (Map.Entry<String, String> speAtt : curP.getSpecializedAttributes().entrySet()) {
                headers.add(speAtt.getKey());
                tmpVarValues.add(speAtt.getValue());
                cptBlank++;
            }
            products.add(tmpVarValues);
        }
        String csvHeader = String.join(",", headers);
        String csvProducts = "";
        for (List<String> p : products) {
            csvProducts += "\n"+String.join(",", p);
        }

        return csvHeader+csvProducts;
    }

}
