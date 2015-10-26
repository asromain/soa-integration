package webservices.services;

import webservices.backend.Database;
import webservices.classes.CustomizationParameters;
import webservices.classes.Order;
import webservices.classes.Product;
import webservices.classes.Tracking;
import webservices.interfaces.IStockManager;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;


public class StockManager implements IStockManager {

    /**
     * Obtenir le stock disponible
     * @return
     */
    public Response getAvailableStock() {
        if (Database.getDbProducts().size() == 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.ok(Database.listProducts().toArray(new Product[Database.listProducts().size()])).build();
        }
    }

    /**
     * Obtenir un detail sur un produit
     * @param itemId
     * @return
     */
    public Response getDetailsOnProduct(String itemId) {
        Product item = Database.getProduct(itemId);
        if (item == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok().entity(item).build();
        }
    }

    public Response addProductToStock(String cramponsId, String colorId, String size, String price) {
        if (!CustomizationParameters.cleats.containsKey(cramponsId)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        if (!CustomizationParameters.colors.containsKey(colorId)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        if (!CustomizationParameters.sizes.containsKey(size)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        Product pCreated = new Product(CustomizationParameters.cleats.get(cramponsId),
                CustomizationParameters.colors.get(colorId),
                CustomizationParameters.sizes.get(size), price);
        Database.getDbProducts().put(pCreated.getRefProduct(), pCreated);
        return Response.ok().entity(pCreated).build();
    }
}
