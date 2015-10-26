package webservices.classes;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="tracking")
public class Tracking {

    private String trackingCode;    // T0
    private String status;          // inpreparation, indelivery, delivered

    private static int refTracking = 0;

    public Tracking() {}

    public Tracking(String status) {
        this.trackingCode = "T" + String.valueOf(refTracking);
        this.status = status;
        refTracking++;
    }

    public String toString() { return trackingCode + " " + status; }

    /* GETTERS */
    public String getTrackingCode() {
        return trackingCode;
    }
    public String getStatus() {
        return status;
    }

    /* SETTERS */
    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
