package vukan.com.apursp.models;

import com.google.firebase.Timestamp;

public class Comment {
    private String commentID;
    private String content;
    private Timestamp dateTime;
    private String userID;
    private String username;

    public Comment() {
    }

    public Comment(String commentID, String content, Timestamp dateTime, String userID, String username) {
        this.commentID = commentID;
        this.content = content;
        this.dateTime = dateTime;
        this.userID = userID;
        this.username = username;
    }

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
