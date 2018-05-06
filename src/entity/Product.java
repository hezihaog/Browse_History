package entity;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String productName;
    private String productType;
    private double price;

    public Product() {

        Product product = new Product();
        product.setId("");
        product.setProductName("");
        product.setProductType("");
        product.setPrice(0.0D);

    }

    public Product(String id, String productName, String productType, double price) {
        this.id = id;
        this.productName = productName;
        this.productType = productType;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"productName\":\"")
                .append(productName).append('\"');
        sb.append(",\"productType\":\"")
                .append(productType).append('\"');
        sb.append(",\"price\":")
                .append(price);
        sb.append('}');
        return sb.toString();
    }
}