package webservices.services;

import webservices.backend.Database;
import webservices.classes.CustomizationParameters;
import webservices.classes.Order;
import webservices.classes.Product;
import webservices.classes.Tracking;
import webservices.interfaces.IOrdersManager;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Web service :  les commandes
 */

public class OrdersManager implements IOrdersManager {

    /**
     * Retourne la liste des commandes
     * @return
     */
    public Response getAllOrders()
    {
        if(Database.getDbOrders().size() == 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(Database.listOrders().toArray(new Order[Database.listOrders().size()])).build();
    }

    /**
     * Retourne les infos sur une commande
     * @param refOrder
     * @return
     */
    public Response getDetailsOnOrder(String refOrder)
    {
        if(Database.getOrder(refOrder) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Order ref = Database.getOrder(refOrder);
        return Response.ok().entity(ref).build();
    }

    /**
     * Obtenir les commandes d'un client
     * @param customerId
     * @return
     */
    public Response getOrdersForCustomer(String customerId)
    {
        List<Order> listeOfOrders = new ArrayList<Order>();

        for(int i = 0; i < Database.getDbOrders().size(); i++)
        {
            Order o = Database.getOrder(String.valueOf(i));
            if(o.getRefCustomer().equals(customerId)) {
                listeOfOrders.add(Database.getOrder(String.valueOf(i)));
            }
        }

        return Response.ok(listeOfOrders.toArray(new Order[listeOfOrders.size()])).build();
    }

    /**
     * Creation d'une nouvelle commande depuis le stock
     * @param customerId
     * @param itemId
     * @return
     */
    public Response createNewOrderFromStock(String customerId, String itemId) {
        if (!Database.getDbCustomers().containsKey(customerId)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        if (!Database.getDbProducts().containsKey(itemId)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        Product pOrdered = Database.getDbProducts().get(itemId);
        Tracking tOrdered = new Tracking("in-preparation");
        Database.getDbProducts().remove(pOrdered.getRefProduct());
        Database.initDbTracking(tOrdered);
        Order created = new Order(pOrdered, customerId, tOrdered.getTrackingCode(), "-1");
        Database.initDbOrders(created);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    /**
     * Creation d'une commande avec personnalisation
     * @param customerId
     * @param cramponsId
     * @param colorId
     * @param size
     * @return
     */
    public Response createNewCustomOrder(String customerId,
                                         String cramponsId,
                                         String colorId,
                                         String size) {
        if (!Database.getDbCustomers().containsKey(customerId)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        if (!CustomizationParameters.cleats.containsKey(cramponsId)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        if (!CustomizationParameters.colors.containsKey(colorId)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        if (!CustomizationParameters.sizes.containsKey(size)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        Product pOrdered = new Product(CustomizationParameters.cleats.get(cramponsId),
                CustomizationParameters.colors.get(colorId),
                CustomizationParameters.sizes.get(size), "150");
        Tracking tOrdered = new Tracking("in_preparation");
        Database.initDbTracking(tOrdered);
        Order created = new Order(pOrdered, customerId, tOrdered.getTrackingCode(), "-1");
        Database.initDbOrders(created);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    /**
     * Supprimer une commande
     * @param orderId
     * @return
     */
    public Response deleteOrder(String orderId) {
        if(Database.getOrder(orderId) == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        Order o = Database.getDbOrders().get(orderId);
        String trackingCode = o.getTrackingCode();
        Database.deleteTracking(trackingCode);
        Database.deleteOrder(orderId);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
