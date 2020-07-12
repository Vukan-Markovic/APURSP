package vukan.com.apursp.models;

public class ProductImage {
    private String productID;
    private String imageUrl;

    public ProductImage() {
    }

    public ProductImage(String productID, String imageUrl) {
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