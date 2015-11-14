package customshirt;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

@Path("/payment")
@Produces(MediaType.APPLICATION_JSON)
public class PaymentService {

	@Path("/{id_payment}")
	@GET
	public Response getPayment(@PathParam("id_payment") String idPayment) {
		// if user valid && command valid && command of user
		Payment currentPayment = Database.getPayment(idPayment);
		// Proceed payment
		JSONObject result = new JSONObject();
		result.append("id_payment", currentPayment.getId());
		result.append("id_command", currentPayment.getCommandId());
		result.append("numCb", currentPayment.getNumCb());
		result.append("crypto", currentPayment.getCrypto());
		result.append("dateEnd", currentPayment.getDateEnd());
		return Response.ok().entity(result.toString(2)).build();
	}

	@Path("")
	@POST
	public Response putPayment(@QueryParam("id_user") String idUser, @QueryParam("id_command") Integer commandId,
			@QueryParam("numCb") String numCb, @QueryParam("crypto") String crypto,
			@QueryParam("dateEnd") String dateEnd) {
		// if user valid && command valid && command of user
		Command currentCommand = Database.getCommand(commandId);

		if (!idUser.equals(currentCommand.getUserId())) {
			JSONObject resultError = new JSONObject();
			resultError.append("error", "This is not your command");
			return Response.status(Status.FORBIDDEN).entity(resultError.toString(2)).build();
		}

		Payment newPayment = new Payment(Database.getNextPaymentId(), commandId, idUser, numCb, crypto, dateEnd);
		//currentCommand.setStatus("paid");
		
		
		Database.addPayment(newPayment);
		
		// il faut que notre comptable appuis sur un bouton pour :
		// creer delivery
		// set status command a paid
		// et donc set une date_paid dans command pour que l'on puisse tester un non retour si plus de 30 jours
		
		// il faut qu'un type dans les entrepots puisse :
		// set status delivery a "envoy�"
		// 
		
		// Proceed payment
		JSONObject result = new JSONObject();
		result.append("id_payment", newPayment.getId());
		return Response.ok().entity(result.toString(2)).build();
	}
}
