/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

/**
 *
 * @author wange
 */

import gaumanagementsystem.view.ProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JOptionPane;

public class ProfileController {

    private ProfileView view;

    public ProfileController(ProfileView view) {
        this.view = view;
        initController();
        loadCitizenProfile(); // Automatically load profile at start (optional)
    }

    private void initController() {
        view.getEditButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProfile();
            }
        });

        view.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose(); // Close the profile window
            }
        });
    }

    private void loadCitizenProfile() {
        String citizenNumber = view.getCitizenIdField().getText().trim();

        if (!citizenNumber.isEmpty()) {
            Map<String, String> data = CitizenController.getCitizenById(citizenNumber);
            if (data != null && !data.isEmpty()) {
                view.getNameField().setText(data.get("name"));
                view.getPhoneField().setText(data.get("phone"));
                view.getAddressField().setText(data.get("address"));
                view.getGenderField().setText(data.get("gender"));
                view.getEmailField().setText(data.get("email"));
                view.getFatherNameField().setText(data.get("father_name"));
                view.getMotherNameField().setText(data.get("mother_name"));
            } else {
                JOptionPane.showMessageDialog(view, "No citizen found with ID: " + citizenNumber);
            }
        }
    }

    private void updateProfile() {
        String citizenNumber = view.getCitizenIdField().getText().trim();
        String name = view.getNameField().getText().trim();
        String phone = view.getPhoneField().getText().trim();
        String address = view.getAddressField().getText().trim();
        String gender = view.getGenderField().getText().trim();
        String email = view.getEmailField().getText().trim();

        boolean success = CitizenController.updateCitizen(
            citizenNumber, name, phone, address, gender, email
        );

        if (success) {
            JOptionPane.showMessageDialog(view, "Profile updated successfully!");
        } else {
            JOptionPane.showMessageDialog(view, "Failed to update profile.");
        }
    }
}

