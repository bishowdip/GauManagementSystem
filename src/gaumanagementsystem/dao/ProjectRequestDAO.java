package gaumanagementsystem.dao;

import gaumanagementsystem.model.ProjectRequest;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author bishodip
 */
public interface ProjectRequestDAO {
    
    /**
     * Create a new project request
     * @param projectRequest ProjectRequest object to be created
     * @return true if project request was created successfully, false otherwise
     */
    boolean createProjectRequest(ProjectRequest projectRequest);
    
    /**
     * Find project request by ID
     * @param requestId Project request ID
     * @return Optional containing ProjectRequest if found, empty Optional otherwise
     */
    Optional<ProjectRequest> findById(int requestId);
    
    /**
     * Get all project requests
     * @return List of all project requests
     */
    List<ProjectRequest> getAllProjectRequests();
    
    /**
     * Get project requests by status
     * @param status Project status (Pending, Approved, In Progress, Completed)
     * @return List of project requests with specified status
     */
    List<ProjectRequest> getProjectRequestsByStatus(String status);
    
    /**
     * Get project requests by ward
     * @param ward Ward number
     * @return List of project requests in specified ward
     */
    List<ProjectRequest> getProjectRequestsByWard(int ward);
    
    /**
     * Get project requests by priority
     * @param priority Project priority (High, Medium, Low)
     * @return List of project requests with specified priority
     */
    List<ProjectRequest> getProjectRequestsByPriority(String priority);
    
    /**
     * Search project requests by project name
     * @param projectName Project name or part of name to search
     * @return List of project requests matching the search criteria
     */
    List<ProjectRequest> searchByProjectName(String projectName);
    
    /**
     * Get project requests by budget range
     * @param minBudget Minimum budget
     * @param maxBudget Maximum budget
     * @return List of project requests within budget range
     */
    List<ProjectRequest> getProjectRequestsByBudgetRange(BigDecimal minBudget, BigDecimal maxBudget);
    
    /**
     * Get project requests by date range
     * @param startDate Start date
     * @param endDate End date
     * @return List of project requests within date range
     */
    List<ProjectRequest> getProjectRequestsByDateRange(Date startDate, Date endDate);
    
    /**
     * Update project request information
     * @param projectRequest ProjectRequest object with updated information
     * @return true if update successful, false otherwise
     */
    boolean updateProjectRequest(ProjectRequest projectRequest);
    
    /**
     * Update project request status
     * @param requestId Project request ID
     * @param newStatus New status
     * @return true if status updated successfully, false otherwise
     */
    boolean updateProjectStatus(int requestId, String newStatus);
    
    /**
     * Update project request priority
     * @param requestId Project request ID
     * @param newPriority New priority
     * @return true if priority updated successfully, false otherwise
     */
    boolean updateProjectPriority(int requestId, String newPriority);
    
    /**
     * Update project budget
     * @param requestId Project request ID
     * @param newBudget New budget amount
     * @return true if budget updated successfully, false otherwise
     */
    boolean updateProjectBudget(int requestId, BigDecimal newBudget);
    
    /**
     * Delete project request by ID
     * @param requestId Project request ID to delete
     * @return true if deletion successful, false otherwise
     */
    boolean deleteProjectRequest(int requestId);
    
    /**
     * Get total count of project requests
     * @return Total number of project requests
     */
    int getProjectRequestCount();
    
    /**
     * Get count of project requests by status
     * @param status Project status
     * @return Number of project requests with specified status
     */
    int getProjectRequestCountByStatus(String status);
    
    /**
     * Get count of project requests by ward
     * @param ward Ward number
     * @return Number of project requests in specified ward
     */
    int getProjectRequestCountByWard(int ward);
    
    /**
     * Get total budget sum of all projects
     * @return Total budget amount
     */
    BigDecimal getTotalProjectBudget();
    
    /**
     * Get total budget sum by status
     * @param status Project status
     * @return Total budget amount for specified status
     */
    BigDecimal getTotalBudgetByStatus(String status);
} 