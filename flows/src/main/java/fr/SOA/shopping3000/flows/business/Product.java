package fr.SOA.shopping3000.flows.business;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pierre on 26/10/2015.
 *
 * Represente l'objet metier Produit des notre Mall
 *
 * specializedAttributes -> proprietes specifiques aux differentes boutiques
 */
public class Product implements Serializable {
    
    private String id;
    private String name;
    private String shop;
    private double price;

    private Map<String, String> specializedAttributes;

    public Product() {
        this.specializedAttributes = new HashMap<String, String>();
    }
    public Product(String id, String name, String shop, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.shop = shop;
        this.specializedAttributes = new HashMap<String, String>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public Map<String, String> getSpecializedAttributes() {
        return specializedAttributes;
    }

    public String getSpecializedAttribute(String key) {
        return specializedAttributes.get(key);
    }

    public void setSpecializedAttribute(String key, String value) {
        this.specializedAttributes.put(key, value);
    }

    public String deleteSpecializedAttribute(String key) {
        return specializedAttributes.remove(key);
    }
}
