package webservices;

import webservices.backend.Database;
import webservices.classes.Order;
import webservices.classes.Tracking;

/**
 * Pour rapides tests
 */
public class Main {

    public static void main(String[] args) {

        String orderId = "3";

        Order o = Database.getOrder(orderId);
        String trackingCode = o.getTrackingCode();

        Tracking t = Database.getTracking(trackingCode);
        System.out.println(t.toString());

    }

}
