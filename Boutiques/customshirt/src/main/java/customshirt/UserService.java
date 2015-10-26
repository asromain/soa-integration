package customshirt;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {

	@Path("/{id_user}")
	@GET
	public Response getUser(@PathParam("id_user") Integer idUser) {
		User currentUser = Database.getUser(idUser);

		JSONObject result = new JSONObject();
		result.append("id", currentUser.getId());
		result.append("user_name", currentUser.getUserName());
		return Response.ok().entity(result.toString(2)).build();
	}

	@Path("/{user_name}")
	@POST
	public Response postUser(@PathParam("user_name") String userName) {

		User newUser = new User(Database.getNextUserId(), userName);

		Database.addUser(newUser);

		JSONObject result = new JSONObject();
		result.append("id_user", newUser.getId());
		return Response.ok().entity(result.toString(2)).build();
	}

	@Path("/{id_user}/{new_name}")
	@PUT
	public Response putUser(@PathParam("id_user") Integer idUser,
			@PathParam("new_name") String newName) {
		User currentUser = Database.getUser(idUser);
		
		currentUser.setUserName(newName);
		
		JSONObject result = new JSONObject();
		result.append("id", currentUser.getId());
		result.append("user_name", currentUser.getUserName());
		return Response.ok().entity(result.toString(2)).build();
	}

}
