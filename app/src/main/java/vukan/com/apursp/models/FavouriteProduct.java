package vukan.com.apursp.models;

public class FavouriteProduct {
    private String productID;
    private String userID;

    public FavouriteProduct() {
    }

    public FavouriteProduct(String productID, String userID) {
        this.productID = productID;
        this.userID = userID;
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
}
