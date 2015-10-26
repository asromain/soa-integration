package fr.unice.rouyerpalagi.artsinprovence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represent an order in the system
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    public enum OrderStatus {
        NEW, // Nouvelle commande
        WAITING_VALIDATION, // En attente de validation (paiement)
        IN_PROGRESS, // En cours de progression
        CANCELED, // Annulée
        FINISH // Livrée
    }

    // region PROPERTIES
    @JsonProperty("order_id")
    private String ID;

    @JsonProperty("client_id")
    private String clientID;

    @JsonProperty("painting_array")
    private ArrayList<String> paintingArray;

    @JsonProperty("address")
    private String deliveryAddress;

    @JsonProperty("status")
    private OrderStatus status;

    @JsonProperty("special_url")
    private String specialUrl;
    // endregion

    // region GETTERS / SETTERS

    public String getID() {
        return ID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public ArrayList<String> getPaintingArray() {
        return paintingArray;
    }

    public void setPaintingArray(ArrayList<String> paintingArray) {
        this.paintingArray = paintingArray;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getSpecialUrl() {
        return specialUrl;
    }

    public void setSpecialUrl(String specialUrl) {
        if (specialUrl != null)
            this.specialUrl = specialUrl;
    }

    // endregion

    // region CONSTRUCTORS

    /**
     * Empty constructor, necessary for Jackson usage
     */
    public Order() {}

    public Order(String clientID, ArrayList<String> paintingArray, String deliveryAddress) {
        this.clientID = clientID;
        this.paintingArray = paintingArray;
        this.deliveryAddress = deliveryAddress;
        this.status = OrderStatus.NEW;
        this.ID = UUID.randomUUID().toString();
    }

    public Order(String clientID, String specialUrl, String deliveryAddress) {
        this.clientID = clientID;
        this.deliveryAddress = deliveryAddress;
        this.status = OrderStatus.NEW;
        this.specialUrl = specialUrl;
        this.ID = UUID.randomUUID().toString();
    }
    // endregion

    // region METHODS
    // endregion
}
