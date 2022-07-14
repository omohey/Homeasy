package com.example.project2;

import com.example.project2.User;

import java.util.List;


public class Worker extends User {
        JobType jobType;
        String Name, Phone;
        List<String> Appointmentsrequested;

    public List<String> getAppointmentsrequested() {
        return Appointmentsrequested;
    }

    public void setAppointmentsrequested(List<String> appointmentsrequested) {
        Appointmentsrequested = appointmentsrequested;
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

    public Worker() {
    }

    public Worker(User user, JobType jobType, String name, String phone) {
        super(user.getUsername(), user.getPassword(), user.getType(), user.getRating(), user.getAppointments(), user.getNorated());
        super.setID(user.getID());
        this.jobType = jobType;
        Name = name;
        Phone = phone;
    }



    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }
}
