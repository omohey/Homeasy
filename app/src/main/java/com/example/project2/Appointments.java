package com.example.project2;

public class Appointments {
    String date, time, workerID, customerID, address, description, jobType;
    int day, month, year, price;
    Statuss status;
    //float = customerRating, workerRating;



    public Appointments() {

    }

    public Appointments(String date, String time, String workerID, String customerID, String address, String description, String jobType, int day, int month, int year, Statuss status) {
        this.date = date;
        this.time = time;
        this.workerID = workerID;
        this.customerID = customerID;
        this.address = address;
        this.description = description;
        this.jobType = jobType;
        this.day = day;
        this.month = month;
        this.year = year;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWorkerID() {
        return workerID;
    }

    public void setWorkerID(String workerID) {
        this.workerID = workerID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Statuss getStatus() {
        return status;
    }

    public void setStatus(Statuss status) {
        this.status = status;
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
