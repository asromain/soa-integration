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
    private String idClient;
    private String address;
    private double totPrice;

    private Map<String, Product> products;

    public Order() {
        this.products = new HashMap<String, Product>();
    }

    public Order(String id, String idClient, String address) {
        this.id = id;
        this.idClient = idClient;
        this.address = address;
        this.totPrice = 0;
        this.products = new HashMap<String, Product>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
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

    public Map<String, Product> getProducts() {
        return products;
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




}
