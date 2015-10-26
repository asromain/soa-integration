package webservices.services;

import webservices.backend.Database;
import webservices.classes.Order;
import webservices.classes.Tracking;
import webservices.interfaces.ITracking;

import javax.ws.rs.core.Response;


public class TrackingManager implements ITracking {


    /**
     * Obtenir tous les suivis de commandes
     * @return
     */
    public Response getAllTracking()
    {
        if(Database.getDbTracking().size() == 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(Database.listTracking().toArray(new Tracking[Database.listTracking().size()])).build();
    }

    /**
     * Details sur un suivi de commande
     * @param trackingCode
     * @return
     */
    public Response getDetailsOnTracking(String trackingCode) {
        Tracking code = Database.getTracking(trackingCode);
        if (code == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().entity(code).build();
        }
    }

    /**
     * Mettre a jour un suivi de commande
     * @param trackingCode
     * @param status
     * @return
     */
    public Response updateTracking(String trackingCode, String status) {
        if(Database.getTracking(trackingCode) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Database.getTracking(trackingCode).setStatus(status);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    /**
     * Supprimer un suivi de commande
     * @param trackingCode
     * @return
     */
    public Response deleteTracking(String trackingCode) {
        if(Database.getTracking(trackingCode) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Database.deleteTracking(trackingCode);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    /**
     * Obtenir les informations de suivi de livraison d'une commande
     * @param orderId
     * @return
     */
    public Response getStateOfDelivery(String orderId)
    {
        if(!Database.getDbOrders().containsKey(orderId)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Order o = Database.getOrder(orderId);
        String trackingCode = o.getTrackingCode();
        Tracking t = Database.getTracking(trackingCode);

        return Response.ok().entity(t).build();
    }
}
