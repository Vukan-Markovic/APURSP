package vukan.com.apursp.models;

public class User {
    private String userID;
    private String username;
    private String location;
    private Double grade;
    private String phone;

    public User() {}

    public User(String userID, String username, String location, Double grade,String phone) {
        this.userID = userID;
        this.username = username;
        this.location = location;
        this.grade = grade;
        this.phone=phone;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getPhone(){  return  phone; }

    public void setPhone(String phone){ this.phone=phone; }
}
