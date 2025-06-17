/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

/**
 *
 * @author wange
 */
import gaumanagementsystem.dao.CitizenDao;
import gaumanagementsystem.database.MySqlConnection;
import gaumanagementsystem.model.CitizenData;
import gaumanagementsystem.view.EditProfileView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ProfileController {
    private EditProfileView view;
    private CitizenDao dao;
    private String selectedImagePath;
    private String uploadedImagePath;
    private static final String UPLOAD_DIR = "uploads/citizen_images/";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};

    public ProfileController(EditProfileView view) {
        this.view = view;
        this.selectedImagePath = null;
        this.uploadedImagePath = null;

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

        // Create upload directory if it doesn't exist
        createUploadDirectory();

        // Add action listeners
        view.getEditButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        view,
                        "Are you sure you want to save the changes?",
                        "Confirm Update",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    saveProfile();
                }
            }
        });

        view.getUploadButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uploadImage();
            }
        });
    }

    private void createUploadDirectory() {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Failed to create upload directory.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveProfile() {
        if (view.getNameField().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Name cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Show confirmation dialog before saving
        int confirmSave = JOptionPane.showConfirmDialog(
            view,
            "Are you sure you want to save these changes?\n\n" +
            "Name: " + view.getNameField().getText() + "\n" +
            "Email: " + view.getEmailField().getText() + "\n" +
            "Phone: " + view.getPhoneField().getText() + "\n" +
            "Address: " + view.getAddressField().getText(),
            "Confirm Save Changes",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmSave != JOptionPane.YES_OPTION) {
            return; // User cancelled the save operation
        }

        // Use the uploaded image path if available, otherwise use the selected path
        String imagePath = (uploadedImagePath != null) ? uploadedImagePath : selectedImagePath;

        CitizenData citizen = new CitizenData(
                view.getCitizenIdField().getText(),
                view.getNameField().getText(),
                view.getEmailField().getText(),
                view.getDateOfBirthField().getText(),
                view.getAddressField().getText(),
                view.getRadioButton(),
                view.getPhoneField().getText(),
                view.getFatherNameField().getText(),
                view.getMotherNameField().getText(),
                imagePath
        );

        boolean success = dao.updateCitizen(citizen);

        if (success) {
            JOptionPane.showMessageDialog(view, 
                "Profile updated successfully!\n\nChanges have been saved to the database.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            // Clear the uploaded image path after successful save
            uploadedImagePath = null;
        } else {
            JOptionPane.showMessageDialog(view, 
                "Failed to update profile. Please check your data and try again.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        
        // Set file filter for images only
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
                String name = f.getName().toLowerCase();
                for (String ext : ALLOWED_EXTENSIONS) {
                    if (name.endsWith(ext)) return true;
                }
                return false;
            }
            
            public String getDescription() {
                return "Image files (*.jpg, *.jpeg, *.png, *.gif, *.bmp)";
            }
        });

        int option = fileChooser.showOpenDialog(view);

        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Validate file
            if (!validateImageFile(selectedFile)) {
                return;
            }

            try {
                // Process and save the image
                String savedImagePath = processAndSaveImage(selectedFile);
                
                if (savedImagePath != null) {
                    // Update the UI
                    displayImage(savedImagePath);
                    uploadedImagePath = savedImagePath;
                    
                    JOptionPane.showMessageDialog(view, 
                        "Image uploaded successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, 
                    "Failed to upload image: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateImageFile(File file) {
        // Check file size
        if (file.length() > MAX_FILE_SIZE) {
            JOptionPane.showMessageDialog(view, 
                "File size too large. Maximum size is 5MB.", 
                "File Size Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Check file extension
        String fileName = file.getName().toLowerCase();
        boolean validExtension = false;
        for (String ext : ALLOWED_EXTENSIONS) {
            if (fileName.endsWith(ext)) {
                validExtension = true;
                break;
            }
        }

        if (!validExtension) {
            JOptionPane.showMessageDialog(view, 
                "Invalid file type. Please select an image file (jpg, jpeg, png, gif, bmp).", 
                "File Type Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validate that it's actually an image
        try {
            BufferedImage image = ImageIO.read(file);
            if (image == null) {
                JOptionPane.showMessageDialog(view, 
                    "Selected file is not a valid image.", 
                    "Invalid Image", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, 
                "Error reading image file.", 
                "File Read Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private String processAndSaveImage(File originalFile) throws IOException {
        // Generate unique filename
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String originalExtension = getFileExtension(originalFile.getName());
        String newFileName = "citizen_" + timestamp + "_" + uniqueId + originalExtension;
        
        Path targetPath = Paths.get(UPLOAD_DIR, newFileName);
        
        // Copy file to upload directory
        Files.copy(originalFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        return targetPath.toString();
    }

    private void displayImage(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                ImageIcon originalIcon = new ImageIcon(imagePath);
                Image scaledImage = originalIcon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                view.getProfileImageLabel().setIcon(scaledIcon);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, 
                "Error displaying image.", 
                "Display Error", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex);
        }
        return ".jpg"; // Default extension
    }

    // Method to load existing profile image
    public void loadProfileImage(String imagePath) {
        if (imagePath != null && !imagePath.trim().isEmpty()) {
            displayImage(imagePath);
            selectedImagePath = imagePath;
        }
    }

    // Method to remove profile image
    public void removeProfileImage() {
        view.getProfileImageLabel().setIcon(null);
        selectedImagePath = null;
        uploadedImagePath = null;
    }

    // Get current image path
    public String getCurrentImagePath() {
        return (uploadedImagePath != null) ? uploadedImagePath : selectedImagePath;
    }
}

