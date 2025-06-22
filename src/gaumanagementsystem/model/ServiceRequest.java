/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.model;

/**
 *
 * @author bishodip
 */
public class ServiceRequest {
    private String citizen;
    private String ward;
    private String serviceType;
    private String description;
    private String status;

    public ServiceRequest(String citizen, String ward, String serviceType, String description, String status) {
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

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    // Getter and Setter for Description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for Status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method to convert the object to a table row
    public Object[] toTableRow() {
        return new Object[]{
            citizen,
            ward,
            serviceType,
            description,
            status
        };
    }
}