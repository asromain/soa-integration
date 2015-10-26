package webservices.services;

import webservices.backend.Database;
import webservices.classes.Order;
import webservices.classes.Payment;
import webservices.interfaces.IPayment;

import javax.ws.rs.core.Response;


public class PaymentManager implements IPayment {

    /**
     * Obtenir les info de paiment sur une commande
     * @param orderId
     * @return
     */
    public Response getPaymentInfoOnOrder(String orderId) {
        if (!Database.getDbOrders().containsKey(orderId)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            Order o = Database.getOrder(orderId);
            Payment result = Database.getDbPayment().get(o.getPaymentId());
            return Response.ok().entity(result).build();
        }
    }

    public Response makePaymentForOrder(String orderId,
                                        String paymentType,
                                        String payerFirstName,
                                        String payerLastName,
                                        String payerAddress) {
        if (!Database.getDbOrders().containsKey(orderId)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } if (Database.getOrder(orderId).getPaymentId() != "-1") {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Order payed = Database.getOrder(orderId);
        Payment result = new Payment(paymentType, payerFirstName, payerLastName, payerAddress);
        payed.setPaymentId(result.getPaymentId());
        Database.initDbPayment(result);
        return Response.ok().entity(result).build();
    }
}
