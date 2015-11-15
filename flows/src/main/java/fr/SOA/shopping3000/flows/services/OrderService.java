package fr.SOA.shopping3000.flows.services;

import fr.SOA.shopping3000.flows.utils.Database;

/**
 * Created by max on 14/11/2015.
 */
public class OrderService {


    public String createOrder(String orderClientId, String orderAddress) {

        String orderId = Database.genUID();

        //fill database
        Database.createOrder(orderId, orderClientId, orderAddress);

        return orderId;
    }


}
