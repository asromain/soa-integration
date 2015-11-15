package fr.SOA.shopping3000.flows.services;

import fr.SOA.shopping3000.flows.utils.Database;

/**
 * Created by max on 14/11/2015.
 */
public class ClientService {


    public String createClient(String client_name, String client_address) {
        String clientId = Database.genUID();

        //fill database
        Database.createClient(clientId, client_name, client_address);

        return clientId;
    }



}
