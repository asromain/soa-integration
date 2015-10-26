package customshirt;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

@Path("/return")
@Produces(MediaType.APPLICATION_JSON)
public class ReturnService {

	@Path("/{id_user}/{id_command}")
	@POST
	public Response postReturn(@PathParam("id_user") Integer idUser, @PathParam("id_command") Integer idCommand,
			@QueryParam("id_personalisation") List<Integer> idsPersonalisation) {
		// TODO update rendre id_Personalisation facultatif pour pouvoir
		// renvoyer TOUTE la commande d'un coups ?
		Command currentCommand = Database.getCommand(idCommand);

		if (!idUser.equals(currentCommand.getUserId())) {
			JSONObject resultError = new JSONObject();
			resultError.append("error", "This is not your command");
			return Response.status(Status.FORBIDDEN).entity(resultError.toString(2)).build();
		}

		// TODO test si moins de 30 jours

		Command newCommand = new Command(Database.getNextCommandId(), idUser);

		for (Integer idPerso : idsPersonalisation) {

			Personalisation curPerso = currentCommand.getPersonalisation(idPerso);

			if (curPerso.equals(null)) {
				JSONObject resultError = new JSONObject();
				resultError.append("error", "This personalisation is not in this command");
				resultError.append("id_personalisation", idPerso);
				return Response.status(Status.FORBIDDEN).entity(resultError.toString(2)).build();
			}
			newCommand.add(curPerso);
		}
		newCommand.setStatus("paid");

		Database.addCommand(newCommand);

		Return newReturn = new Return(Database.getNextReturnId(), idCommand, newCommand.getId(), idsPersonalisation);

		Database.addReturn(newReturn);

		JSONObject result = new JSONObject();
		result.append("id_new_Command", newCommand.getId());
		return Response.ok().entity(result.toString(2)).build();
	}

}
