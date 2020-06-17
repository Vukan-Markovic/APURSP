package vukan.com.apursp.models;

import com.google.firebase.Timestamp;

public class ProductImage {
    private String productID;
    private String imageUrl;

    public ProductImage() {
    }

    public ProductImage(String productID, String imageUrl, Timestamp dateTime) {
        this.productID = productID;
        this.imageUrl = imageUrl;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}