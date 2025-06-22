package gaumanagementsystem.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

/**
 * Demo version of EmailUtil that works without JavaMail API
 * This class simulates email functionality for testing purposes
 */
public class EmailUtilDemo {
    
    // In-memory storage for verification codes (demo purposes only)
    private static final Map<String, VerificationCode> verificationCodes = new ConcurrentHashMap<>();
    
    // Demo configuration
    private static final int CODE_EXPIRY_MINUTES = 10;
    private static final String DEMO_CODE = "123456";
    
    /**
     * Inner class to store verification code with timestamp
     */
    private static class VerificationCode {
        private final String code;
        private final long timestamp;
        
        public VerificationCode(String code, long timestamp) {
            this.code = code;
            this.timestamp = timestamp;
        }
        
        public String getCode() {
            return code;
        }
        
        public boolean isExpired() {
            long currentTime = System.currentTimeMillis();
            long expiryTime = timestamp + (CODE_EXPIRY_MINUTES * 60 * 1000);
            return currentTime > expiryTime;
        }
    }
    
    /**
     * Simulates sending a password reset code via email
     * In demo mode, this always returns true and uses a fixed code
     * 
     * @param email The recipient email address
     * @return true if code was "sent" successfully
     */
    public static boolean sendPasswordResetCode(String email) {
        try {
            // Generate demo code (always 123456 for demo)
            String code = DEMO_CODE;
            
            // Store the code with current timestamp
            verificationCodes.put(email, new VerificationCode(code, System.currentTimeMillis()));
            
            // Simulate email sending delay
            Thread.sleep(1000);
            
            // Log for demo purposes
            System.out.println("=== DEMO MODE EMAIL ===");
            System.out.println("To: " + email);
            System.out.println("Subject: Password Reset Verification Code - Gau Management System");
            System.out.println("Verification Code: " + code);
            System.out.println("Valid for: " + CODE_EXPIRY_MINUTES + " minutes");
            System.out.println("======================");
            
            return true;
            
        } catch (Exception e) {
            System.err.println("Demo email sending failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Verifies a code against the stored code for the given email
     * 
     * @param email The email address
     * @param code The code to verify
     * @return true if code is valid and not expired
     */
    public static boolean verifyCode(String email, String code) {
        try {
            VerificationCode storedCode = verificationCodes.get(email);
            
            if (storedCode == null) {
                System.out.println("No verification code found for email: " + email);
                return false;
            }
            
            if (storedCode.isExpired()) {
                System.out.println("Verification code expired for email: " + email);
                verificationCodes.remove(email); // Clean up expired code
                return false;
            }
            
            boolean isValid = storedCode.getCode().equals(code);
            
            if (isValid) {
                System.out.println("Verification code verified successfully for: " + email);
                verificationCodes.remove(email); // Remove used code
            } else {
                System.out.println("Invalid verification code for: " + email);
            }
            
            return isValid;
            
        } catch (Exception e) {
            System.err.println("Error verifying code: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if email is configured (always false in demo mode)
     * 
     * @return false (demo mode)
     */
    public static boolean isEmailConfigured() {
        return false; // Always demo mode
    }
    
    /**
     * Gets the code expiry time in minutes
     * 
     * @return Expiry time in minutes
     */
    public static int getCodeExpiryMinutes() {
        return CODE_EXPIRY_MINUTES;
    }
    
    /**
     * Sends a test email (demo mode simulation)
     * 
     * @param testEmail The email to test
     * @return true (always succeeds in demo mode)
     */
    public static boolean sendTestEmail(String testEmail) {
        System.out.println("=== DEMO MODE TEST EMAIL ===");
        System.out.println("To: " + testEmail);
        System.out.println("Subject: Test Email - Gau Management System");
        System.out.println("Message: This is a test email from Gau Management System (Demo Mode)");
        System.out.println("============================");
        return true;
    }
    
    /**
     * Cleans up expired verification codes
     */
    public static void cleanupExpiredCodes() {
        verificationCodes.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
    
    /**
     * Gets the current number of stored verification codes (for debugging)
     * 
     * @return Number of stored codes
     */
    public static int getStoredCodeCount() {
        cleanupExpiredCodes();
        return verificationCodes.size();
    }
    
    /**
     * Clears all stored verification codes (for testing)
     */
    public static void clearAllCodes() {
        verificationCodes.clear();
        System.out.println("All verification codes cleared (demo mode)");
    }
} 