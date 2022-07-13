package com.example.project2;

import kotlinx.coroutines.Job;


enum UserType {notdef, Customer, Worker};
enum JobType {notset, Carpenter, Electrician, Plumber, Gardener, Nanny,
    House_Keeping, Cook, Painter, AC_Repair};


public class values {
    public final static String user_table = "users", customers_table = "customers"
            , workers_table = "workers", apps_table = "appointments";
    public static JobType getjob(String job)
    {
        if (job.equals("Carpenter"))
            return JobType.Carpenter;
        else if (job.equals("Electrician"))
            return JobType.Electrician;
        else if (job.equals("Plumber"))
            return JobType.Plumber;
        else if (job.equals("Gardener"))
            return JobType.Gardener;
        else if (job.equals("Nanny"))
            return JobType.Nanny;
        else if (job.equals("House Keeping"))
            return JobType.House_Keeping;
        else if (job.equals("Cook"))
            return JobType.Cook;
        else if (job.equals("Painter"))
            return JobType.Painter;
        else if (job.equals("AC Repair"))
            return JobType.AC_Repair;
        else
            return JobType.notset;
    }
}

