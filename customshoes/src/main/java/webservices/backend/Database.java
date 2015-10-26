package webservices.backend;

import webservices.classes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe qui fait office de base de donnee
 * pour les differents web service... a voir
 */
public class Database {

    /*----------------------/
    /       CUSTOMERS       /
    /----------------------*/


    // database customers
    private static Map<String, Customer> dbCustomers = new HashMap<String, Customer>();

    /**
     * Permet l'ajout d'un nouveau client dans le registre client
     */
    public static void initRegistreClient(Customer c) {
        dbCustomers.put(c.getRefCustomer(), c);
    }

    /**
     * Retourne un client en fonction de sa reference
     * @param ref
     * @return
     */
    public static Customer getCustomer(String ref) {
        return dbCustomers.get(ref);
    }

    /**
     * Insere un nouveau client
     * @param c
     * */
    public static void insertCustomer(Customer c) {
        dbCustomers.put(c.getRefCustomer(), c);
    }

    /**
     * Supprime un client
     * @param customerRef
     */
    public static void deleteCustomer(String customerRef) {
        dbCustomers.remove(customerRef);
    }

    /**
     * Liste tous les clients
     */
    public static List<Customer> listCustomers()
    {
        List<Customer> liste = new ArrayList<Customer>();
        for (Map.Entry<String, Customer> entry : dbCustomers.entrySet()) {
            // String key = entry.getKey();
            Customer customerInfo = entry.getValue();
            liste.add(customerInfo);
        }
        return liste;
    }

    /**
     * GETTER base de donnee clients
     * @return
     */
    public static Map<String, Customer> getDbCustomers() {
        return dbCustomers;
    }

    /**
     * Insertion de donnee dans le registre client
     */
    static {
        Database.initRegistreClient(new Customer("jean", "durand", "user1@mail.com"));
        Database.initRegistreClient(new Customer("sebastien", "garro", "user2@mail.com"));
        Database.initRegistreClient(new Customer("romain", "mattei", "user3@mail.com"));
        Database.initRegistreClient(new Customer("david", "gamba", "user4@mail.com"));
        Database.initRegistreClient(new Customer("christopher", "elena", "user5@mail.com"));
    }



    /*----------------------/
    /        ORDERS         /
    /----------------------*/

    private static Map<String, Order> dbOrders = new HashMap<String, Order>();

    /**
     * Initialise la base de donnée dbOrders
     * @param order
     */
    public static void initDbOrders(Order order) {
        dbOrders.put(order.getRefOrder(), order);
    }

    /**
     * Retourne une commande en fonction de la reference
     * @param orderRef
     * @return
     */
    public static Order getOrder(String orderRef) {
        return dbOrders.get(orderRef);
    }

    /**
     * Liste les commandes
     * @return
     */
    public static List<Order> listOrders()
    {
        List<Order> liste = new ArrayList<Order>();
        for (Map.Entry<String, Order> entry : dbOrders.entrySet()) {
            // String key = entry.getKey();
            Order orderInfo = entry.getValue();
            liste.add(orderInfo);
        }
        return liste;
    }

    /**
     * Supprimer une commande
     * @param refOrder
     */
    public static void deleteOrder(String refOrder) {
        dbOrders.remove(refOrder);
    }

    /**
     * GETTER dbOrders
     * @return
     */
    public static Map<String, Order> getDbOrders() {
        return dbOrders;
    }

    static {
        Database.initDbOrders(new Order(new Product("FG : Firm Ground", "Black", "41", "120"), "davigamb4", "T0", "P0"));    // P4
        Database.initDbOrders(new Order(new Product("SG : Soft Ground", "Grey", "41.5", "120"), "davigamb4", "T1", "P1"));    // P0
        Database.initDbOrders(new Order(new Product("H : Hybrid", "Blue", "41", "120"), "jeandura1", "T2", "P2"));    // P1
        Database.initDbOrders(new Order(new Product("HG : Hard ground", "Yellow", "43", "120"), "sebagarr2", "T3", "P3"));    // P2
        Database.initDbOrders(new Order(new Product("AG : Artificial ground", "Red", "44", "120"), "sebagarr2", "T4", "-1"));    // P3
    }



    /*----------------------/
    /        PRODUCTS       /
    /----------------------*/

    // database products
    private static Map<String, Product> dbProducts = new HashMap<String, Product>();

    public static Map<String, Product> getDbProducts() { return dbProducts; }

    /**
     * Initialise la BD
     */
    private static void generateProducts() {
        for (int i = 0; i < 5; i++) {
            Product add = new Product(
                    CustomizationParameters.cleats.get(String.valueOf(i)),
                    CustomizationParameters.colors.get(String.valueOf(i)),
                    CustomizationParameters.sizes.get(String.valueOf(i)), "120");
            dbProducts.put(add.getRefProduct(), add);
        }
    }

    /**
     * Pour affiche tous les produits
     */
    public static List<Product> listProducts() {
        List<Product> liste = new ArrayList<Product>();
        for (Map.Entry<String, Product> entry : dbProducts.entrySet()) {
            // String key = entry.getKey();
            Product productInfo = entry.getValue();
            liste.add(productInfo);
        }
        return liste;
    }

    /**
     *
     * @param ref
     * @return
     */
    public static Product getProduct(String ref) {
        return dbProducts.get(ref);
    }

    static {
        generateProducts();
    }



    /*----------------------/
    /        TRACKING       /
    /----------------------*/

    // database products
    private static Map<String, Tracking> dbTracking = new HashMap<String, Tracking>();

    /**
     * Initialise la base de donnee Tracking
     * @param t
     */
    public static void initDbTracking(Tracking t) {
        dbTracking.put(t.getTrackingCode(), t);
    }

    public static List<Tracking> listTracking()
    {
        List<Tracking> liste = new ArrayList<Tracking>();
        for (Map.Entry<String, Tracking> entry : dbTracking.entrySet()) {
            Tracking t = entry.getValue();
            liste.add(t);
        }
        return liste;
    }

    /**
     * Renvoie les informations de livraison
     * @param trackingCode
     * @return
     */
    public static Tracking getTracking(String trackingCode) {
        return dbTracking.get(trackingCode);
    }

    public static void deleteTracking(String trackingCode) {
        dbTracking.remove(trackingCode);
    }

    public static Map<String, Tracking> getDbTracking() {
        return dbTracking;
    }

    static {
        Database.initDbTracking(new Tracking("in-preparation"));
        Database.initDbTracking(new Tracking("in-preparation"));
        Database.initDbTracking(new Tracking("delivered"));
        Database.initDbTracking(new Tracking("delivered"));
        Database.initDbTracking(new Tracking("in-delivery"));
    }


    /*----------------------/
    /        PAYMENT        /
    /----------------------*/
    private static Map<String, Payment> dbPayment = new HashMap<String, Payment>();

    public static Map<String, Payment> getDbPayment() {
        return dbPayment;
    }

    public static void initDbPayment(Payment p) {
        dbPayment.put(p.getPaymentId(), p);
    }

    static {
        Database.initDbPayment(new Payment("CB", "David", "Gamba", "12 rue de machin"));
        Database.initDbPayment(new Payment("Cheque", "Jean", "Durand", "Le val vesqui"));
        Database.initDbPayment(new Payment("CB", "Sebastien", "Garro", "Avenue de la republique"));
        Database.initDbPayment(new Payment("CB", "Sebastien", "Garro", "Avenue de la republique"));
    }
}
