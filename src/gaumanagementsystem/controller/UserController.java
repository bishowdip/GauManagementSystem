package gaumanagementsystem.controller;

import gaumanagementsystem.dao.UserDAO;
import gaumanagementsystem.model.User;
import gaumanagementsystem.view.LoginView;
import gaumanagementsystem.view.RegisterView;
import javax.swing.JOptionPane;

public class UserController {
    private final UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    public void handleRegistration(RegisterView view) {
        String email = view.getEmailTextField().getText().trim();
        String password = new String(view.getPasswordField().getPassword());
        String confirmPassword = new String(view.getConfirmPasswordField().getPassword());
        String role = view.getRadioButton();

        // Validate all fields
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(view, "Please enter a valid email address", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(view, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (userDAO.checkUserExists(email)) {
                JOptionPane.showMessageDialog(view, "Email already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            if (userDAO.registerUser(user, role)) {
                JOptionPane.showMessageDialog(view, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleLogin(LoginView view, String email, String password) {
        try {
            User user = userDAO.authenticateUser(email, password);
            if (user != null) {
                JOptionPane.showMessageDialog(view, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new gaumanagementsystem.view.DashboardView().setVisible(true);
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} 