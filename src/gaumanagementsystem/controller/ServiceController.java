/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

import gaumanagementsystem.view.Service;
import gaumanagementsystem.view.AddServiceForm;
import gaumanagementsystem.model.ServiceModel;
import gaumanagementsystem.view.LoginView;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bisho
 */
public class ServiceController {
    private Service view;
    private List<ServiceModel> services; // In a real app, this would be a DAO or a service layer

    public ServiceController(Service view) {
        this.view = view;
        this.services = new ArrayList<>(); // Initialize with dummy data for now
        loadDummyData();
        initializeListeners();
        updateServiceTable();
    }

    private void initializeListeners() {
        view.addMenuButtonListener(e -> toggleMenu());
        view.addDeleteButtonListener(e -> deleteService());
        view.addUpdateButtonListener(e -> updateService());
        view.addRefreshButtonListener(e -> refreshServices());
        view.addAddButtonListener(e -> addService());
        view.addSearchTextFieldListener(new SearchServiceListener());
    }

    private void loadDummyData() {
        services.add(new ServiceModel("S001", "Water Supply", "Ram Bahadur", "Ward 1", "Installation of new water pipelines.", "Completed"));
        services.add(new ServiceModel("S002", "Road Maintenance", "Sita Devi", "Ward 3", "Repair of potholes on main road.", "In Progress"));
        services.add(new ServiceModel("S003", "Electricity Upgrade", "Hari Prasad", "Ward 2", "Replacing old electrical poles.", "Pending"));
    }

    private void updateServiceTable() {
        DefaultTableModel model = (DefaultTableModel) view.getServiceTable().getModel();
        model.setRowCount(0); // Clear existing data
        for (ServiceModel service : services) {
            model.addRow(new Object[]{
                service.getServiceId(),
                service.getServiceName(),
                "N/A", // SubmittedAt - not in model, add if needed
                service.getNameOfCitizen(),
                service.getWard(),
                service.getDescription(),
                service.getStatus()
            });
        }
    }

    private void addService() {
        AddServiceForm addServiceForm = new AddServiceForm();
        new AddServiceController(addServiceForm).open(); // Open add service form
        // After the addServiceForm is closed, you might want to refresh the table
        addServiceForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                refreshServices(); // Refresh table when add form is closed
            }
        });
    }

    private void deleteService() {
        int selectedRow = view.getServiceTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a service to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this service?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // In a real app, delete from database
            services.remove(selectedRow);
            updateServiceTable();
            JOptionPane.showMessageDialog(view, "Service deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateService() {
        int selectedRow = view.getServiceTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a service to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // For updating, you might open a similar form to AddServiceForm but pre-filled with data
        JOptionPane.showMessageDialog(view, "Update functionality to be implemented. Selected Service: " + services.get(selectedRow).getServiceName());
    }

    private void refreshServices() {
        // In a real app, reload data from database
        // For now, re-populate with dummy data or existing data if not modified
        updateServiceTable();
        JOptionPane.showMessageDialog(view, "Service list refreshed.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    class SearchServiceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String query = view.getSearchQuery().toLowerCase();
            List<ServiceModel> filteredServices = new ArrayList<>();
            for (ServiceModel service : services) {
                if (service.getServiceName().toLowerCase().contains(query) ||
                    service.getNameOfCitizen().toLowerCase().contains(query) ||
                    service.getWard().toLowerCase().contains(query) ||
                    service.getDescription().toLowerCase().contains(query) ||
                    service.getStatus().toLowerCase().contains(query) ||
                    service.getServiceId().toLowerCase().contains(query)) {
                    filteredServices.add(service);
                }
            }
            updateServiceTable(filteredServices);
        }

        private void updateServiceTable(List<ServiceModel> filteredServices) {
            DefaultTableModel model = (DefaultTableModel) view.getServiceTable().getModel();
            model.setRowCount(0); // Clear existing data
            for (ServiceModel service : filteredServices) {
                model.addRow(new Object[]{
                    service.getServiceId(),
                    service.getServiceName(),
                    "N/A", // SubmittedAt
                    service.getNameOfCitizen(),
                    service.getWard(),
                    service.getDescription(),
                    service.getStatus()
                });
            }
        }
    }

    private void toggleMenu() {
        JOptionPane.showMessageDialog(view, "Menu toggle functionality for Service View.");
        // This would typically interact with a side menu panel in the view
    }

    public void open() {
        view.setVisible(true);
    }
}
