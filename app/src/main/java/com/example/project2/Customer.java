package com.example.project2;

public class Customer extends User{
    String Name, Phone;

    public Customer() {
    }

    public Customer(User user, String name, String phone) {
        super(user.getUsername(), user.getPassword(), user.getType(), user.getRating(), user.getAppointments(), user.getNorated());
        super.setID(user.getID());
        Name = name;
        Phone = phone;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
