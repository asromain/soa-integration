package business;

import org.json.JSONObject;

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

    private String name;
    private String idShop;
    private String cost;

    private Map<String, String> specializedAttributes;

    public Product(String attributs) {
        JSONObject product = new JSONObject(attributs);
        this.specializedAttributes = new HashMap<String, String>();


    }


}
