package vukan.com.apursp.models;

import com.google.firebase.Timestamp;

public class ProductImage {
    private String productID;
    private String userID;
    private String imageUrl;
    private Timestamp dateTime;

    public ProductImage() {
    }

    public ProductImage(String productID, String userID, String imageUrl, Timestamp dateTime) {
        this.productID = productID;
        this.userID = userID;
        this.imageUrl = imageUrl;
        this.dateTime = dateTime;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
}