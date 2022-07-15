package com.example.project2;

public class SMSMessage {
    String Phone, Body;
    boolean sent;

    public SMSMessage() {
    }

    public SMSMessage(String phone, String body, boolean sent) {
        Phone = phone;
        Body = body;
        this.sent = sent;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    /*
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }*/
}
