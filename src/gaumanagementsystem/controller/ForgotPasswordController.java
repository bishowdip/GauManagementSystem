package gaumanagementsystem.controller;

import gaumanagementsystem.dao.UserDAO;
import gaumanagementsystem.dao.impl.UserDAOImpl;
import gaumanagementsystem.util.EmailUtil;
import gaumanagementsystem.util.PasswordUtil;
import gaumanagementsystem.view.ForgotPasswordView;
import gaumanagementsystem.model.User;
import java.util.Optional;

/**
 * Controller for handling forgot password functionality with email verification
 */
public class ForgotPasswordController {
    
    private ForgotPasswordView view;
    private UserDAO userDAO;
    
    public ForgotPasswordController(ForgotPasswordView view) {
        this.view = view;
        this.userDAO = new UserDAOImpl();
    }
    
    /**
     * Sends verification code to the specified email address
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
            
            // Check if email configuration is valid
            if (!EmailUtil.isEmailConfigured()) {
                System.err.println("Email is not properly configured");
                // For demo purposes, simulate sending code
                System.out.println("Demo mode: Verification code would be sent to " + email);
                System.out.println("Demo code: 123456");
                return true;
            }
            
            // Send verification code via email
            boolean sent = EmailUtil.sendPasswordResetCode(email);
            
            if (sent) {
                System.out.println("Verification code sent successfully to: " + email);
            } else {
                System.err.println("Failed to send verification code to: " + email);
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
            // Check if email configuration is valid
            if (!EmailUtil.isEmailConfigured()) {
                // For demo purposes, accept demo code
                System.out.println("Demo mode: Verifying code for " + email);
                boolean isValid = "123456".equals(code);
                if (isValid) {
                    System.out.println("Demo code verified successfully");
                } else {
                    System.out.println("Demo code verification failed");
                }
                return isValid;
            }
            
            // Verify code using EmailUtil
            boolean isValid = EmailUtil.verifyCode(email, code);
            
            if (isValid) {
                System.out.println("Verification code verified successfully for: " + email);
            } else {
                System.out.println("Invalid or expired verification code for: " + email);
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
            
            // Update password in database (DAO will handle hashing)
            boolean success = userDAO.updatePassword(user.getId(), newPassword);
            
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
        if (EmailUtil.isEmailConfigured()) {
            return "Email is configured and ready";
        } else {
            return "Email not configured - running in demo mode";
        }
    }
    
    /**
     * Gets the code expiry time in minutes
     * 
     * @return Expiry time in minutes
     */
    public int getCodeExpiryMinutes() {
        return EmailUtil.getCodeExpiryMinutes();
    }
} 