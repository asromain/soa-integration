package webservices.services;

import webservices.backend.Database;
import webservices.classes.Customer;
import webservices.interfaces.ICustomersManager;

import javax.ws.rs.core.Response;

/**
 * Web service : registre des clients
 */

public class CustomersManager implements ICustomersManager {

    /**
     * Retourne la liste des clients
     * @return
     */
    public Response getCustomerList()
    {
        if(Database.getDbCustomers().size() == 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(Database.listCustomers().toArray(new Customer[Database.listCustomers().size()])).build();
    }

    /**
     * Retourne l'info sur un client
     * @param customerRef
     * @return
     */
    public Response getCustomerDetails(String customerRef)
    {
        if(Database.getCustomer(customerRef) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Customer c = Database.getCustomer(customerRef);
        return Response.ok().entity(c).build();
    }

    /**
     * Insere un nouveau client
     * @param nickname
     * @param name
     * @param email
     * avec query http://localhost:8181/cxf/services/customers/add?nickname=yooooo&name=yaaaaa&email=kaka@mail.com
     * avec param http://localhost:8181/cxf/services/customers/add/yooooo/yaaaaa/yayo@mail.com
     * @return
     */
    public Response insertCustomer(String nickname, String name, String email)
    {
        Database.insertCustomer(new Customer(nickname, name, email));
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * Mise a jour du prenom
     * @param customerRef
     * @param nickname
     * @return
     */
    public Response updateCustomerNickname(String customerRef, String nickname)
    {
        if(Database.getCustomer(customerRef) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Customer c = Database.getCustomer(customerRef);
        c.setNickname(nickname);
        Database.initRegistreClient(c);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    /**
     * Mise a jour du nom
     * @param customerRef
     * @param name
     * @return
     */
    public Response updateCustomerName(String customerRef, String name) {
        if(Database.getCustomer(customerRef) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Customer c = Database.getCustomer(customerRef);
        c.setName(name);
        Database.initRegistreClient(c);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    /**
     * Mise a jour de l'email
     * @param customerRef
     * @param email
     * @return
     */
    public Response updateCustomerEmail(String customerRef, String email) {
        if(Database.getCustomer(customerRef) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Customer c = Database.getCustomer(customerRef);
        c.setEmail(email);
        Database.initRegistreClient(c);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    /**
     * Suppression d'un client
     * @param customerRef
     * @return
     */
    public Response deleteCustomer(String customerRef)
    {
        if(Database.getCustomer(customerRef) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Database.deleteCustomer(customerRef);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}