package webservices.interfaces;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Interface pour le suivi de livraison
 */

@Path("/tracking")
@Produces(MediaType.APPLICATION_JSON)
public interface ITracking {

    @GET
    Response getAllTracking();

    @Path("/{trackingCode}")
    @GET
    Response getDetailsOnTracking(@PathParam("trackingCode") String trackingCode);

    @Path("/{trackingCode}/{status}")
    @PUT
    Response updateTracking(@PathParam("trackingCode") String trackingCode, @PathParam("status") String status);

    @Path("/{trackingCode}")
    @DELETE
    Response deleteTracking(@PathParam("trackingCode") String trackingCode);

    @Path("/order/{orderId}")
    @GET
    Response getStateOfDelivery(@PathParam("orderId") String orderId);
}
