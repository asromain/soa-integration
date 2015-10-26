package webservices.interfaces;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Interface pour la personnalisation des articles
 */

@Path("/custom")
@Produces(MediaType.APPLICATION_JSON)
public interface ICustomization {

    @Path("/cleats")
    @GET
    Response getOptionsForCleats();

    @Path("/cleats/{cramponId}")
    @GET
    Response getDetailsOnCleats(@PathParam("cramponId") String cramponId);

    @Path("/colors")
    @GET
    Response getOptionsForColors();

    @Path("/colors/{colorId}")
    @GET
    Response getDetailsOnColors(@PathParam("colorId") String colorId);

    @Path("/sizes")
    @GET
    Response getOptionsForSizes();

    @Path("/sizes/{sizeId}")
    @GET
    Response getDetailsOnSizes(@PathParam("sizeId") String sizeId);
}
