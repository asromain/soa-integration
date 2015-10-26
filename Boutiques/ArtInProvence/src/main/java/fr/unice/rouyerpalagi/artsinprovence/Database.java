package fr.unice.rouyerpalagi.artsinprovence;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.*;

/**
 * Class to handle the storage of the application.
 */
public class Database {

    // region PROPERTIES
    /**
     * HashMap of the client, stored by id
     */
    private static HashMap<String, Client> clients = new HashMap<String, Client>();

    /**
     * HashMap of arts produce, stored by id
     */
    private static HashMap<String, Painting> catalog = new HashMap<String, Painting>();

    /**
     * HashMap of the orders, stored by id
     */
    private static HashMap<String, Order> orders = new HashMap<String, Order>();

    /**
     * HashMap of the special paintings
     */
    private static HashMap<String, SpecialPainting> specialPaintingStorage = new HashMap<String, SpecialPainting>();
    // endregion



    // region GETTERS / SETTERS
    // endregion

    // region CONSTRUCTORS
    // endregion

    // region METHODS

    /**
     * Create and save a new Painting to sold.
     * @param description description of the product.
     * @param price price (random) of the product.
     * @return paintingID
     */
    public static String createPainting(String description, Integer price) {
        Painting currentPainting = new Painting(description, price);
        String paintingID = currentPainting.getID();
        catalog.put(paintingID, currentPainting);
        return paintingID;
    }

    public static String createSpecialPainting(String urlImage, String clientID) {
        // Simulation prix
        Random r = new Random();
        int Low = 100;
        int High = 1000;
        int price = r.nextInt(High-Low) + Low;
        SpecialPainting specialPainting = new SpecialPainting("Special painting for : " + clientID, price, urlImage);
        String paintingID = specialPainting.getID();
        specialPaintingStorage.put(paintingID, specialPainting);
        return paintingID;
    }

    public static String createOrder (String clientID, String urlImage, String deliveryAddress) {
        Order currentOrder = new Order(clientID, urlImage, deliveryAddress);
        String orderID = currentOrder.getID();
        orders.put(orderID, currentOrder);
        return orderID;
    }

    public static String createOrder (String clientID, ArrayList<String> paintingArray, String deliveryAddress) {
        Order currentOrder = new Order(clientID, paintingArray, deliveryAddress);
        String orderID = currentOrder.getID();
        orders.put(orderID, currentOrder);
        return orderID;
    }

    /**
     * Check the {@link #orders} to see if the painting is available or not.
     * @param paintingID the painting we're looking for.
     * @return true if the painting is available, false otherwise.
     */
    public static boolean isPaintingAvailable (String paintingID) {
        for (Painting painting : catalog.values()) {
            if (paintingID.equals(painting.getID())) {
                if (!painting.isAvailable()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retrieve all paintings of the catalog
     * @return the catalog itself.
     */
    public static Collection<Painting> getAllCatalog() {
        return Database.catalog.values();
    }

    /**
     * Retrieve the Painting object associated with the given ID.
     * If not found, return null.
     * @param paintingID the ID of the Painting.
     * @return the Painting if found, null otherwise.
     */
    public static Painting findInCatalog(String paintingID) {
        if (Database.catalog.containsKey(paintingID)) {
            return Database.catalog.get(paintingID);
        }
        return null;
    }

    public static SpecialPainting findInSpecialCatalog(String paintingID) {
        if (Database.specialPaintingStorage.containsKey(paintingID)) {
            return Database.specialPaintingStorage.get(paintingID);
        }
        return null;
    }

    public static Order findInOrders(String orderID) {
        if (Database.orders.containsKey(orderID)) {
            return Database.orders.get(orderID);
        }
        return null;
    }

    /**
     *
     * @param lastName Nom du client
     * @param firstName Prénom du client
     * @param address Adresse de facturation du client
     * @param mail Adresse mail du client
     */
    public static String createClient(String lastName, String firstName, String address, String mail){
        Client client = new Client(lastName, firstName, address, mail);
        clients.put(client.getID(),client);
        return client.getID();
    }

    /**
     *
     * @param cli objet Client qui va permettre son accès pour le modifier
     * @param lastName nouveau nom
     * @param firstName nouveau prénom
     * @param address nouvelle adresse de facturation
     * @param mail nouvelle adresse mail
     */
    public static void updateClient(Client cli, String lastName, String firstName, String address, String mail){
        for(Map.Entry<String, Client> entry : clients.entrySet()){
            if (cli.equals(entry.getValue())){
                entry.getValue().setFirstName(firstName);
                entry.getValue().setLastName(lastName);
                entry.getValue().setAddress(address);
                entry.getValue().setMail(mail);
            }
        }
    }

    /**
     *
     * @param id identifiant du client à supprimer
     */
    public static void deleteClient(String id){
        for(Map.Entry<String, Client> entry : clients.entrySet()){
            if (id.equals(entry.getKey())){
                clients.remove(entry.getKey());
                System.out.println("Match");
            }
        }
    }

    /**
     *
     * @param mail mail du client servant de comparaison afin de savoir
     *             si le client n'est pas deja dans la MAP
     * @return renvoi si oui ou non le client est deja dans la hashMap
     */
    public static boolean isRegisteredClientByMail(String mail){
        for(Map.Entry<String, Client> entry : clients.entrySet()){
            if (mail.equals(entry.getValue().getMail())){
                return true;
            }
        }
        return false;
    }

    public static boolean isRegisteredClientById(String clientID){
        for(Map.Entry<String, Client> entry : clients.entrySet()){
            if (clientID.equals(entry.getValue().getID())){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param mail adresse mail du client dont on veut avoir son objet
     * @return retourne l'objet correspondant à l'adresse mail sinon null
     */
    public static Client getClientByMail(String mail){
        for(Map.Entry<String, Client> entry : clients.entrySet()){
            if (mail.equals(entry.getValue().getMail())){
                return entry.getValue();
            }
        }
        return null;
    }
    /**
     *
     * @param id identifiant du client dont on veut avoir l'objet
     * @return retourne le client ou null
     */
    public static Client getClientById(String id){
        for (Map.Entry<String, Client> entry : clients.entrySet()){
            if (id.equals(entry.getKey())){
                return entry.getValue();
            }
        }
        return null;
    }
    /**
     *
     * @return retourne la map de tous les clients
     */
    public static Collection<Client> getAllClients(){
        return Database.clients.values();
    }
    // endregion
}
