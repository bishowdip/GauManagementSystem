/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.model;

/**
 *
 * @author bisho
 */
public class Project {
    private String requestId;
    private String projectName;
    private String startedDate;
    private String ward;
    private String category;
    private String expectedEndDate;
    private String description;
    private String status;
    private Double amount;

    // Default constructor
    public Project() {
    }

    // Constructor with all fields
    public Project(String requestId, String projectName, String startedDate, String ward, 
                   String category, String expectedEndDate, String description, String status, Double amount) {
        this.requestId = requestId;
        this.projectName = projectName;
        this.startedDate = startedDate;
        this.ward = ward;
        this.category = category;
        this.expectedEndDate = expectedEndDate;
        this.description = description;
        this.status = status;
        this.amount = amount;
    }

    // Getters
    public String getRequestId() { return requestId; }
    public String getProjectName() { return projectName; }
    public String getStartedDate() { return startedDate; }
    public String getWard() { return ward; }
    public String getCategory() { return category; }
    public String getExpectedEndDate() { return expectedEndDate; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Double getAmount() { return amount; }

    // Setters
    public void setRequestId(String requestId) { this.requestId = requestId; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public void setStartedDate(String startedDate) { this.startedDate = startedDate; }
    public void setWard(String ward) { this.ward = ward; }
    public void setCategory(String category) { this.category = category; }
    public void setExpectedEndDate(String expectedEndDate) { this.expectedEndDate = expectedEndDate; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }
    public void setAmount(Double amount) { this.amount = amount; }

    // Convert to Object array for table display
    public Object[] toTableRow() {
        return new Object[]{requestId, projectName, startedDate, ward, category, expectedEndDate, description, status, amount};
    }

    @Override
    public String toString() {
        return "Project{" +
                "requestId='" + requestId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", category='" + category + '\'' +
                ", amount=" + amount +
                '}';
    }
}
