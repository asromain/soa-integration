package fr.unice.rouyerpalagi.artsinprovence;

/**
 * Created by Florian Rouyer on 28/09/2015.
 */

/**
 *
 *  structuration de la r�ponse Get client ?
 *
 */



import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.sql.DataTruncation;
import java.util.Collection;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
public class ClientService {

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response createClient(String client) {
        //verification de la presence de parametres
        if (client == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        JSONObject jclient = new JSONObject(client);

        String lastName = jclient.getString("lastname");
        String firstName = jclient.getString("firstname");
        String address = jclient.getString("address");
        String mail = jclient.getString("mail");

        if(Database.isRegisteredClientByMail(mail)) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        String id = Database.createClient(lastName, firstName, address, mail);
        return Response.created(URI.create("http://localhost:8181/cxf/arts/clients/"+id)).build();
    }

    @Path("/{id}")
    @GET
    public Response getClient(@PathParam("id") String id) {
        //verification de la presence de parametres
        if (id == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        //verification de la presence dans la base
        if(Database.getClientById(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        JSONObject client = new JSONObject();
        client.put("id", Database.getClientById(id).getID());
        client.put("firstname", Database.getClientById(id).getFirstName());
        client.put("lastname", Database.getClientById(id).getLastName());
        client.put("address", Database.getClientById(id).getAddress());
        client.put("mail", Database.getClientById(id).getMail());
        return Response.ok().entity(client.toString()).build();
    }

    @GET
    public Response getClientList(){
        Collection<Client> clients = Database.getAllClients();
        JSONArray result = new JSONArray();
        for(Client c: clients) {
            JSONObject clientPartial = new JSONObject();
            clientPartial.put("lastName", c.getLastName());
            clientPartial.put("firstName", c.getFirstName());
            clientPartial.put("details", "http://localhost:8181/cxf/arts/clients/"+c.getID());
            result.put(clientPartial);
        }
        return Response.ok().entity(result.toString()).build();
    }
    @Path("/{client}")
    @PUT
    public Response updateClient(@PathParam("client") String clientID,String client){
        if (client == null || client == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        JSONObject jclient = new JSONObject(client);

        String lastName = jclient.getString("lastname");
        String firstName = jclient.getString("firstname");
        String address = jclient.getString("address");
        String mail = jclient.getString("mail");

        if(Database.getClientById(clientID) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Database.updateClient(Database.getClientById(clientID), lastName, firstName, address, mail);
        return Response.created(URI.create("http://localhost:8181/cxf/arts/clients/"+clientID)).build();
    }

    @Path("/{id}")
    @DELETE
    public Response deleteClient(@PathParam("id") String id) {
        if(Database.getClientById(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Database.deleteClient(id);
        return Response.ok().build();
    }

}
