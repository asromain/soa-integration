package customshirt;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

@Path("/personalisation")
@Produces(MediaType.APPLICATION_JSON)
public class PersonalisationService {

	@Path("/{id_user}/{id_command}/{id_personalisation}")
	@GET
	public Response getPersonalisation(@QueryParam("id_user") Integer idUser,
			@PathParam("id_command") Integer idCommand, @PathParam("id_personalisation") Integer idPersonalisation) {

		Command currentCommand = Database.getCommand(idCommand);

		if (!idUser.equals(currentCommand.getUserId())) {
			JSONObject resultError = new JSONObject();
			resultError.append("error", "This is not your personalisation");
			return Response.status(Status.FORBIDDEN).entity(resultError.toString(2)).build();
		}

		Personalisation currentPersonalisation = currentCommand.getPersonalisation(idPersonalisation);
		if (currentPersonalisation.equals(null)) {
			JSONObject resultError = new JSONObject();
			resultError.append("error", "This personalisation is not in this command");
			return Response.status(Status.FORBIDDEN).entity(resultError.toString(2)).build();
		}
		JSONObject result = new JSONObject();
		result.append("id", currentPersonalisation.getId());
		result.append("color", currentPersonalisation.getColor());
		result.append("type", currentPersonalisation.getType());
		result.append("symbol", currentPersonalisation.getSymbol());
		return Response.ok().entity(result.toString(2)).build();
	}

	@Path("/{id_user}/{id_command}/{id_personalisation}/{color}/{type}/{symbol}")
	@PUT
	public Response putPersonalisation(@PathParam("id_user") Integer idUser, @PathParam("id_command") Integer idCommand,
			@PathParam("id_personalisation") Integer idPersonalisation, @PathParam("color") String color,
			@PathParam("type") String type, @PathParam("symbol") String symbol) {

		Command currentCommand = Database.getCommand(idCommand);

		if (!idUser.equals(currentCommand.getUserId())) {
			JSONObject resultError = new JSONObject();
			resultError.append("error", "This is not your personalisation");
			return Response.status(Status.FORBIDDEN).entity(resultError.toString(2)).build();
		}

		Personalisation currentPersonalisation = currentCommand.getPersonalisation(idPersonalisation);
		if (currentPersonalisation.equals(null)) {
			JSONObject resultError = new JSONObject();
			resultError.append("error", "This personalisation is not in this command");
			return Response.status(Status.FORBIDDEN).entity(resultError.toString(2)).build();
		}

		// TODO a faire en static
		Color c = new Color();
		Type t = new Type();
		Symbol s = new Symbol();

		if (c.get(color).equals(null)) {
			JSONObject resultError = new JSONObject();
			resultError.append("error", "This color is not in the catalog");
			return Response.status(Status.FORBIDDEN).entity(resultError.toString(2)).build();
		}
		if (t.get(type).equals(null)) {
			JSONObject resultError = new JSONObject();
			resultError.append("error", "This type is not in the catalog");
			return Response.status(Status.FORBIDDEN).entity(resultError.toString(2)).build();
		}
		if (s.get(symbol).equals(null)) {
			JSONObject resultError = new JSONObject();
			resultError.append("error", "This symbol is not in the catalog");
			return Response.status(Status.FORBIDDEN).entity(resultError.toString(2)).build();
		}

		currentPersonalisation.setColor(color);
		currentPersonalisation.setType(type);
		currentPersonalisation.setSymbol(symbol);

		// TODO on return l'objet ???
		JSONObject result = new JSONObject();
		result.append("id", currentPersonalisation.getId());
		result.append("color", currentPersonalisation.getColor());
		result.append("type", currentPersonalisation.getType());
		result.append("symbol", currentPersonalisation.getSymbol());
		return Response.ok().entity(result.toString(2)).build();
	}

	@Path("/{id_user}/{id_command}/{id_personalisation}")
	@DELETE
	public Response deletePersonalisation(@PathParam("id_user") Integer idUser,
			@PathParam("id_command") Integer idCommand, @PathParam("id_personalisation") Integer idPersonalisation) {

		Command currentCommand = Database.getCommand(idCommand);

		if (!idUser.equals(currentCommand.getUserId())) {
			JSONObject resultError = new JSONObject();
			resultError.append("error", "This is not your personalisation");
			return Response.status(Status.FORBIDDEN).entity(resultError.toString(2)).build();
		}

		Personalisation currentPersonalisation = currentCommand.getPersonalisation(idPersonalisation);
		if (currentPersonalisation.equals(null)) {
			JSONObject resultError = new JSONObject();
			resultError.append("error", "This personalisation is not in this command");
			return Response.status(Status.FORBIDDEN).entity(resultError.toString(2)).build();
		}

		currentCommand.delete(currentPersonalisation);

		JSONObject result = new JSONObject(); // TODO on renvoi quoi ?
		return Response.ok().entity(result.toString(2)).build();
	}
}