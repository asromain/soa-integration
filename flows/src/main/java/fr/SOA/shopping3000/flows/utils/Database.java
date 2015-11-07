package fr.SOA.shopping3000.flows.utils;


import fr.SOA.shopping3000.flows.business.Client;
import fr.SOA.shopping3000.flows.business.Order;
import fr.SOA.shopping3000.flows.business.Product;

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
        Client currentCli = new Client(id,name,address);
        clients.put(id,currentCli);
    }

    //TODO implementer
    static public void createOrder(String id, String address, double totPrice){
        Order currentOrder = new Order(id, address, totPrice);
        // for the test
        Product cp = new Product("id_product", "id_name", "id_shop", 35.45);
        currentOrder.addProduct(cp.getId(), cp);
        // end
        orders.put(id, currentOrder);
    }

    //TODO implementer
    static public void createProduct(String id, String name, String shop, double prix){
        Product products = new Product(id, name, shop, prix);
        //products.put();
    }

    static public Client getClient(String id){
        return clients.get(id);
    }

    static public Product getProduct(String id){
        return products.get(id);
    }

    static public Order getOrder(String id){
        return orders.get(id);
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


}
