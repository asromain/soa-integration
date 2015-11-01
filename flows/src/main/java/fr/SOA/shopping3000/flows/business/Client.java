package fr.SOA.shopping3000.flows.business;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represente l'objet metier Client de notre Mall
 *
 *
 */
public class Client implements Serializable {

    private String id;
    private String name;
    private String address;


    private Map<String, String> specializedAttributes;

    public Client(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
