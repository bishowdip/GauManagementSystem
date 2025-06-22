package gaumanagementsystem.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Complaint model class for complaints and feedback management
 */
public class Complaint {
    private int id;
    private String name;
    private Date date;
    private String email;
    private String description;
    private String category;
    private String status;
    private String feedback;
    private int ward;
    private String phone;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public Complaint() {}
    
    // Constructor for new complaint
    public Complaint(String name, Date date, String email, String description, String category) {
        this.name = name;
        this.date = date;
        this.email = email;
        this.description = description;
        this.category = category;
        this.status = "Pending";
    }
    
    // Full constructor
    public Complaint(int id, String name, Date date, String email, String description, String category,
                     String status, String feedback, int ward, String phone, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.email = email;
        this.description = description;
        this.category = category;
        this.status = status;
        this.feedback = feedback;
        this.ward = ward;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getFeedback() {
        return feedback;
    }
    
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    
    public int getWard() {
        return ward;
    }
    
    public void setWard(int ward) {
        this.ward = ward;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Utility methods
    public boolean isPending() {
        return "Pending".equalsIgnoreCase(this.status);
    }
    
    public boolean isResolved() {
        return "Resolved".equalsIgnoreCase(this.status);
    }
    
    public boolean isInProgress() {
        return "In Progress".equalsIgnoreCase(this.status);
    }
    
    @Override
    public String toString() {
        return "Complaint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                ", date=" + date +
                '}';
    }
} 