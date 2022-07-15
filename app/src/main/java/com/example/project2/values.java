package com.example.project2;

import kotlinx.coroutines.Job;


enum UserType {notdef, Customer, Worker};
enum JobType {notset, Carpenter, Electrician, Plumber, Gardener, Nanny,
    House_Keeping, Cook, Painter, AC_Repair};


public class values {
    public final static String user_table = "users", customers_table = "customers"
            , workers_table = "workers", apps_table = "appointments", reports_table = "reports";
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

    public static String getjobstring(JobType job)
    {
        if (job.equals(JobType.Carpenter))
            return "Carpenter";
        else if (job.equals(JobType.Electrician))
            return "Electrician";
        else if (job.equals(JobType.Plumber))
            return "Plumber";
        else if (job.equals(JobType.Gardener))
            return "Gardener";
        else if (job.equals(JobType.Nanny))
            return "Nanny";
        else if (job.equals(JobType.House_Keeping))
            return "House Keeping";
        else if (job.equals(JobType.Cook))
            return "Cook";
        else if (job.equals(JobType.Painter))
            return "Painter";
        else if (job.equals(JobType.AC_Repair))
            return "AC Repair";
        else
            return "Error";
    }
}

