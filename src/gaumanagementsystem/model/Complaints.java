/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.model;

/**
 *
 * @author SONIC
 */
public class Complaints {
    private String name;
    private String reportedDate;
    private String email;
    private String details;
    private String feedback;

    public Complaints(String name, String reportedDate, String email, String details, String feedback) {
        this.name = name;
        this.reportedDate = reportedDate;
        this.email = email;
        this.details = details;
        this.feedback = feedback;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getReportedDate() { return reportedDate; }
    public void setReportedDate(String reportedDate) { this.reportedDate = reportedDate; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}
