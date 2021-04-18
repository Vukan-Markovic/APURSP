package vukan.com.apursp.models;

import java.util.ArrayList;
import java.util.List;

public class Conv {
    private final List<Message> messages;
    private String productName;
    private String userName;

    public Conv() {
        messages = new ArrayList<>();
        productName = "";
        userName = "";
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Message> getMessages() {
        return messages;
    }
}