package webservices.interfaces;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Interface pour gestion clientele
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public interface ICustomersManager {

    @GET
    Response getCustomerList();

    @Path("/{ref}")
    @GET
    Response getCustomerDetails(@PathParam("ref") String customerRef);

    @POST
    Response insertCustomer(@QueryParam("nickname") String nickname, @QueryParam("name") String name, @QueryParam("email") String email);

    @PUT
    @Path("/{ref}/nickname/{nickname}")
    Response updateCustomerNickname(@PathParam("ref") String customerRef, @PathParam("nickname") String nickname);

    @PUT
    @Path("/{ref}/name/{name}")
    Response updateCustomerName(@PathParam("ref") String customerRef, @PathParam("name") String name);

    @PUT
    @Path("/{ref}/email/{email}")
    Response updateCustomerEmail(@PathParam("ref") String customerRef, @PathParam("email") String email);

    @DELETE
    @Path("/{ref}")
    Response deleteCustomer(@PathParam("ref") String customerRef);

}
