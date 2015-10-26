package webservices.interfaces;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Interface pour la gestion des commandes
 */

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public interface IOrdersManager {

    @GET
    Response getAllOrders();

    @Path("/{orderId}")
    @GET
    Response getDetailsOnOrder(@PathParam("orderId") String refOrder);

    @Path("/customer/{customerId}")
    @GET
    Response getOrdersForCustomer(@PathParam("customerId") String customerId);

    @Path("/stock")
    @POST
    Response createNewOrderFromStock(@QueryParam("customerId") String customerId,
                                     @QueryParam("itemId") String itemId);

    @Path("/custom")
    @POST
    Response createNewCustomOrder(@QueryParam("customerId") String customerId,
                                  @QueryParam("cramponsId") String cramponsId,
                                  @QueryParam("colorId") String colorId,
                                  @QueryParam("size") String size);

    @Path("/{orderId}")
    @DELETE
    Response deleteOrder(@PathParam("orderId") String orderId);
}
