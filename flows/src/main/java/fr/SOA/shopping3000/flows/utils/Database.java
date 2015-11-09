package fr.SOA.shopping3000.flows.utils;


import fr.SOA.shopping3000.flows.business.Client;
import fr.SOA.shopping3000.flows.business.Order;
import fr.SOA.shopping3000.flows.business.Product;

import java.io.Console;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Florian Rouyer on 29/10/2015.
 */
public final class Database {
    private static HashMap<String, Client> clients = new HashMap<String, Client>();
    private static HashMap<String, Order> orders = new HashMap<String, Order>();
    private static HashMap<String, Product> products = new HashMap<String, Product>(){
        {
            put("123",new Product("name1","123","155","customShoes"));
            put("456",new Product("name2","456","985","artinprovence"));
            put("789",new Product("name3","789","454","customShoes"));
            put("147",new Product("name4","147","65","artinprovence"));
            put("258",new Product("name5","258","12","customshirt"));
            put("369",new Product("name6","369","30","customshirt"));
        }
    };





    static public void createOrder(String id, String idClient, String address) {
        Order currentOrder = new Order(id, idClient, address);
        orders.put(id, currentOrder);

    }

<<<<<<< HEAD
    static public Collection<Product> getAllProducts(){
        return Database.products.values();
    }

    //TODO implementer
    static public void createOrder(){
        Order curentOrder = new Order();
        // UTILISER ORDERID POUR L'ID
        //orders.put();
        orderId++;
=======
    static public void createClient(String id, String name, String address){
        Client curentCli = new Client(id,name,address);
        clients.put(id,curentCli);
>>>>>>> dev
    }

    static public void createProduct(String id, String name, String shop, double prix){
        Product currentProduct = new Product(id, name, shop, prix);
        products.put(id, currentProduct);
    }

    static public void addProduct(Product p) {
        products.put(p.getId(), p);
    }

    static public void TESTaddProduct() {
        products.put("test", new Product("test", "test", "test", 2345));
    }

    static public Client getClient(String id){
        return clients.get(id);
    }

    static public Product getProduct(String id) {
        return products.get(id);
    }

    static public Order getOrder(String id) {
        return orders.get(id);
    }

    //TODO implementer
    static public void updateOrder(String id) {
        for (Map.Entry<String, Client> entry : clients.entrySet()){
            if (id.equals(entry.getKey())){

            }
        }
    }

    //TODO implementer
    static public void updateProduct(String id) {
        for (Map.Entry<String, Product> entry : products.entrySet()){
            if (id.equals(entry.getKey())){

            }
        }

    }

    //TODO implementer
    static public void updateClient(String id, String name, String address) {
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


    // generate unique ids
    static public String genUID() {
        return String.valueOf(UUID.randomUUID());
    }


}
