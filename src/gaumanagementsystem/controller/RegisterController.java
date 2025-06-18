/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
*/

package gaumanagementsystem.controller;

import gaumanagementsystem.dao.UserDao;
import gaumanagementsystem.model.UserData;
import gaumanagementsystem.view.RegisterView;
import gaumanagementsystem.view.LoginView;
import gaumanagementsystem.view.DashboardUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author wangel
 */
public class RegisterController {
    RegisterView view;

    public RegisterController(RegisterView view) {
        this.view = view;
        setupActionListeners();
    }

    private void setupActionListeners() {
        // Register button action listener
        view.getRegisterUserButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });

        // Back to login button action listener
        view.getBackToLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToLogin();
            }
        });

        // Login button action listener
        view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToLogin();
            }
        });

        // View password checkbox action listener
        view.getViewPasswordCheckBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePasswordVisibility();
            }
        });

        // Email field action listener (for Enter key)
        view.getEmailTextField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move focus to password field when Enter is pressed
                view.getPasswordField().requestFocus();
            }
        });

        // Password field action listener (for Enter key)
        view.getPasswordField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move focus to confirm password field when Enter is pressed
                view.getConfirmPasswordField().requestFocus();
            }
        });

        // Confirm password field action listener (for Enter key)
        view.getConfirmPasswordField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Trigger registration when Enter is pressed
                handleRegistration();
            }
        });
    }

    private void handleRegistration() {
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

        // Role validation
        if (!role.equals("Admin") && !role.equals("User")) {
            JOptionPane.showMessageDialog(view, "Please select a valid role (Admin or User)", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            UserData user = new UserData(email, role, password, confirmPassword);
            UserDao userDao = new UserDao();
            boolean result = userDao.register(user);

            if (result) {
                JOptionPane.showMessageDialog(view, "Registration Successful! Redirecting to Dashboard...", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Open Dashboard with the user's role
                DashboardUser dashboard = new DashboardUser(role);
                dashboard.setVisible(true);
                close();
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(view, "An error occurred during registration. Please try again later.", "System Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void navigateToLogin() {
        LoginView login = new LoginView();
        login.setVisible(true);
        close();
    }

    private void togglePasswordVisibility() {
        if (view.getViewPasswordCheckBox().isSelected()) {
            // Show passwords
            view.getPasswordField().setEchoChar('\u0000');
            view.getConfirmPasswordField().setEchoChar('\u0000');
            view.getViewPasswordCheckBox().setText("Hide Password");
        } else {
            // Hide passwords
            view.getPasswordField().setEchoChar('•');
            view.getConfirmPasswordField().setEchoChar('•');
            view.getViewPasswordCheckBox().setText("View Password");
        }
    }

    public void open() {
        this.view.setVisible(true);
    }

    public void close() {
        this.view.dispose();
    }
}
