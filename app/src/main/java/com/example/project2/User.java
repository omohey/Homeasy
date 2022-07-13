package com.example.project2;


import java.util.List;

public class User {
    private String Username, Password, ID;
    UserType Type;
    float Rating;
    int norated;
    List<String> Appointments;

    public int getNorated() {
        return norated;
    }

    public void setNorated(int norated) {
        this.norated = norated;
    }



    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public List<String> getAppointments() {
        return Appointments;
    }

    public void setAppointments(List<String> appointments) {
        Appointments = appointments;
    }

    public User() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public User(String username, String password, UserType type) {
        Username = username;
        Password = password;
        Type = type;
    }

    public User(String username, String password, UserType type, float rating, List<String> appointments, int numrated) {
        Username = username;
        Password = password;
        Type = type;
        Rating = rating;
        Appointments = appointments;
        norated = numrated;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }


    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public UserType getType() {
        return Type;
    }

    public void setType(UserType type) {
        Type = type;
    }
}
