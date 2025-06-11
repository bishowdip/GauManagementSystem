/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.model;

/**
 *
 * @author ASUS
 */
public class ServiceRequest {
  
    private String requestId;
    private String citizen;
    private String ward;
    private String requestedDate;
    private String ServiceType;
    private String description;
    private String status;
    private final String serviceType;
    
    public ServiceRequest (String requestId, String citizen, String ward, 
                              String requestedDate, String serviceType, 
                              String description, String status) {
        this.requestId = requestId;
        this.citizen = citizen;
        this.ward = ward;
        this.requestedDate = requestedDate;
        this.serviceType = serviceType;
        this.description = description;
        this.status = status;
    }
        
    // Getter and Setter for Request ID
    public String getRequestId() {
        return requestId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    // Getter and Setter for Citizen
    public String getCitizen() {
        return citizen;
    }
    
    public void setCitizen(String citizen) {
        this.citizen = citizen;
    }
    
    // Getter and Setter for Ward
    public String getWard() {
        return ward;
    }
    
    public void setWard(String ward) {
        this.ward = ward;
    }
    
    // Getter and Setter for Requested Date
    public String getRequestedDate() {
        return requestedDate;
    }
    
    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }
    
    // Getter and Setter for Service Type
    public String getServiceType() {
        return serviceType;
    }
    
}