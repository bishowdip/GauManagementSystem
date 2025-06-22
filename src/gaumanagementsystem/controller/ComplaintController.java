package gaumanagementsystem.controller;

import gaumanagementsystem.dao.ComplaintDAO;
import gaumanagementsystem.dao.impl.ComplaintDAOImpl;
import gaumanagementsystem.model.Complaint;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controller for managing Complaints and Feedback
 * Handles business logic and coordinates between View and DAO layers
 */
public class ComplaintController {
    
    private final ComplaintDAO complaintDAO;
    
    public ComplaintController() {
        this.complaintDAO = new ComplaintDAOImpl();
    }
    
    /**
     * Create a new complaint
     */
    public boolean createComplaint(String name, int ward, String phone, String email, 
                                  String category, String description, Date date) {
        try {
            System.out.println("ComplaintController: Creating complaint with data:");
            System.out.println("  Name: " + name);
            System.out.println("  Ward: " + ward);
            System.out.println("  Phone: " + phone);
            System.out.println("  Email: " + email);
            System.out.println("  Category: " + category);
            System.out.println("  Description: " + description);
            System.out.println("  Date: " + date);
            
            Complaint complaint = new Complaint();
            complaint.setName(name);
            complaint.setWard(ward);
            complaint.setPhone(phone);
            complaint.setEmail(email);
            complaint.setCategory(category);
            complaint.setDescription(description);
            complaint.setDate(date);
            complaint.setStatus("Pending");
            
            boolean success = complaintDAO.createComplaint(complaint);
            if (success) {
                System.out.println("ComplaintController: Complaint created successfully with ID: " + complaint.getId());
            } else {
                System.err.println("ComplaintController: Failed to create complaint");
            }
            return success;
        } catch (Exception e) {
            System.err.println("ComplaintController: Error creating complaint: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get all complaints
     */
    public List<Complaint> getAllComplaints() {
        try {
            return complaintDAO.getAllComplaints();
        } catch (Exception e) {
            System.err.println("Error retrieving all complaints: " + e.getMessage());
            return List.of(); // Return empty list on error
        }
    }
    
    /**
     * Get complaints by type (Complaint or Feedback)
     */
    public List<Complaint> getComplaintsByType(String type) {
        try {
            return complaintDAO.getComplaintsByType(type);
        } catch (Exception e) {
            System.err.println("Error retrieving complaints by type: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Update complaint
     */
    public boolean updateComplaint(int id, String name, int ward, String phone, String email,
                                  String category, String description, String status, Date date, String feedback) {
        try {
            Optional<Complaint> existingComplaint = complaintDAO.findById(id);
            if (existingComplaint.isPresent()) {
                Complaint complaint = existingComplaint.get();
                complaint.setName(name);
                complaint.setWard(ward);
                complaint.setPhone(phone);
                complaint.setEmail(email);
                complaint.setCategory(category);
                complaint.setDescription(description);
                complaint.setStatus(status);
                complaint.setDate(date);
                complaint.setFeedback(feedback);
                
                return complaintDAO.updateComplaint(complaint);
            } else {
                System.err.println("Complaint with ID " + id + " not found");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error updating complaint: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete complaint
     */
    public boolean deleteComplaint(int id) {
        try {
            return complaintDAO.deleteComplaint(id);
        } catch (Exception e) {
            System.err.println("Error deleting complaint: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Search complaints by description
     */
    public List<Complaint> searchComplaints(String searchText) {
        try {
            if (searchText == null || searchText.trim().isEmpty()) {
                return getAllComplaints();
            }
            return complaintDAO.searchByDescription(searchText.trim());
        } catch (Exception e) {
            System.err.println("Error searching complaints: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Get complaints by status
     */
    public List<Complaint> getComplaintsByStatus(String status) {
        try {
            return complaintDAO.getComplaintsByStatus(status);
        } catch (Exception e) {
            System.err.println("Error retrieving complaints by status: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Get complaints by ward
     */
    public List<Complaint> getComplaintsByWard(int ward) {
        try {
            return complaintDAO.getComplaintsByWard(ward);
        } catch (Exception e) {
            System.err.println("Error retrieving complaints by ward: " + e.getMessage());
            return List.of();
        }
    }
    
    /**
     * Update complaint status
     */
    public boolean updateComplaintStatus(int id, String newStatus) {
        try {
            return complaintDAO.updateComplaintStatus(id, newStatus);
        } catch (Exception e) {
            System.err.println("Error updating complaint status: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Add response to complaint
     */
    public boolean addComplaintResponse(int id, String response) {
        try {
            return complaintDAO.addComplaintResponse(id, response);
        } catch (Exception e) {
            System.err.println("Error adding complaint response: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get complaint by ID
     */
    public Optional<Complaint> getComplaintById(int id) {
        try {
            return complaintDAO.findById(id);
        } catch (Exception e) {
            System.err.println("Error finding complaint by ID: " + e.getMessage());
            return Optional.empty();
        }
    }
    
    /**
     * Get total complaint count
     */
    public int getComplaintCount() {
        try {
            return complaintDAO.getComplaintCount();
        } catch (Exception e) {
            System.err.println("Error getting complaint count: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get complaint count by type
     */
    public int getComplaintCountByType(String type) {
        try {
            return complaintDAO.getComplaintCountByType(type);
        } catch (Exception e) {
            System.err.println("Error getting complaint count by type: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Get complaint count by status
     */
    public int getComplaintCountByStatus(String status) {
        try {
            return complaintDAO.getComplaintCountByStatus(status);
        } catch (Exception e) {
            System.err.println("Error getting complaint count by status: " + e.getMessage());
            return 0;
        }
    }
} 