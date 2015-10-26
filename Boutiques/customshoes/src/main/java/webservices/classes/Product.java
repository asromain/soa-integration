package webservices.classes;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="product")
public class Product {

    public Product() {}

    private static int idProd = 0;

    private String refProduct;
    private String cleats;
    private String color;
    private String size;
    private String price;

    public Product(String cleats, String color, String size, String price) {
        this.refProduct = String.valueOf(idProd);
        this.cleats = cleats;
        this.color = color;
        this.size = size;
        this.price = price;
        idProd++;
    }

    public String toString() {
        return refProduct + " " + cleats + " " + color + " " + size + " ";
    }

    /* GETTERS */
    public String getRefProduct() {
        return refProduct;
    }
    public String getCleats() {
        return cleats;
    }
    public String getColor() {
        return color;
    }
    public String getSize() {
        return size;
    }
    public String getPrice() { return price; }

    /* SETTERS */
    public void setRefProduct(String refProduct) {
        this.refProduct = refProduct;
    }
    public void setCleats(String cleats) {
        this.cleats = cleats;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public void setPrice(String price) { this.price = price; }
}