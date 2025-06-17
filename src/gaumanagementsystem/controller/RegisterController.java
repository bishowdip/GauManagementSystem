/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
*/

package gaumanagementsystem.controller;

import gaumanagementsystem.dao.UserDao;
import gaumanagementsystem.model.UserData;
import gaumanagementsystem.view.RegisterView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author wangel
 */
public class RegisterController {
    RegisterView view = new RegisterView();

    public RegisterController(RegisterView view) {
        this.view = view;
        RegisterUser register = new RegisterUser();
        this.view.registerUser(register);
    }

    public void open() {
        this.view.setVisible(true);
    }

    public void close() {
        this.view.dispose();
    }

    class RegisterUser implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmailTextField().getText().trim();
            String role = view.getRadioButton();
            String password = String.valueOf(view.getPasswordField().getPassword());
            String confirmPassword = String.valueOf(view.getConfirmPasswordField().getPassword());

            // Input validation
            if (email.isEmpty() || role.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill in all the fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(view, "Please enter a valid email address", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.length() < 6) {
                JOptionPane.showMessageDialog(view, "Password must be at least 6 characters long", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(view, "Passwords do not match", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                UserData user = new UserData(email, role, password, confirmPassword);
                UserDao userDao = new UserDao();
                boolean result = userDao.register(user);

                if (result) {
                    JOptionPane.showMessageDialog(view, "Registration Successful! Redirecting to Dashboard...", "Success", JOptionPane.INFORMATION_MESSAGE);
                    gaumanagementsystem.view.DashboardUser dashboard = new gaumanagementsystem.view.DashboardUser();
                    dashboard.setVisible(true);
                    close();
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(view, "An error occurred during registration. Please try again later.", "System Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
