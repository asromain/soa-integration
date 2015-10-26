package customshirt;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("/delivery")
@Produces(MediaType.APPLICATION_JSON)
public class DeliveryService {
	
	@Path("/{id_delivery}")
	@GET
	public Response getDelivery(@PathParam("id_delivery") Integer id_delivery) {
		Delivery currentDelivery = Database.getDelivery(id_delivery);
		JSONObject result = new JSONObject();
		result.append("id_command", currentDelivery.getCommandId());
		result.append("address", currentDelivery.getAddress());
		result.append("status", currentDelivery.getStatus());
		return Response.ok().entity(result.toString(2)).build();
	}
	
	@Path("")
	@POST
	public Response postDelivery(@QueryParam("id_user") Integer idUser,
			                    @QueryParam("id_command") Integer commandId,
								@QueryParam("address") String address,
								@QueryParam("status") String status) {
		//if user valid && command valid && command of user
		Delivery currentDelivery = new Delivery(Database.getNextDeliveryId(),commandId, address);
		Database.addDelivery(currentDelivery);
		//Proceed payment
		JSONObject result = new JSONObject();
		result.append("id_delivery", currentDelivery.getId());
		return Response.ok().entity(result.toString(2)).build();
	}
	
	@Path("/{id_delivery}")
	@PUT
	public Response putDelivery(@PathParam("id_delivery") Integer id_delivery,
								@QueryParam("address") String address,
								@QueryParam("status") String status) {
		Delivery currentDelivery = Database.getDelivery(id_delivery);
		if(!address.equals("") || address != null){
			currentDelivery.setAddress(address);
		}
		if(!status.equals("") || status != null){
			currentDelivery.setStatus(status);
		}
		JSONObject result = new JSONObject();
		result.append("id_delivery", currentDelivery.getId());
		return Response.ok().entity(result.toString(2)).build();
	}
}
