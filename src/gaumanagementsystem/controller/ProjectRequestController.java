/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

import gaumanagementsystem.view.ProjectRequests;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;

/**
 *
 * @author SONIC
 */

public class ProjectRequestController {
    private final List<ProjectRequests> projectList;
    private ProjectRequestPopupView popupView;

    public ProjectRequestController(List<ProjectRequests> projectList, JTable table) {
        this.projectList = projectList;
    }
    public void showPopup(JFrame parent) {
        popupView = new ProjectRequestPopupView(parent);
        popupView.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProjectRequest pr = new ProjectRequest(
                    popupView.getRequestIdField().getText(),
                    popupView.getProjectNameField().getText(),
                    popupView.getStartedDateField().getText(),
                    popupView.getWardField().getText(),
                    popupView.getExpectedToEndField().getText(),
                    popupView.getDescriptionField().getText(),
                    popupView.getStatusField().getText(),
                    popupView.getBudgetField().getText()
                );
                projectList.add(pr);
                updateTable();
                popupView.dispose();
            }
        });
        popupView.setVisible(true);
    }

    public void updateTable() {
        // Update your JTable model here with the new projectList
        // For example, use DefaultTableModel and setRowCount(0) then addRow for each ProjectRequest
    }

    public void search(String keyword) {
        // Filter projectList by keyword and update the table
    }
}
}
