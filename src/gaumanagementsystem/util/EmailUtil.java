package gaumanagementsystem.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Email utility class for sending password reset codes
 * Uses Gmail SMTP with app-specific password for authentication
 */
public class EmailUtil {
    
    // Email configuration
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_USERNAME = "bishodip123@gmail.com"; // Your Gmail address
    private static final String EMAIL_PASSWORD = "mzhg bqng svnf hftk"; // Your app password
    private static final String FROM_NAME = "Gau Management System";
    
    // Code storage and expiration
    private static final ConcurrentHashMap<String, VerificationCode> verificationCodes = new ConcurrentHashMap<>();
    private static final int CODE_EXPIRY_MINUTES = 10; // Code expires in 10 minutes
    private static final int CODE_LENGTH = 6; // 6-digit verification code
    
    // Scheduler for code cleanup
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    static {
        // Start cleanup task to remove expired codes every minute
        scheduler.scheduleAtFixedRate(EmailUtil::cleanupExpiredCodes, 1, 1, TimeUnit.MINUTES);
    }
    
    /**
     * Inner class to store verification code with timestamp
     */
    private static class VerificationCode {
        private final String code;
        private final long timestamp;
        
        public VerificationCode(String code) {
            this.code = code;
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getCode() {
            return code;
        }
        
        public boolean isExpired() {
            return System.currentTimeMillis() - timestamp > CODE_EXPIRY_MINUTES * 60 * 1000;
        }
    }
    
    /**
     * Sends a password reset code to the specified email address
     * 
     * @param toEmail The recipient's email address
     * @return true if email was sent successfully, false otherwise
     */
    public static boolean sendPasswordResetCode(String toEmail) {
        try {
            // Generate verification code
            String verificationCode = generateVerificationCode();
            
            // Store the code with email
            verificationCodes.put(toEmail.toLowerCase(), new VerificationCode(verificationCode));
            
            // Configure email properties
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            
            // Create session with authentication
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
                }
            });
            
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME, FROM_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Password Reset Code - Gau Management System");
            
            // Create email content
            String emailContent = createPasswordResetEmailContent(verificationCode);
            message.setContent(emailContent, "text/html; charset=utf-8");
            
            // Send email
            Transport.send(message);
            
            System.out.println("Password reset code sent successfully to: " + toEmail);
            return true;
            
        } catch (Exception e) {
            System.err.println("Failed to send password reset email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Verifies if the provided code matches the stored code for the email
     * 
     * @param email The email address
     * @param code The verification code to check
     * @return true if code is valid and not expired, false otherwise
     */
    public static boolean verifyCode(String email, String code) {
        VerificationCode storedCode = verificationCodes.get(email.toLowerCase());
        
        if (storedCode == null) {
            System.out.println("No verification code found for email: " + email);
            return false;
        }
        
        if (storedCode.isExpired()) {
            verificationCodes.remove(email.toLowerCase());
            System.out.println("Verification code expired for email: " + email);
            return false;
        }
        
        boolean isValid = storedCode.getCode().equals(code);
        if (isValid) {
            // Remove code after successful verification
            verificationCodes.remove(email.toLowerCase());
            System.out.println("Verification code verified successfully for email: " + email);
        } else {
            System.out.println("Invalid verification code for email: " + email);
        }
        
        return isValid;
    }
    
    /**
     * Generates a random 6-digit verification code
     * 
     * @return 6-digit verification code as string
     */
    private static String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generate 6-digit number
        return String.valueOf(code);
    }
    
    /**
     * Creates the HTML content for password reset email
     * 
     * @param verificationCode The verification code to include
     * @return HTML email content
     */
    private static String createPasswordResetEmailContent(String verificationCode) {
        StringBuilder content = new StringBuilder();
        content.append("<!DOCTYPE html>");
        content.append("<html><head>");
        content.append("<style>");
        content.append("body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }");
        content.append(".container { max-width: 600px; margin: 0 auto; padding: 20px; }");
        content.append(".header { background-color: #4CAF50; color: white; text-align: center; padding: 20px; border-radius: 5px 5px 0 0; }");
        content.append(".content { background-color: #f9f9f9; padding: 30px; border-radius: 0 0 5px 5px; }");
        content.append(".code { background-color: #fff; border: 2px solid #4CAF50; padding: 15px; text-align: center; font-size: 24px; font-weight: bold; color: #4CAF50; margin: 20px 0; border-radius: 5px; }");
        content.append(".warning { background-color: #fff3cd; border-left: 4px solid #ffc107; padding: 10px; margin: 20px 0; }");
        content.append(".footer { text-align: center; margin-top: 30px; font-size: 12px; color: #666; }");
        content.append("</style></head><body>");
        content.append("<div class='container'>");
        content.append("<div class='header'>");
        content.append("<h1>🔐 Password Reset Request</h1>");
        content.append("<p>Gau Management System</p>");
        content.append("</div>");
        content.append("<div class='content'>");
        content.append("<h2>Hello,</h2>");
        content.append("<p>We received a request to reset your password for your Gau Management System account.</p>");
        content.append("<p>Your verification code is:</p>");
        content.append("<div class='code'>").append(verificationCode).append("</div>");
        content.append("<div class='warning'>");
        content.append("<strong>⚠️ Important:</strong>");
        content.append("<ul>");
        content.append("<li>This code will expire in ").append(CODE_EXPIRY_MINUTES).append(" minutes</li>");
        content.append("<li>Do not share this code with anyone</li>");
        content.append("<li>If you didn't request this reset, please ignore this email</li>");
        content.append("</ul></div>");
        content.append("<p>Best regards,<br><strong>Gau Management System Team</strong></p>");
        content.append("</div>");
        content.append("<div class='footer'>");
        content.append("<p>This is an automated message. Please do not reply to this email.</p>");
        content.append("<p>© 2024 Gau Management System. All rights reserved.</p>");
        content.append("</div></div></body></html>");
        
        return content.toString();
    }
    
    /**
     * Removes expired verification codes from memory
     */
    private static void cleanupExpiredCodes() {
        verificationCodes.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
    
    /**
     * Checks if email configuration is valid
     * 
     * @return true if email is configured properly
     */
    public static boolean isEmailConfigured() {
        return !EMAIL_USERNAME.equals("your-email@gmail.com") && 
               !EMAIL_PASSWORD.isEmpty() && 
               EMAIL_PASSWORD.length() > 10;
    }
    
    /**
     * Tests email configuration by sending a test email
     * 
     * @param testEmail Email to send test message to
     * @return true if test email was sent successfully
     */
    public static boolean testEmailConfiguration(String testEmail) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", SMTP_PORT);
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
                }
            });
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME, FROM_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(testEmail));
            message.setSubject("Test Email - Gau Management System");
            message.setText("This is a test email to verify email configuration.");
            
            Transport.send(message);
            return true;
            
        } catch (Exception e) {
            System.err.println("Email configuration test failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Gets the number of minutes until code expiry
     * 
     * @return expiry time in minutes
     */
    public static int getCodeExpiryMinutes() {
        return CODE_EXPIRY_MINUTES;
    }
    
    /**
     * Shutdown the scheduler (call when application closes)
     */
    public static void shutdown() {
        scheduler.shutdown();
    }
} 