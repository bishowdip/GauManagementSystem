/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

/**
 *
 * @author wangel
 */

import gaumanagementsystem.dao.CitizenDao;
import gaumanagementsystem.database.MySqlConnection;
import gaumanagementsystem.model.CitizenData;
import java.sql.*;
import java.util.List;
import javax.swing.JOptionPane;

public class CitizenController {
    
    private MySqlConnection dbConnection;
    private CitizenDao citizenDao;
    
    public CitizenController() {
        this.dbConnection = new MySqlConnection();
        Connection conn = dbConnection.openConnection();
        this.citizenDao = new CitizenDao(conn);
    }
    
    // CREATE Operations
    public boolean createCitizen(CitizenData citizen) {
        try {
            // Validate citizen data
            if (!validateCitizenData(citizen)) {
                return false;
            }
            
            // Check if citizen already exists
            if (citizenDao.citizenExists(citizen.getCitizenId())) {
                JOptionPane.showMessageDialog(null, 
                    "Citizen with ID " + citizen.getCitizenId() + " already exists.\nPlease use a different ID.", 
                    "Duplicate Citizen ID", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
            
            // Show confirmation dialog before creating
            int confirmCreate = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to create a new citizen?\n\n" +
                "Citizen ID: " + citizen.getCitizenId() + "\n" +
                "Name: " + citizen.getName() + "\n" +
                "Email: " + citizen.getEmail() + "\n" +
                "Phone: " + citizen.getPhone(),
                "Confirm Create Citizen",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (confirmCreate != JOptionPane.YES_OPTION) {
                return false; // User cancelled the create operation
            }
            
            boolean success = citizenDao.createCitizen(citizen);
            
            if (success) {
                JOptionPane.showMessageDialog(null, 
                    "Citizen created successfully!\n\nCitizen ID: " + citizen.getCitizenId() + "\nName: " + citizen.getName(), 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Failed to create citizen. Please try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error creating citizen: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // READ Operations
    public CitizenData getCitizenById(String citizenId) {
        try {
            if (citizenId == null || citizenId.trim().isEmpty()) {
                return null;
            }
            return citizenDao.getCitizenById(citizenId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<CitizenData> getAllCitizens() {
        try {
            return citizenDao.getAllCitizens();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<CitizenData> searchCitizensByName(String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return getAllCitizens();
            }
            return citizenDao.searchCitizensByName(name.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<CitizenData> searchCitizensByCitizenshipNumber(String citizenshipNumber) {
        try {
            if (citizenshipNumber == null || citizenshipNumber.trim().isEmpty()) {
                return getAllCitizens();
            }
            return citizenDao.searchCitizensByCitizenshipNumber(citizenshipNumber.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<CitizenData> searchCitizensByNameOrCitizenship(String searchTerm) {
        try {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return getAllCitizens();
            }
            return citizenDao.searchCitizensByNameOrCitizenship(searchTerm.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<CitizenData> getCitizensByAddress(String address) {
        try {
            if (address == null || address.trim().isEmpty()) {
                return getAllCitizens();
            }
            return citizenDao.getCitizensByAddress(address.trim());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // UPDATE Operations
    public boolean updateCitizen(CitizenData citizen) {
        try {
            // Validate citizen data
            if (!validateCitizenData(citizen)) {
                return false;
            }
            
            // Check if citizen exists before updating
            if (!citizenDao.citizenExists(citizen.getCitizenId())) {
                JOptionPane.showMessageDialog(null, 
                    "Citizen with ID " + citizen.getCitizenId() + " does not exist.\nCannot update non-existent citizen.", 
                    "Citizen Not Found", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
            
            // Show confirmation dialog before updating
            int confirmUpdate = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to update this citizen?\n\n" +
                "Citizen ID: " + citizen.getCitizenId() + "\n" +
                "Name: " + citizen.getName() + "\n" +
                "Email: " + citizen.getEmail() + "\n" +
                "Phone: " + citizen.getPhone() + "\n\n" +
                "This action will overwrite existing data.",
                "Confirm Update Citizen",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (confirmUpdate != JOptionPane.YES_OPTION) {
                return false; // User cancelled the update operation
            }
            
            boolean success = citizenDao.updateCitizen(citizen);
            
            if (success) {
                JOptionPane.showMessageDialog(null, 
                    "Citizen updated successfully!\n\nCitizen ID: " + citizen.getCitizenId() + "\nName: " + citizen.getName(), 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Failed to update citizen. Please try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error updating citizen: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // DELETE Operations
    public boolean deleteCitizen(String citizenId) {
        try {
            if (citizenId == null || citizenId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "Citizen ID is required for deletion.", 
                    "Invalid Input", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
            
            // Check if citizen exists before deleting
            if (!citizenDao.citizenExists(citizenId)) {
                JOptionPane.showMessageDialog(null, 
                    "Citizen with ID " + citizenId + " does not exist.\nCannot delete non-existent citizen.", 
                    "Citizen Not Found", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
            
            // Get citizen details for confirmation
            CitizenData citizen = citizenDao.getCitizenById(citizenId);
            String citizenName = (citizen != null) ? citizen.getName() : "Unknown";
            
            // Show confirmation dialog before deleting
            int confirmDelete = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this citizen?\n\n" +
                "Citizen ID: " + citizenId + "\n" +
                "Name: " + citizenName + "\n\n" +
                "This action cannot be undone!",
                "Confirm Delete Citizen",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirmDelete != JOptionPane.YES_OPTION) {
                return false; // User cancelled the delete operation
            }
            
            boolean success = citizenDao.deleteCitizen(citizenId);
            
            if (success) {
                JOptionPane.showMessageDialog(null, 
                    "Citizen deleted successfully!\n\nCitizen ID: " + citizenId + "\nName: " + citizenName, 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Failed to delete citizen. Please try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error deleting citizen: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public boolean deleteMultipleCitizens(List<String> citizenIds) {
        try {
            if (citizenIds == null || citizenIds.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No citizen IDs provided for deletion.", 
                    "Invalid Input", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
            
            // Remove null or empty IDs
            citizenIds.removeIf(id -> id == null || id.trim().isEmpty());
            
            if (citizenIds.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No valid citizen IDs provided for deletion.", 
                    "Invalid Input", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
            
            // Show confirmation dialog for multiple deletion
            int confirmDeleteMultiple = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete " + citizenIds.size() + " citizen(s)?\n\n" +
                "Citizen IDs: " + String.join(", ", citizenIds) + "\n\n" +
                "This action cannot be undone!",
                "Confirm Delete Multiple Citizens",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirmDeleteMultiple != JOptionPane.YES_OPTION) {
                return false; // User cancelled the delete operation
            }
            
            boolean success = citizenDao.deleteMultipleCitizens(citizenIds);
            
            if (success) {
                JOptionPane.showMessageDialog(null, 
                    "Successfully deleted " + citizenIds.size() + " citizen(s)!\n\n" +
                    "Citizen IDs: " + String.join(", ", citizenIds), 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Failed to delete some or all citizens. Please try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error deleting citizens: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Utility Operations
    public boolean citizenExists(String citizenId) {
        try {
            if (citizenId == null || citizenId.trim().isEmpty()) {
                return false;
            }
            return citizenDao.citizenExists(citizenId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getCitizenCount() {
        try {
            return citizenDao.getCitizenCount();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    // Validation method
    private boolean validateCitizenData(CitizenData citizen) {
        if (citizen == null) {
            System.out.println("Citizen data is null.");
            return false;
        }
        
        if (citizen.getCitizenId() == null || citizen.getCitizenId().trim().isEmpty()) {
            System.out.println("Citizen ID is required.");
            return false;
        }
        
        if (citizen.getName() == null || citizen.getName().trim().isEmpty()) {
            System.out.println("Citizen name is required.");
            return false;
        }
        
        if (citizen.getEmail() == null || citizen.getEmail().trim().isEmpty()) {
            System.out.println("Citizen email is required.");
            return false;
        }
        
        // Basic email validation
        if (!isValidEmail(citizen.getEmail())) {
            System.out.println("Invalid email format.");
            return false;
        }
        
        if (citizen.getPhone() == null || citizen.getPhone().trim().isEmpty()) {
            System.out.println("Citizen phone is required.");
            return false;
        }
        
        return true;
    }
    
    // Email validation helper
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    
    // Close database connection
    public void closeConnection() {
        try {
            if (dbConnection != null) {
                // The connection is managed by MySqlConnection
                // This method can be used for any cleanup if needed
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
