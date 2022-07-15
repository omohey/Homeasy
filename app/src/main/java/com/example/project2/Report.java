package com.example.project2;

public class Report {
    String reportID, reporterID, reportedID, description, appID;

    public Report(String reporterID, String reportedID, String description, String appID) {
        this.reporterID = reporterID;
        this.reportedID = reportedID;
        this.description = description;
        this.appID = appID;
    }
    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getReporterID() {
        return reporterID;
    }

    public void setReporterID(String reporterID) {
        this.reporterID = reporterID;
    }

    public String getReportedID() {
        return reportedID;
    }

    public void setReportedID(String reportedID) {
        this.reportedID = reportedID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }
}
