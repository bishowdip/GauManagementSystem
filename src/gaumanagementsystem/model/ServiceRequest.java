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
  
   
    private String citizen;
    private String ward;
    private String ServiceType;
    private String description;
    private String status;
    private final String serviceType;
    
    public ServiceRequest ( String citizen, String ward, 
                              String serviceType, 
                              String description, String status) {
        
        this.citizen = citizen;
        this.ward = ward;
        this.serviceType = serviceType;
        this.description = description;
        this.status = status;
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
    
    // Getter and Setter for Service Type
    public String getServiceType() {
        return serviceType;
    }
    
}