package gaumanagementsystem.controller;

import gaumanagementsystem.dao.UserDAO;
import gaumanagementsystem.dao.impl.UserDAOImpl;
import gaumanagementsystem.model.User;
import gaumanagementsystem.util.PasswordUtil;
import gaumanagementsystem.view.LoginView;
import gaumanagementsystem.view.RegisterView;
import javax.swing.JOptionPane;

/**
 *
 * @author bishodip
 */
public class UserController {
    private final UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAOImpl();
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

        // Validate password strength
        if (!PasswordUtil.isPasswordStrong(password)) {
            String strengthMessage = PasswordUtil.getPasswordStrengthMessage(password);
            JOptionPane.showMessageDialog(view, strengthMessage, "Weak Password", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (userDAO.emailExists(email)) {
                JOptionPane.showMessageDialog(view, "Email already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Hash the password before storing
            String hashedPassword = PasswordUtil.hashPassword(password);
            
            User user = new User();
            user.setEmail(email);
            user.setPassword(hashedPassword);
            user.setRole(role);
            if (userDAO.createUser(user)) {
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
                // Pass user role, ID, and email to DashboardView for role-based functionality
                String userRole = user.getRole() != null ? user.getRole() : "user"; // Default to user if role is null
                String userId = String.valueOf(user.getId());
                // Create a custom DashboardView that accepts user email for profile lookup
                gaumanagementsystem.view.DashboardView dashboard = new gaumanagementsystem.view.DashboardView(userRole, userId);
                // Store user email for profile lookup - we'll need to add this to DashboardView
                dashboard.setUserEmail(email);
                dashboard.setVisible(true);
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Invalid email or password. Please check your credentials and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} 