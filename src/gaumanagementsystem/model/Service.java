package gaumanagementsystem.model;

import java.sql.Timestamp;

/**
 *
 * @author bishodip
 */
public class Service {
    private int serviceId;
    private String serviceName;
    private Timestamp submittedAt;
    private String nameOfCitizen;
    private int ward;
    private String description;
    private String status;
    private String phone;
    private String email;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public Service() {}
    
    // Constructor for new service request
    public Service(String serviceName, String nameOfCitizen, int ward, String description) {
        this.serviceName = serviceName;
        this.nameOfCitizen = nameOfCitizen;
        this.ward = ward;
        this.description = description;
        this.status = "Pending";
        this.submittedAt = new Timestamp(System.currentTimeMillis());
    }
    
    // Full constructor
    public Service(int serviceId, String serviceName, Timestamp submittedAt, String nameOfCitizen,
                   int ward, String description, String status, String phone, String email,
                   Timestamp createdAt, Timestamp updatedAt) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.submittedAt = submittedAt;
        this.nameOfCitizen = nameOfCitizen;
        this.ward = ward;
        this.description = description;
        this.status = status;
        this.phone = phone;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getServiceId() {
        return serviceId;
    }
    
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public Timestamp getSubmittedAt() {
        return submittedAt;
    }
    
    public void setSubmittedAt(Timestamp submittedAt) {
        this.submittedAt = submittedAt;
    }
    
    public String getNameOfCitizen() {
        return nameOfCitizen;
    }
    
    public void setNameOfCitizen(String nameOfCitizen) {
        this.nameOfCitizen = nameOfCitizen;
    }
    
    public int getWard() {
        return ward;
    }
    
    public void setWard(int ward) {
        this.ward = ward;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
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
    
    public boolean isCompleted() {
        return "Completed".equalsIgnoreCase(this.status);
    }
    
    public boolean isInProgress() {
        return "In Progress".equalsIgnoreCase(this.status);
    }
    
    @Override
    public String toString() {
        return "Service{" +
                "serviceId=" + serviceId +
                ", serviceName='" + serviceName + '\'' +
                ", nameOfCitizen='" + nameOfCitizen + '\'' +
                ", ward=" + ward +
                ", status='" + status + '\'' +
                ", submittedAt=" + submittedAt +
                '}';
    }
} 