package com.example.project2;

public class AppointListWorkerReq {
    String Name;
    String Rating;
    String Date;
    String Description;

    public AppointListWorkerReq() {
    }

    public AppointListWorkerReq(String name, String rating, String date, String description) {
        Name = name;
        Rating = rating;
        Date = date;
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public String getRating() {
        return Rating;
    }

    public String getDate() {
        return Date;
    }
}
