package gaumanagementsystem.controller;

import gaumanagementsystem.dao.UserDAO;
import gaumanagementsystem.dao.impl.UserDAOImpl;
import gaumanagementsystem.util.EmailUtilDemo;
import gaumanagementsystem.util.PasswordUtil;
import gaumanagementsystem.view.ForgotPasswordView;
import gaumanagementsystem.model.User;
import java.util.Optional;

/**
 * Demo Controller for handling forgot password functionality without email dependencies
 */
public class ForgotPasswordControllerDemo {
    
    private ForgotPasswordView view;
    private UserDAO userDAO;
    
    public ForgotPasswordControllerDemo(ForgotPasswordView view) {
        this.view = view;
        this.userDAO = new UserDAOImpl();
    }
    
    /**
     * Sends verification code to the specified email address (demo mode)
     * 
     * @param email The email address to send code to
     * @return true if code was sent successfully, false otherwise
     */
    public boolean sendVerificationCode(String email) {
        try {
            // First check if email exists in database
            Optional<User> userOpt = userDAO.findByEmail(email);
            if (!userOpt.isPresent()) {
                System.out.println("Email not found in database: " + email);
                return false;
            }
            
            // Send verification code via demo email utility
            boolean sent = EmailUtilDemo.sendPasswordResetCode(email);
            
            if (sent) {
                System.out.println("Demo verification code sent successfully to: " + email);
            } else {
                System.err.println("Failed to send demo verification code to: " + email);
            }
            
            return sent;
            
        } catch (Exception e) {
            System.err.println("Error sending verification code: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Verifies the provided code against the stored code
     * 
     * @param email The email address
     * @param code The verification code to verify
     * @return true if code is valid, false otherwise
     */
    public boolean verifyCode(String email, String code) {
        try {
            // Verify code using demo email utility
            boolean isValid = EmailUtilDemo.verifyCode(email, code);
            
            if (isValid) {
                System.out.println("Demo verification code verified successfully for: " + email);
            } else {
                System.out.println("Invalid or expired demo verification code for: " + email);
            }
            
            return isValid;
            
        } catch (Exception e) {
            System.err.println("Error verifying code: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Resets the password for the specified email
     * 
     * @param email The email address
     * @param newPassword The new password
     * @return true if password was reset successfully, false otherwise
     */
    public boolean resetPassword(String email, String newPassword) {
        try {
            // Validate password strength
            if (!PasswordUtil.isPasswordStrong(newPassword)) {
                String strengthMessage = PasswordUtil.getPasswordStrengthMessage(newPassword);
                System.err.println("Weak password rejected: " + strengthMessage);
                view.showError(strengthMessage);
                return false;
            }
            
            // Find user by email
            Optional<User> userOpt = userDAO.findByEmail(email);
            if (!userOpt.isPresent()) {
                System.err.println("User not found for email: " + email);
                return false;
            }
            
            User user = userOpt.get();
            
            // Hash the new password
            String hashedPassword = PasswordUtil.hashPassword(newPassword);
            
            // Update password in database
            boolean success = userDAO.updatePassword(user.getId(), hashedPassword);
            
            if (success) {
                System.out.println("Password reset successfully for user: " + email);
            } else {
                System.err.println("Failed to update password in database for user: " + email);
            }
            
            return success;
            
        } catch (Exception e) {
            System.err.println("Error resetting password: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Validates email format and existence in database
     * 
     * @param email The email to validate
     * @return true if email is valid and exists, false otherwise
     */
    public boolean validateEmail(String email) {
        try {
            // Check email format
            if (email == null || email.trim().isEmpty()) {
                return false;
            }
            
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                return false;
            }
            
            // Check if email exists in database
            Optional<User> userOpt = userDAO.findByEmail(email);
            return userOpt.isPresent();
            
        } catch (Exception e) {
            System.err.println("Error validating email: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Gets information about the email configuration status
     * 
     * @return Configuration status message
     */
    public String getEmailConfigurationStatus() {
        return "Running in demo mode - use verification code: 123456";
    }
    
    /**
     * Gets the code expiry time in minutes
     * 
     * @return Expiry time in minutes
     */
    public int getCodeExpiryMinutes() {
        return EmailUtilDemo.getCodeExpiryMinutes();
    }
} 