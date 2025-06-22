package gaumanagementsystem.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author bishodip
 */
public class ProjectRequest {
    private int requestId;
    private String projectsName;
    private Date startedDate;
    private int ward;
    private Date expectedToEnd;
    private String description;
    private String status;
    private BigDecimal budget;
    private String requestedBy;
    private String priority;
    private String category;
    private String fiscalYear;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Default constructor
    public ProjectRequest() {}
    
    // Constructor for new project request
    public ProjectRequest(String projectsName, Date startedDate, int ward, Date expectedToEnd,
                          String description, BigDecimal budget, String category) {
        this.projectsName = projectsName;
        this.startedDate = startedDate;
        this.ward = ward;
        this.expectedToEnd = expectedToEnd;
        this.description = description;
        this.budget = budget;
        this.category = category;
        this.status = "Pending";
        this.priority = "Medium";
        this.fiscalYear = "2024-25";
    }
    
    // Full constructor
    public ProjectRequest(int requestId, String projectsName, Date startedDate, int ward, Date expectedToEnd,
                          String description, String status, BigDecimal budget, String requestedBy,
                          String priority, String category, String fiscalYear, Timestamp createdAt, Timestamp updatedAt) {
        this.requestId = requestId;
        this.projectsName = projectsName;
        this.startedDate = startedDate;
        this.ward = ward;
        this.expectedToEnd = expectedToEnd;
        this.description = description;
        this.status = status;
        this.budget = budget;
        this.requestedBy = requestedBy;
        this.priority = priority;
        this.category = category;
        this.fiscalYear = fiscalYear;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getRequestId() {
        return requestId;
    }
    
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
    
    public String getProjectsName() {
        return projectsName;
    }
    
    public void setProjectsName(String projectsName) {
        this.projectsName = projectsName;
    }
    
    public Date getStartedDate() {
        return startedDate;
    }
    
    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }
    
    public int getWard() {
        return ward;
    }
    
    public void setWard(int ward) {
        this.ward = ward;
    }
    
    public Date getExpectedToEnd() {
        return expectedToEnd;
    }
    
    public void setExpectedToEnd(Date expectedToEnd) {
        this.expectedToEnd = expectedToEnd;
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
    
    public BigDecimal getBudget() {
        return budget;
    }
    
    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
    
    public String getRequestedBy() {
        return requestedBy;
    }
    
    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getFiscalYear() {
        return fiscalYear;
    }
    
    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
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
    
    public boolean isApproved() {
        return "Approved".equalsIgnoreCase(this.status);
    }
    
    public boolean isInProgress() {
        return "In Progress".equalsIgnoreCase(this.status);
    }
    
    public boolean isCompleted() {
        return "Completed".equalsIgnoreCase(this.status);
    }
    
    public boolean isHighPriority() {
        return "High".equalsIgnoreCase(this.priority);
    }
    
    @Override
    public String toString() {
        return "ProjectRequest{" +
                "requestId=" + requestId +
                ", projectsName='" + projectsName + '\'' +
                ", ward=" + ward +
                ", status='" + status + '\'' +
                ", budget=" + budget +
                ", category='" + category + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
} 