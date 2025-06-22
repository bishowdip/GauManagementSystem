package gaumanagementsystem.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Password utility class for secure password hashing and verification
 * Uses PBKDF2 with SHA-256 for strong password security
 */
public class PasswordUtil {
    
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 100000; // 100,000 iterations for security
    private static final int KEY_LENGTH = 256; // 256-bit key
    private static final int SALT_LENGTH = 32; // 32-byte salt
    
    /**
     * Hashes a password with a random salt using PBKDF2
     * 
     * @param password The plain text password to hash
     * @return The hashed password in format: salt:hash
     * @throws RuntimeException if hashing fails
     */
    public static String hashPassword(String password) {
        try {
            // Generate random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            
            // Hash the password
            byte[] hash = pbkdf2(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            
            // Encode salt and hash to Base64 and combine
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);
            
            return saltBase64 + ":" + hashBase64;
            
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    /**
     * Verifies a password against a stored hash
     * 
     * @param password The plain text password to verify
     * @param storedHash The stored hash in format: salt:hash
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            // Split stored hash into salt and hash parts
            String[] parts = storedHash.split(":");
            if (parts.length != 2) {
                return false;
            }
            
            // Decode salt and hash from Base64
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] storedHashBytes = Base64.getDecoder().decode(parts[1]);
            
            // Hash the provided password with the same salt
            byte[] testHash = pbkdf2(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            
            // Compare hashes using constant-time comparison
            return constantTimeEquals(storedHashBytes, testHash);
            
        } catch (Exception e) {
            // If any error occurs during verification, return false
            return false;
        }
    }
    
    /**
     * Checks if a password appears to be already hashed
     * (contains salt:hash format with Base64 encoding)
     * 
     * @param password The password string to check
     * @return true if it appears to be hashed, false if plain text
     */
    public static boolean isPasswordHashed(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        
        try {
            String[] parts = password.split(":");
            if (parts.length != 2) {
                return false;
            }
            
            // Try to decode both parts as Base64
            Base64.getDecoder().decode(parts[0]);
            Base64.getDecoder().decode(parts[1]);
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Generates a secure random password for testing or temporary use
     * 
     * @param length The desired password length
     * @return A randomly generated password
     */
    public static String generateSecurePassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }
    
    /**
     * PBKDF2 key derivation function
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLength) 
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        
        try {
            return factory.generateSecret(spec).getEncoded();
        } finally {
            spec.clearPassword(); // Clear password from memory
        }
    }
    
    /**
     * Constant-time comparison to prevent timing attacks
     */
    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }
        
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        
        return result == 0;
    }
    
    /**
     * Validates password strength
     * 
     * @param password The password to validate
     * @return true if password meets security requirements
     */
    public static boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if ("!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(c) >= 0) hasSpecial = true;
        }
        
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
    
    /**
     * Gets password strength description
     * 
     * @param password The password to analyze
     * @return Description of password strength and requirements
     */
    public static String getPasswordStrengthMessage(String password) {
        if (password == null || password.isEmpty()) {
            return "Password is required";
        }
        
        if (password.length() < 8) {
            return "Password must be at least 8 characters long";
        }
        
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> "!@#$%^&*()_+-=[]{}|;:,.<>?".indexOf(c) >= 0);
        
        if (hasUpper && hasLower && hasDigit && hasSpecial) {
            return "Strong password";
        }
        
        StringBuilder missing = new StringBuilder("Password must contain: ");
        if (!hasUpper) missing.append("uppercase letter, ");
        if (!hasLower) missing.append("lowercase letter, ");
        if (!hasDigit) missing.append("number, ");
        if (!hasSpecial) missing.append("special character, ");
        
        return missing.substring(0, missing.length() - 2);
    }
} 