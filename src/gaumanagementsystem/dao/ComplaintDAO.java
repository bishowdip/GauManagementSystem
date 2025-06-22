package gaumanagementsystem.dao;

import gaumanagementsystem.model.Complaint;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author bishodip
 */
public interface ComplaintDAO {
    
    /**
     * Create a new complaint
     * @param complaint Complaint object to be created
     * @return true if complaint was created successfully, false otherwise
     */
    boolean createComplaint(Complaint complaint);
    
    /**
     * Find complaint by ID
     * @param complaintId Complaint ID
     * @return Optional containing Complaint if found, empty Optional otherwise
     */
    Optional<Complaint> findById(int complaintId);
    
    /**
     * Get all complaints
     * @return List of all complaints
     */
    List<Complaint> getAllComplaints();
    
    /**
     * Get complaints by type
     * @param type Complaint type (Complaint, Feedback)
     * @return List of complaints with specified type
     */
    List<Complaint> getComplaintsByType(String type);
    
    /**
     * Get complaints by status
     * @param status Complaint status (Pending, In Progress, Resolved)
     * @return List of complaints with specified status
     */
    List<Complaint> getComplaintsByStatus(String status);
    
    /**
     * Get complaints by priority
     * @param priority Complaint priority (High, Medium, Low)
     * @return List of complaints with specified priority
     */
    List<Complaint> getComplaintsByPriority(String priority);
    
    /**
     * Get complaints by ward
     * @param ward Ward number
     * @return List of complaints in specified ward
     */
    List<Complaint> getComplaintsByWard(int ward);
    
    /**
     * Get complaints by citizen name
     * @param citizenName Name of citizen
     * @return List of complaints submitted by specified citizen
     */
    List<Complaint> getComplaintsByCitizen(String citizenName);
    
    /**
     * Search complaints by subject
     * @param subject Subject or part of subject to search
     * @return List of complaints matching the search criteria
     */
    List<Complaint> searchBySubject(String subject);
    
    /**
     * Search complaints by description
     * @param description Description or part of description to search
     * @return List of complaints matching the search criteria
     */
    List<Complaint> searchByDescription(String description);
    
    /**
     * Get complaints submitted in date range
     * @param startDate Start date
     * @param endDate End date
     * @return List of complaints submitted between dates
     */
    List<Complaint> getComplaintsByDateRange(Date startDate, Date endDate);
    
    /**
     * Update complaint information
     * @param complaint Complaint object with updated information
     * @return true if update successful, false otherwise
     */
    boolean updateComplaint(Complaint complaint);
    
    /**
     * Update complaint status
     * @param complaintId Complaint ID
     * @param newStatus New status
     * @return true if status updated successfully, false otherwise
     */
    boolean updateComplaintStatus(int complaintId, String newStatus);
    
    /**
     * Update complaint priority
     * @param complaintId Complaint ID
     * @param newPriority New priority
     * @return true if priority updated successfully, false otherwise
     */
    boolean updateComplaintPriority(int complaintId, String newPriority);
    
    /**
     * Add response to complaint
     * @param complaintId Complaint ID
     * @param response Response text
     * @return true if response added successfully, false otherwise
     */
    boolean addComplaintResponse(int complaintId, String response);
    
    /**
     * Delete complaint by ID
     * @param complaintId Complaint ID to delete
     * @return true if deletion successful, false otherwise
     */
    boolean deleteComplaint(int complaintId);
    
    /**
     * Get total count of complaints
     * @return Total number of complaints
     */
    int getComplaintCount();
    
    /**
     * Get count of complaints by type
     * @param type Complaint type
     * @return Number of complaints with specified type
     */
    int getComplaintCountByType(String type);
    
    /**
     * Get count of complaints by status
     * @param status Complaint status
     * @return Number of complaints with specified status
     */
    int getComplaintCountByStatus(String status);
    
    /**
     * Get count of complaints by priority
     * @param priority Complaint priority
     * @return Number of complaints with specified priority
     */
    int getComplaintCountByPriority(String priority);
    
    /**
     * Get count of complaints by ward
     * @param ward Ward number
     * @return Number of complaints in specified ward
     */
    int getComplaintCountByWard(int ward);
    
    /**
     * Get average resolution time in days
     * @return Average days to resolve complaints
     */
    double getAverageResolutionTime();
    
    /**
     * Get complaints pending for more than specified days
     * @param days Number of days
     * @return List of complaints pending for more than specified days
     */
    List<Complaint> getComplaintsPendingForDays(int days);
} 