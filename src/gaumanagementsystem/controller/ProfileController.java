/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

/**
 *
 * @author wange
 */

import gaumanagementsystem.view.EditProfileView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ProfileController {

    private EditProfileView view;

    public ProfileController(EditProfileView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        // Load citizen data
        String citizenId = view.getCitizenIdField().getText().trim();
        loadCitizenProfile(citizenId);

        // Button Actions
        view.getEditButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCitizenProfile();
            }
        });

        view.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose(); // Close profile view
            }
        });
        
    }

    private void loadCitizenProfile(String citizenNumber) {
        Map<String, String> citizen = CitizenController.getCitizenById(citizenNumber);
        if (!citizen.isEmpty()) {
            view.getNameField().setText(citizen.get("name"));
            view.getEmailField().setText(citizen.get("email"));
            view.getAddressField().setText(citizen.get("address"));
            view.getGenderField().setText(citizen.get("gender"));
            view.getPhoneField().setText(citizen.get("phone"));
            view.getFatherNameField().setText(citizen.get("father_name"));
            view.getMotherNameField().setText(citizen.get("mother_name"));
        } else {
            System.out.println("Citizen not found.");
        }
    }

    private void updateCitizenProfile() {
        
        
        String citizenNumber = view.getCitizenIdField().getText().trim();
        String name = view.getNameField().getText().trim();
        String email = view.getEmailField().getText().trim();
        String address = view.getAddressField().getText().trim();
        String gender = view.getGenderField().getText().trim();
        String phone = view.getPhoneField().getText().trim();

        boolean success = CitizenController.updateCitizen(
            citizenNumber, name, phone, address, gender, email
        );

        if (success) {
            System.out.println("Citizen updated successfully.");
        } else {
            System.out.println("Failed to update citizen.");
        }
    }
    
    
}
