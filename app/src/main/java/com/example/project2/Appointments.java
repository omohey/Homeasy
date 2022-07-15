package com.example.project2;

import java.util.List;

public class Appointments {
    String date, time, workerID, customerID, address, description, jobType, appID;
    int day, month, year, price;
    Statuss status;
    List<String> requested_workers;
    List<Float> requested_price;
    Boolean workerRated, customerRated;
    int workergotrated, customergotrated;
    //float = customerRating, workerRating;



    public Appointments() {
        workerRated = false;
        customerRated = false;
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
        workerRated = false;
        customerRated = false;
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

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public List<String> getRequested_workers() {
        return requested_workers;
    }

    public void setRequested_workers(List<String> requested_workers) {
        this.requested_workers = requested_workers;
    }

    public List<Float> getRequested_price() {
        return requested_price;
    }

    public void setRequested_price(List<Float> requested_price) {
        this.requested_price = requested_price;
    }

    public Boolean getWorkerRated() {
        return workerRated;
    }

    public void setWorkerRated(Boolean workerRated) {
        this.workerRated = workerRated;
    }

    public Boolean getCustomerRated() {
        return customerRated;
    }

    public void setCustomerRated(Boolean customerRated) {
        this.customerRated = customerRated;
    }

    public int getWorkergotrated() {
        return workergotrated;
    }

    public void setWorkergotrated(int workergotrated) {
        this.workergotrated = workergotrated;
    }

    public int getCustomergotrated() {
        return customergotrated;
    }

    public void setCustomergotrated(int customergotrated) {
        this.customergotrated = customergotrated;
    }
}
