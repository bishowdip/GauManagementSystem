/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

import gaumanagementsystem.model.ServiceRequest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


/**
 *
 * @author ASUS
 */
public class ServiceController {
 

    private final ServiceRequestView serviceView;
    private final ServiceDao serviceDao;

    /**
     *
     * @param serviceView
     */
    public ServiceController(ServiceRequestView serviceView) {
        this.serviceView = serviceView;
        this.serviceDao = new ServiceDao();

        // Load service requests into the table
        loadServiceRequests();

        // Set up the "+ Requested Service" button action
        this.serviceView.addNewServiceButtonListener((ActionEvent e) -> {
            openNewServiceForm();
        });
    }

    private void loadServiceRequests() {
        List<ServiceRequest> requestList = serviceDao.getAllRequests();
        serviceView.setServiceTableData(requestList);
    }

    private void openNewServiceForm() {
        NewServiceFormView newServiceView = new NewServiceFormView();
        newServiceView.setVisible(true);
        newServiceView.setLocationRelativeTo(null); // center on screen

        // Optionally pass this controller to update the table after adding new request
        // Or use a callback mechanism
    }

    private static class NewServiceFormView {

        public NewServiceFormView() {
        }

        private void setVisible(boolean b) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private void setLocationRelativeTo(Object object) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    private static class ServiceDao {

        public ServiceDao() {
        }

        private List<ServiceRequest> getAllRequests() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    private static class ServiceRequestView {

        public ServiceRequestView() {
        }

        private void setServiceTableData(List<ServiceRequest> requestList) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private void addNewServiceButtonListener(ActionListener actionListener) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

    }
}

     