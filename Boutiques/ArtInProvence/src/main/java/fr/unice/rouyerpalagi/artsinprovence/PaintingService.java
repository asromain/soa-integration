package fr.unice.rouyerpalagi.artsinprovence;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;

/**
 * PaintingService is a class designed to manage services relatives to paintings
 */
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class PaintingService {
    // region PROPERTIES
    // endregion

    // region GETTERS / SETTERS
    // endregion

    // region CONSTRUCTORS
    // endregion

    // region METHODS

    /**
     * Return all the catalog of painting
     * @return
     */
    @GET
    public Response getCatalog() {
        Collection<Painting> catalog = Database.getAllCatalog();
        JSONArray result = new JSONArray();

        for (Painting painting : catalog) {
            // Do not return special painting
            if (SpecialPainting.class.isAssignableFrom(painting.getClass())) {
                continue;
            }

            JSONObject customPaint = new JSONObject();
            customPaint.put("price", painting.getPrice());
            customPaint.put("description", painting.getDescription());
            customPaint.put("available", painting.isAvailable());
            String ressourceURL = "http://localhost:8181/cxf/arts/products/" + painting.getID();
            customPaint.put("url", ressourceURL);
            result.put(customPaint);



        }
        return Response.ok().entity(result.toString()).build();
    }

    @GET
    @Path("/{painting_id}")
    public Response getPaintingDetails(@PathParam("painting_id") String paintingID) {
        Painting painting = Database.findInCatalog(paintingID);
        if (painting == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(painting).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPainting(Painting painting) {
        if (painting == null || painting.getDescription() == null || painting.getPrice() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad or missing parameter(s) description and/or price.").build();
        }

        Integer price = painting.getPrice() ;
        String description = painting.getDescription();
        // Save new product in Database
        String paintingID = Database.createPainting(description, price);

        return Response.created(URI.create("http://localhost:8181/cxf/arts/products/" + paintingID)).build();
    }

    @PUT
    @Path("/{painting_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePaintingDetails(@PathParam("painting_id") String paintingID, Painting details) {
        if (details == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        // Check existing product
        Painting toUpdate = Database.findInCatalog(paintingID);
        if (toUpdate == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(paintingID).build();
        }

        Integer price = details.getPrice() ;
        String description = details.getDescription();
        boolean available = details.isAvailable();

        toUpdate.setAvailable(available);
        toUpdate.setPrice(price);
        toUpdate.setDescription(description);

        return Response.ok().build();
    }

    @PUT
    @Path("/special/{painting_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSpecialPainting(@PathParam("painting_id") String paintingID, SpecialPainting details) {
        if (details == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        // Check existing product
        SpecialPainting toUpdate = Database.findInSpecialCatalog(paintingID);
        if (toUpdate == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(paintingID).build();
        }

        toUpdate.setProgress(details.getProgress());

        return Response.ok().build();
    }

    @GET
    @Path("/special/{painting_id}")
    public Response getSpecialDetails(@PathParam("painting_id") String paintingID) {
        SpecialPainting painting = Database.findInSpecialCatalog(paintingID);
        if (painting == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(painting).build();
    }

    // endregion
}
