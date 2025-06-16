/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.model;

/**
 *
 * @author ASUS
 */
public class NewsAndNotice {
    private String date;
    private String audience;
    private String subject;
    private String description;
    private String expiryDate;

    public NewsAndNotice( String date, String audience, String subject, String description, String expiryDate) {
       
        this.date = date;
        this.audience = audience;
        this.subject = subject;
        this.description = description;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters
   

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getAudience() { return audience; }
    public void setAudience(String audience) { this.audience = audience; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public Object[] toObjectArray() {
        return new Object[]{date, audience, subject, description, expiryDate};
    }
}
