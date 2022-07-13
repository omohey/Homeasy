package com.example.project2;

public class AppointListPending {
    String jobtype;
    String date;
    String description;

    public AppointListPending() {
    }

    public AppointListPending(String jobtype, String date, String description) {
        this.jobtype = jobtype;
        this.date = date;
        this.description = description;
    }
}
