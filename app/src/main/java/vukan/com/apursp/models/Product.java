package vukan.com.apursp.models;

public class Product {
    private String productID;
    private String name;
    private String description;
    private Double price;
    private String categoryID;

    public Product() {}

    public Product(String name, String description, Double price, String categoryID) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryID = categoryID;
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
