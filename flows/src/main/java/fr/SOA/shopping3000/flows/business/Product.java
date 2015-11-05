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
    private String price;
    private String shop;

    private Map<String, String> specializedAttributes;

    public Product(String name, String id, String price, String shop) {
        this.name = name;
        this.id = id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
