package fr.SOA.shopping3000.flows.utils;


import fr.SOA.shopping3000.flows.business.Client;
import fr.SOA.shopping3000.flows.business.Order;
import fr.SOA.shopping3000.flows.business.Product;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Florian Rouyer on 29/10/2015.
 */
public final class Database {
    private static HashMap<String, Client> clients = new HashMap<String, Client>();
    private static HashMap<String, Order> orders = new HashMap<String, Order>();
    private static HashMap<String, Product> products = new HashMap<String, Product>();



    static public void createClient(String id, String name, String address){
        Client curentCli = new Client(id,name,address);
        clients.put(id,curentCli);
    }

    //TODO implementer
    static public void createOrder(){
        Order curentOrder = new Order();
        //orders.put();
    }

    //TODO implementer
    static public void createProduct(String id, String name, String shop, String prix){
        Product products = new Product(id, name, shop, prix);
        //products.put();
    }

    static public Client getClient(String id){
        for (Map.Entry<String, Client> entry : clients.entrySet()){
            if (id.equals(entry.getKey())){
                return entry.getValue();
            }
        }
        return null;
    }

    static public Product getProduct(String id){
        for (Map.Entry<String, Product> entry : products.entrySet()){
            if (id.equals(entry.getKey())){
                return entry.getValue();
            }
        }
        return null;
    }

    static public Order getOrder(String id){
        for (Map.Entry<String, Order> entry : orders.entrySet()){
            if (id.equals(entry.getKey())){
                return entry.getValue();
            }
        }
        return null;
    }

    //TODO implementer
    static public void updateOrder(String id){
        for (Map.Entry<String, Client> entry : clients.entrySet()){
            if (id.equals(entry.getKey())){

            }
        }
    }

    //TODO implementer
    static public void updateProduct(String id){
        for (Map.Entry<String, Product> entry : products.entrySet()){
            if (id.equals(entry.getKey())){

            }
        }

    }

    //TODO implementer
    static public void updateClient(String id, String name, String address){
        for (Map.Entry<String, Client> entry : clients.entrySet()){
            if (id.equals(entry.getKey())){
                entry.getValue().setAddress(address);
                entry.getValue().setName(name);
            }
        }

    }

    static public void logTest() {
        System.out.println("passe");
    }


}
