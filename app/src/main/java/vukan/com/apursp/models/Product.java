package vukan.com.apursp.models;

import com.google.firebase.Timestamp;

public class Product {
    private String productID;
    private String name;
    private String description;
    private Double price;
    private Timestamp datetime;
    private String categoryID;
    private String userID;
    private String homePhotoUrl;
    private Long seen;


    public Product() {
    }

    public Product(String name, String description, String userID, Double price, String categoryID, Timestamp datetime, String homePhotoUrl, Long seen) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryID = categoryID;
        this.datetime = datetime;
        this.userID = userID;
        this.homePhotoUrl = homePhotoUrl;
        this.seen = seen;
    }

    public Product(String name, String description, String userID, Double price, String categoryID, Timestamp datetime, String homePhotoUrl, String currency, Boolean fixPrice, Long seen) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryID = categoryID;
        this.datetime = datetime;
        this.userID = userID;
        this.homePhotoUrl = homePhotoUrl;
        this.seen = seen;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getHomePhotoUrl() {
        return homePhotoUrl;
    }

    public void setHomePhotoUrl(String homePhotoUrl) {
        this.homePhotoUrl = homePhotoUrl;
    }

    public Long getSeen() {
        return seen;
    }

    public void setSeen(Long seen) {
        this.seen = seen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productID.equals(product.productID) &&
                name.equals(product.name) &&
                description.equals(product.description) &&
                price.equals(product.price) &&
                categoryID.equals(product.categoryID);
    }


}
