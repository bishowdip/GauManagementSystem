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
        String name = view.getNameTextField().getText().trim();
        String username = view.getUsernameTextField().getText().trim();
        String email = view.getEmailTextField().getText().trim();
        String gender = view.getGenderTextField().getText().trim();
        String password = new String(view.getPasswordField().getPassword());
        String confirmPassword = new String(view.getConfirmPasswordField().getPassword());

        // Validate all fields
        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || 
            gender.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
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
            // Check if user exists
            if (userDAO.checkUserExists(username, email)) {
                JOptionPane.showMessageDialog(view, "Username or email already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create user object
            User user = new User(name, username, email, gender, password);

            // Register user
            if (userDAO.registerUser(user)) {
                JOptionPane.showMessageDialog(view, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Redirect to login
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
                // TODO: Navigate to appropriate dashboard based on user role
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} 