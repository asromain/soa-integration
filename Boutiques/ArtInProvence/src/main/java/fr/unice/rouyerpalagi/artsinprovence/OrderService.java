package fr.unice.rouyerpalagi.artsinprovence;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Service Class to manage the order part of the process
 */
@Path("/orders")    // Path of the service
@Produces(MediaType.APPLICATION_JSON)   // Return JSON format
public class OrderService {

    // region PROPERTIES
    // endregion

    // region GETTERS / SETTERS
    // endregion

    // region CONSTRUCTORS
    // endregion

    // region METHODS

    @POST
    @Consumes(MediaType.APPLICATION_JSON)   // Consume JSON format
    public Response registerOrder (Order order) {
        // Client doesn't exist
        if (order.getClientID() == null || !Database.isRegisteredClientById(order.getClientID())) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("The client reference as " + order.getClientID() + " is not registered yet.")
                    .build();
        }

        // Check delivery address
        if (order.getDeliveryAddress() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("You can't order without give the delivery address.").build();
        }

        String errorMessage = "The paintings: ";
        boolean error = false;
        String orderID;

        if (order.getPaintingArray() != null) {
            for (String paintingID : order.getPaintingArray()) {
                // Not available
                if (paintingID == null || !Database.isPaintingAvailable(paintingID)) {
                    error = true;
                    errorMessage += "\n " + paintingID;
                }
            }
            errorMessage += "\n are not available.";
            if (error) {
                return Response.status(Response.Status.CONFLICT).entity(errorMessage).build();
            }

            orderID = Database.createOrder(order.getClientID(), order.getPaintingArray(), order.getDeliveryAddress());

            return Response.created(URI.create("http://localhost:8181/cxf/arts/orders/" + orderID)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("You can't order without select paintings.").build();
        }
    }

    @POST
    @Path("/special")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerSpecialOrder (Order order) {
        // Client doesn't exist
        if (order.getClientID() == null || !Database.isRegisteredClientById(order.getClientID())) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("The client reference as " + order.getClientID() + " is not registered yet.")
                    .build();
        }
        // Check url for order
        if (order.getSpecialUrl() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("You can't order without give the url of your picture.").build();
        }
        // Check delivery address
        if (order.getDeliveryAddress() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("You can't order without give the delivery address.").build();
        }

        String orderID = Database.createOrder(order.getClientID(), order.getSpecialUrl(), order.getDeliveryAddress());
        Database.createSpecialPainting(order.getSpecialUrl(), order.getClientID());

        return Response.status(Response.Status.CREATED).entity(order.toString()).build();
    }

    @PUT
    @Path("/{order_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrderDetails(@PathParam("order_id") String orderID, Order details) {
        if (details == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        // Check existing product
        Order toUpdate = Database.findInOrders(orderID);
        if (toUpdate == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(orderID).build();
        }

        toUpdate.setDeliveryAddress(details.getDeliveryAddress());
        toUpdate.setStatus(details.getStatus());

        return Response.ok().build();
    }

    @GET
    @Path("/{order_id}")
    public Response getOrderDetails(@PathParam("order_id") String orderID) {
        Order order = Database.findInOrders(orderID);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(order).build();
    }

    // endregion
}
