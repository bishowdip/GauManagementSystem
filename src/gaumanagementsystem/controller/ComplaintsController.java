/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;


import gaumanagementsystem.model.UserData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author SONIC
 */
public class ComplaintsController {
    private final ComplaintsView view;
    private final UserData user;

    public ComplaintsController(ComplaintsView view, UserData user) {
        this.view = view;
        this.user = user;
        // Register the ActionListener for the submit button
        this.view.getSubmitButton().addActionListener(new SubmitButtonListener());
    }
    public void open() {
        view.setVisible(true);
    }

    public void close() {
        view.dispose();
    }
    class ComplaintsView implements  ActionListener{
        @Override
         public void actionPerformed(ActionEvent e) {
            String name = view.getNameTextField().getText();
            String reportedDate = view.getReportedDateField().getText();
            String email = view.getEmailTextField().getText();
            String details = view.getDetailsArea().getText();
            String feedback = view.getFeedbackArea().getText();

            // Example validation
            if (name.isEmpty() || email.isEmpty() || details.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Fill in all the required fields");
                return;
            }

            // Here you can create a Complaint object and process it as needed
            // Complaint complaint = new Complaint(name, reportedDate, email, details, feedback);
            // Save complaint, update model, etc.

            JOptionPane.showMessageDialog(view, "Complaint submitted successfully!");
            // Optionally clear fields
            view.getNameTextField().setText("");
            view.getReportedDateField().setText("");
            view.getEmailTextField().setText("");
            view.getDetailsArea().setText("");
            view.getFeedbackArea().setText("");
        }
    }
}
       
