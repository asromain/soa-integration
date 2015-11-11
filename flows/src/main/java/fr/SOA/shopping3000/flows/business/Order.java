package fr.SOA.shopping3000.flows.business;

import fr.SOA.shopping3000.flows.utils.Database;

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

    //private List<String> productIds;

    private Map<String, Map<String, String>> productIds;

    public Order() {
        this.productIds = new HashMap<String, Map<String, String>>();
    }

    public Order(String id, String idClient, String address) {
        this.id = id;
        this.idClient = idClient;
        this.address = address;
        this.totPrice = 0;
        this.productIds = new HashMap<String, Map<String, String>>();
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

    public Map<String, Map<String, String>> getProductIds() {
        return productIds;
    }

    // obsolete
    public void addProduct(String id, Product product) {
        this.totPrice += product.getPrice();
        this.productIds.put(id, null);
    }
    public void addProduct(String id, Map<String, String> persos) {
        this.totPrice += Database.getProduct(id).getPrice();
        this.productIds.put(id, persos);
    }

    public void deleteProduct(String id) {
        Product curr = Database.getProduct(id);
        this.totPrice -= curr.getPrice();
        productIds.remove(id);
    }

    public double calculateTotPrice() {
        this.totPrice = 0;
        for (String curProdId : this.productIds.keySet()) {
            this.totPrice += Database.getProduct(curProdId).getPrice();
        }
        return this.totPrice;
    }




}
