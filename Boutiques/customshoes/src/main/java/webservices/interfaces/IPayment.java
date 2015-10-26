package webservices.interfaces;

import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/payment")
@Produces(MediaType.APPLICATION_JSON)
public interface IPayment {

    @Path("/{orderId}")
    @GET
    Response getPaymentInfoOnOrder(@PathParam("orderId") String orderId);

    @Path("/{orderId}")
    @POST
    Response makePaymentForOrder(@PathParam("orderId") String orderId, @QueryParam("payment") String paymentType,
                                 @QueryParam("payerFirstName") String payerFirstName,
                                 @QueryParam("payerLastName") String payerLastName,
                                 @QueryParam("payerAddress") String payerAddress);

}
