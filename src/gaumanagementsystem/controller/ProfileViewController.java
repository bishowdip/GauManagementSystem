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
import gaumanagementsystem.view.ProfileView;
import gaumanagementsystem.view.EditProfileView;
import gaumanagementsystem.view.DashboardUser;

import javax.swing.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;

public class ProfileViewController {
    private ProfileView view;
    private CitizenDao dao;
    private String currentCitizenId;

    public ProfileViewController(ProfileView view) {
        this.view = view;
        
        // Initialize database connection
        try {
            MySqlConnection dbConnection = new MySqlConnection();
            Connection conn = dbConnection.openConnection();
            dao = new CitizenDao(conn);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Set fields as read-only since this is for viewing only
        view.setFieldsReadOnly();

        // Add action listeners
        setupActionListeners();
    }

    private void setupActionListeners() {
        // Edit button - opens EditProfileView
        view.getEditButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openEditProfile();
            }
        });

        // Back button - returns to dashboard
        view.getBackButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goBackToDashboard();
            }
        });

        // Menu button (if needed)
        view.getMenuButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle menu action if needed
            }
        });
    }

    // Load citizen data by ID
    public boolean loadCitizenData(String citizenId) {
        try {
            if (citizenId == null || citizenId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Citizen ID is required.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            CitizenData citizen = dao.getCitizenById(citizenId.trim());
            
            if (citizen == null) {
                JOptionPane.showMessageDialog(view, "Citizen not found with ID: " + citizenId, "Not Found", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Populate the form with citizen data
            populateFormWithCitizenData(citizen);
            currentCitizenId = citizenId;
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error loading citizen data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Populate form fields with citizen data
    private void populateFormWithCitizenData(CitizenData citizen) {
        try {
            // Set text fields
            view.getCitizenIdField().setText(citizen.getCitizenId());
            view.getNameField().setText(citizen.getName());
            view.getEmailField().setText(citizen.getEmail());
            view.getDateOfBirthField().setText(citizen.getDateOfBirth());
            view.getAddressField().setText(citizen.getAddress());
            view.getPhoneField().setText(citizen.getPhone());
            view.getFatherNameField().setText(citizen.getFatherName());
            view.getMotherNameField().setText(citizen.getMotherName());

            // Set gender selection
            view.setGenderSelection(citizen.getGender());

            // Load profile image if available
            loadProfileImage(citizen.getImagePath());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error populating form: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Load and display profile image
    private void loadProfileImage(String imagePath) {
        try {
            if (imagePath != null && !imagePath.trim().isEmpty()) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    ImageIcon originalIcon = new ImageIcon(imagePath);
                    Image scaledImage = originalIcon.getImage().getScaledInstance(88, 92, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    view.getProfileImageLabel().setIcon(scaledIcon);
                } else {
                    // Set default image or clear if file doesn't exist
                    view.getProfileImageLabel().setIcon(null);
                }
            } else {
                // Clear image if no path provided
                view.getProfileImageLabel().setIcon(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Don't show error for image loading, just clear the image
            view.getProfileImageLabel().setIcon(null);
        }
    }

    // Open EditProfileView for editing
    private void openEditProfile() {
        try {
            if (currentCitizenId == null || currentCitizenId.trim().isEmpty()) {
                JOptionPane.showMessageDialog(view, "No citizen data loaded to edit.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Create EditProfileView and pass the current citizen ID
            EditProfileView editView = new EditProfileView();
            
            // You might need to modify EditProfileView to accept citizen ID in constructor
            // or create a method to load citizen data by ID
            
            editView.setVisible(true);
            view.dispose(); // Close the current view

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error opening edit form: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Go back to dashboard
    private void goBackToDashboard() {
        try {
            DashboardUser dashboard = new DashboardUser();
            dashboard.setVisible(true);
            view.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error returning to dashboard: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Refresh citizen data
    public void refreshCitizenData() {
        if (currentCitizenId != null) {
            loadCitizenData(currentCitizenId);
        }
    }

    // Get current citizen ID
    public String getCurrentCitizenId() {
        return currentCitizenId;
    }

    // Clear all form data
    public void clearForm() {
        view.clearAllFields();
        currentCitizenId = null;
    }

    // Method to check if citizen data is loaded
    public boolean isCitizenDataLoaded() {
        return currentCitizenId != null && !currentCitizenId.trim().isEmpty();
    }
} 