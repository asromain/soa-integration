package webservices.classes;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="payment")
public class Payment {

    private String paymentId;
    private String paymentType;
    private String payerFirstName;
    private String payerLastName;
    private String payerAddress;
    private static int ref = 0;

    public Payment() {}

    public Payment(String paymentType, String payerFirstName, String payerLastName, String payerAddress) {

        this.paymentType = paymentType;
        this.payerFirstName = payerFirstName;
        this.payerLastName = payerLastName;
        this.payerAddress = payerAddress;

        paymentId = "P" + String.valueOf(ref);
        ref++;
    }

    public String getPaymentId() {
        return paymentId;
    }
    public String getPaymentType() {
        return paymentType;
    }
    public String getPayerFirstName() {
        return payerFirstName;
    }
    public String getPayerLastName() {
        return payerLastName;
    }
    public String getPayerAddress() {
        return payerAddress;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    public void setPayerFirstName(String payerFirstName) {
        this.payerFirstName = payerFirstName;
    }
    public void setPayerLastName(String payerLastName) {
        this.payerLastName = payerLastName;
    }
    public void setPayerAddress(String payerAddress) {
        this.payerAddress = payerAddress;
    }

}
