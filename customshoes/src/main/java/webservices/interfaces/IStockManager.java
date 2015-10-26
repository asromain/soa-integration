package webservices.interfaces;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Interface pour la gestion du stock
 */

@Path("/stock")
@Produces(MediaType.APPLICATION_JSON)
public interface IStockManager {

    @GET
    Response getAvailableStock();

    @Path("/{itemId}")
    @GET
    Response getDetailsOnProduct(@PathParam("itemId") String itemId);

    @POST
    Response addProductToStock(@QueryParam("cramponsId") String cramponsId,
                               @QueryParam("colorId") String colorId,
                               @QueryParam("size") String size,
                               @QueryParam("price") String price);
}
