package vukan.com.apursp.models;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Message {
    private String content;
    private Timestamp dateTime;
    private String productID;
    private String senderID;
    private String receiverID;
    private long dateTimelong;


    public Message() {
    }

    public Message(String content, Timestamp dateTime, String productID, String senderID, String receiverID) {
        this.content = content;
        this.dateTime = dateTime;
        this.productID = productID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.dateTimelong = new Date().getTime();

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDateTimelong() {
        return dateTimelong;
    }

    public void setDateTimelong(long dateTime) {
        this.dateTimelong = dateTimelong;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    @Override
    public String toString() {
        return "Content='" + content + '\'' +
                ", sender='" + senderID + '\'';
    }
}
