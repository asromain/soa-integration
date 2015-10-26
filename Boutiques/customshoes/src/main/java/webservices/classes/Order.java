package webservices.classes;

/* produit en stock et produit personnalisé même "format", ont tous crampons + couleurs + pointures */
/* 1C custom
 * 1  normal */

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="order")
public class Order {

    private String refOrder;
    private Product product;
    private String refCustomer;
    private String trackingCode;
    private String paymentId;

    private static int refId = 0;

    public Order() {}

    public Order(Product product, String refCustomer, String trackingCode, String paymentId) {
        this.refOrder = String.valueOf(refId);
        this.product = product;
        this.refCustomer = refCustomer;
        this.trackingCode = trackingCode;
        this.paymentId = paymentId;
        refId++;
    }

    public String toString() {
        return refOrder + " " + product + " " + refCustomer + " " + trackingCode + " " + paymentId;
    }


    /* GETTERS */
    public String getRefOrder() {
        return refOrder;
    }
    public Product getProduct() {
        return product;
    }
    public String getRefCustomer() {
        return refCustomer;
    }
    public String getTrackingCode() {
        return trackingCode;
    }
    public String getPaymentId() { return paymentId; }

    /* SETTERS */
    public void setRefOrder(String refOrder) {
        this.refOrder = refOrder;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public void setRefCustomer(String refCustomer) {
        this.refCustomer = refCustomer;
    }
    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
}