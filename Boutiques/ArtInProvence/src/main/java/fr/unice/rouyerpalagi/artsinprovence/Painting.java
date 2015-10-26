package fr.unice.rouyerpalagi.artsinprovence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Represent a painting in the system.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Painting {

    // region PROPERTIES

    @JsonProperty("painting_id")
    private String ID;

    @JsonProperty("description")
    private String description;

    @JsonProperty("available")
    private boolean available;

    @JsonProperty("price")
    private Integer price;
    // endregion

    // region GETTERS / SETTERS

    public String getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null)
            this.description = description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        if (price != null) {
            this.price = price;
        }
    }

    // endregion

    // region CONSTRUCTORS

    /**
     * Empty constructor, necessary for Jackson usage
     */
    public Painting() {}

    public Painting(String description, Integer price) {
        this.description = description;
        this.price = price;
        this.available = true;
        this.ID = UUID.randomUUID().toString();
    }
    // endregion

    // region METHODS
    // endregion

}
