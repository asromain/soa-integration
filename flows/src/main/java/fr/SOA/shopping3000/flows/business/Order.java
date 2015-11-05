package fr.SOA.shopping3000.flows.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian Rouyer on 26/10/2015.
 */

public class Order implements Serializable {

    private String id;
    private String address;
    private String totPrice;

    private List<Order> orders;

    public Order() {
        this.orders = new ArrayList<Order>();
    }

    public Order(String id, String address, String totPrice) {
        this.id = id;
        this.address = address;
        this.totPrice = totPrice;
        this.orders = new ArrayList<Order>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(String shop) {
        this.totPrice = totPrice;
    }


    public String getCSVHeader() {
        return "id,address,price";
    }
    public String toCSV() {
        return this.id +","+ this.address +","+ this.totPrice;
    }
}
