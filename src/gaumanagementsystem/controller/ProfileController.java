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
import gaumanagementsystem.model.CitizenData;
import gaumanagementsystem.view.EditProfileView;

import javax.swing.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

public class ProfileController {
    private EditProfileView view;
    private CitizenDao dao;
    private String selectedImagePath;

    public ProfileController(EditProfileView view) {
        this.view = view;

        try {
            dao = new CitizenDao(CitizenController.getConnection());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        selectedImagePath = null;

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

    private void saveProfile() {
        if (view.getNameField().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Name cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

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
                selectedImagePath
        );

        boolean success = dao.updateCitizen(citizen);

        if (success) {
            JOptionPane.showMessageDialog(view, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Failed to update profile. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(view);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            int confirm = JOptionPane.showConfirmDialog(
                    view,
                    "Use this image as your profile picture?\n" + file.getName(),
                    "Confirm Upload",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                selectedImagePath = file.getAbsolutePath();
                ImageIcon icon = new ImageIcon(new ImageIcon(selectedImagePath).getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
                view.getProfileImageLabel().setIcon(icon);
            }
        }
    }
}

