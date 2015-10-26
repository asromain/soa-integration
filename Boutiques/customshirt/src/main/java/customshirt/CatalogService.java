package customshirt;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("/catalog")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogService {

	@Path("/colors")
	@GET
	public Response getColors() {
		// TODO si color_value present et value correspond dans hashmap on
		// return son id sinon on return la list des id et values.
		Color colors = new Color();
		JSONObject result = new JSONObject();
		result.append("colors", colors.get());
		return Response.ok().entity(result.toString(2)).build();
	}

	@Path("/types")
	@GET
	public Response getTypes() {
		// TODO si type_value present et value correspond dans hashmap on return
		// son id sinon on return la list des id et values.
		Type types = new Type();
		JSONObject result = new JSONObject();
		result.append("types", types.get());
		return Response.ok().entity(result.toString(2)).build();
	}

	@Path("/symbols")
	@GET
	public Response getSymbols() {
		// TODO si symbol_value present et value correspond dans hashmap on
		// return son id sinon on return la list des id et values.
		Symbol symbols = new Symbol();
		JSONObject result = new JSONObject();
		result.append("symbols", symbols.get());
		return Response.ok().entity(result.toString(2)).build();
	}

}
