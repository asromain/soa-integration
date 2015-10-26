package customshirt;

import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("/command")
@Produces(MediaType.APPLICATION_JSON)
public class CommandService {

	@Path("/{id_command}")
	@GET
	public Response getCommand(@PathParam("id_command") Integer idCommand,
			@QueryParam("id_user") String idUser) {
		Command currentCommand = Database.getCommand(idCommand);
		if (!idUser.equals(currentCommand.getUserId())) {
			JSONObject resultError = new JSONObject();
			resultError.append("error", "This is not your command");
			return Response.status(Status.FORBIDDEN)
					.entity(resultError.toString(2)).build();
		}
		JSONObject result = new JSONObject();
		result.append("id_delivery", currentCommand.getDeliveryId());
		result.append("status", currentCommand.getStatus());
		JSONArray ids = new JSONArray();
		for (Personalisation currentP : currentCommand.getPersonalisations()) {
			ids.put(currentP.getId());
		}
		result.append("personalisationsIds", ids);
		return Response.ok().entity(result.toString(2)).build();
	}

	@Path("")
	@GET
	public Response getCommands(@QueryParam("id_user") String idUser) {
		List<Command> commands = Database.getCommands(idUser);
		JSONArray result = new JSONArray();
		for (Command currentCommand : commands) {
			JSONObject commandJson = new JSONObject();
			commandJson.append("id_delivery", currentCommand.getDeliveryId());
			commandJson.append("status", currentCommand.getStatus());
			JSONArray ids = new JSONArray();
			for (Personalisation currentP : currentCommand
					.getPersonalisations()) {
				ids.put(currentP.getId());
			}
			commandJson.append("personalisationsIds", ids);
			result.put(commandJson);
		}
		return Response.ok().entity(result.toString(2)).build();
	}

	@Path("")
	@POST
	public Response postCommand(@QueryParam("id_user") Integer idUser,
			@QueryParam("color") String color, @QueryParam("type") String type,
			@QueryParam("symbol") String symbol) {

		// if user valide
		Personalisation perso = new Personalisation(Database.getNextPersoId(),
				color, type, symbol);
		Command newCommand = new Command(Database.getNextCommandId(), idUser,
				perso);
		Database.addCommand(newCommand);
		JSONObject result = new JSONObject();
		result.append("id_command", newCommand.getId());
		return Response.ok().entity(result.toString(2)).build();
	}

	@Path("/{id_command}")
	@PUT
	public Response putCommand(@PathParam("id_command") Integer idCommand,
			@QueryParam("id_user") String idUser,
			@QueryParam("color") String color, @QueryParam("type") String type,
			@QueryParam("symbol") String symbol) {
		// if user valid && command valid && command of user
		Command currentCommand = Database.getCommand(idCommand);
		if (!idUser.equals(currentCommand.getUserId())) {
			JSONObject resultError = new JSONObject();
			resultError.append("error", "This is not your command");
			return Response.status(Status.FORBIDDEN)
					.entity(resultError.toString(2)).build();
		}
		Personalisation perso = new Personalisation(Database.getNextPersoId(),
				color, type, symbol);
		currentCommand.add(perso);
		JSONObject result = new JSONObject();
		result.append("id_command", currentCommand.getId());
		return Response.ok().entity(result.toString(2)).build();
	}

	@Path("/{id_command}")
	@DELETE
	public Response deleteCommand(@PathParam("id_command") Integer idCommand,
			@QueryParam("id_user") String idUser) {
		Command currentCommand = Database.getCommand(idCommand);
		if (!idUser.equals(currentCommand.getUserId())) {
			JSONObject resultError = new JSONObject();
			resultError.append("error", "This is not your command");
			return Response.status(Status.FORBIDDEN)
					.entity(resultError.toString(2)).build();
		}
		Database.deleteCommand(idCommand);
		return Response.ok().build();
	}
}
