package webservices.services;

import webservices.classes.CustomizationParameters;
import webservices.interfaces.ICustomization;

import javax.ws.rs.core.Response;


public class CustomizationManager implements ICustomization {

    /**
     * Afficher les crampons disponibles
     * @return
     */
    public Response getOptionsForCleats()
    {
        if (CustomizationParameters.cleats.size() == 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok().entity(CustomizationParameters.getCleats().toString()).build();
    }

    /**
     * Detail d'un crampon
     * @param cramponId
     * @return
     */
    public Response getDetailsOnCleats(String cramponId)
    {
        if (!CustomizationParameters.cleats.containsKey(cramponId)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.ok().entity(CustomizationParameters.getDetailsOnCleats(cramponId).toString()).build();
        }
    }

    /**
     * Afficher les couleurs disponibles
     * @return
     */
    public Response getOptionsForColors()
    {
        if (CustomizationParameters.colors.size() == 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok().entity(CustomizationParameters.getColors().toString()).build();
    }

    /**
     * Detail des couleurs
     * @param colorId
     * @return
     */
    public Response getDetailsOnColors(String colorId) {
        if (!CustomizationParameters.colors.containsKey(colorId)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.ok().entity(CustomizationParameters.getDetailsOnColors(colorId).toString()).build();
        }
    }

    /**
     * Afficher les pointures disponibles
     * @return
     */
    public Response getOptionsForSizes()
    {
        if (CustomizationParameters.sizes.size() == 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok().entity(CustomizationParameters.getSizes().toString()).build();
    }

    /**
     * Detail des pointures
     * @param sizeId
     * @return
     */
    public Response getDetailsOnSizes(String sizeId)
    {
        if (!CustomizationParameters.sizes.containsKey(sizeId)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.ok().entity(CustomizationParameters.getDetailsOnSizes(sizeId).toString()).build();
        }
    }
}
