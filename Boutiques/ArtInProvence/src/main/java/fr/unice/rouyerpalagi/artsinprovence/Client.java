package fr.unice.rouyerpalagi.artsinprovence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Class to simulate a Client inside the DataBase.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client {

    // region PROPERTIES


    @JsonProperty("id")
    private String ID;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("mail")
    private String mail;

    // endregion

    // region GETTERS / SETTERS

    public String getID() {
        return ID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    // endregion

    // region CONSTRUCTORS
    /**
     * Empty constructor, necessary for Jackson usage
     */
    public Client() {}

    public Client(String lastName, String firstName, String address, String mail) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.mail = mail;
        this.ID = UUID.randomUUID().toString();
    }
    // endregion

    // region METHODS
    // endregion
}
