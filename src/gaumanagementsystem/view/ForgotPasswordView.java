package gaumanagementsystem.view;

import gaumanagementsystem.controller.ForgotPasswordControllerDemo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Forgot Password View - Multi-step password reset process
 * Step 1: Enter email address
 * Step 2: Enter verification code
 * Step 3: Set new password
 */
public class ForgotPasswordView extends JFrame {
    
    // Components
    private JLabel titleLabel;
    private JLabel instructionLabel;
    private JLabel emailLabel;
    private JLabel codeLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JTextField emailField;
    private JTextField codeField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton sendCodeButton;
    private JButton verifyCodeButton;
    private JButton resetPasswordButton;
    private JButton backToLoginButton;
    private JButton resendCodeButton;
    private JLabel statusLabel;
    private JProgressBar progressBar;
    
    // State management
    private String currentEmail;
    private int currentStep = 1; // 1: Email, 2: Code, 3: Password
    private ForgotPasswordControllerDemo controller;
    
    public ForgotPasswordView() {
        this.controller = new ForgotPasswordControllerDemo(this);
        initComponents();
        setLocationRelativeTo(null);
        showStep1(); // Start with email input
    }
    
    private void initComponents() {
        setTitle("Forgot Password - Gau Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        
        // Initialize components
        titleLabel = new JLabel("Password Recovery");
        instructionLabel = new JLabel("Enter your email address to receive a verification code");
        emailLabel = new JLabel("Email Address:");
        codeLabel = new JLabel("Verification Code:");
        passwordLabel = new JLabel("New Password:");
        confirmPasswordLabel = new JLabel("Confirm Password:");
        
        emailField = new JTextField();
        codeField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        
        sendCodeButton = new JButton("Send Code");
        verifyCodeButton = new JButton("Verify Code");
        resetPasswordButton = new JButton("Reset Password");
        backToLoginButton = new JButton("Back to Login");
        resendCodeButton = new JButton("Resend Code");
        
        statusLabel = new JLabel("");
        progressBar = new JProgressBar(1, 3);
        progressBar.setValue(1);
        progressBar.setStringPainted(true);
        progressBar.setString("Step 1 of 3: Email Verification");
        
        // Set bounds
        titleLabel.setBounds(150, 20, 200, 30);
        progressBar.setBounds(50, 60, 400, 25);
        instructionLabel.setBounds(50, 100, 400, 25);
        
        emailLabel.setBounds(50, 140, 120, 25);
        emailField.setBounds(180, 140, 200, 25);
        sendCodeButton.setBounds(390, 140, 100, 25);
        
        codeLabel.setBounds(50, 180, 120, 25);
        codeField.setBounds(180, 180, 200, 25);
        verifyCodeButton.setBounds(390, 180, 100, 25);
        resendCodeButton.setBounds(390, 210, 100, 25);
        
        passwordLabel.setBounds(50, 220, 120, 25);
        passwordField.setBounds(180, 220, 200, 25);
        
        confirmPasswordLabel.setBounds(50, 260, 120, 25);
        confirmPasswordField.setBounds(180, 260, 200, 25);
        
        resetPasswordButton.setBounds(180, 300, 150, 30);
        backToLoginButton.setBounds(50, 350, 120, 30);
        
        statusLabel.setBounds(50, 390, 450, 25);
        
        // Styling
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 51));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        instructionLabel.setForeground(new Color(102, 102, 102));
        
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        
        // Add action listeners
        sendCodeButton.addActionListener(e -> handleSendCode());
        verifyCodeButton.addActionListener(e -> handleVerifyCode());
        resetPasswordButton.addActionListener(e -> handleResetPassword());
        backToLoginButton.addActionListener(e -> handleBackToLogin());
        resendCodeButton.addActionListener(e -> handleResendCode());
        
        // Add enter key listeners
        emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleSendCode();
                }
            }
        });
        
        codeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleVerifyCode();
                }
            }
        });
        
        confirmPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleResetPassword();
                }
            }
        });
        
        // Add all components
        add(titleLabel);
        add(progressBar);
        add(instructionLabel);
        add(emailLabel);
        add(emailField);
        add(sendCodeButton);
        add(codeLabel);
        add(codeField);
        add(verifyCodeButton);
        add(resendCodeButton);
        add(passwordLabel);
        add(passwordField);
        add(confirmPasswordLabel);
        add(confirmPasswordField);
        add(resetPasswordButton);
        add(backToLoginButton);
        add(statusLabel);
        
        setSize(550, 450);
    }
    
    private void showStep1() {
        currentStep = 1;
        progressBar.setValue(1);
        progressBar.setString("Step 1 of 3: Email Verification");
        instructionLabel.setText("Enter your email address to receive a verification code");
        
        // Show email components
        emailLabel.setVisible(true);
        emailField.setVisible(true);
        sendCodeButton.setVisible(true);
        
        // Hide code components
        codeLabel.setVisible(false);
        codeField.setVisible(false);
        verifyCodeButton.setVisible(false);
        resendCodeButton.setVisible(false);
        
        // Hide password components
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);
        confirmPasswordLabel.setVisible(false);
        confirmPasswordField.setVisible(false);
        resetPasswordButton.setVisible(false);
        
        emailField.requestFocus();
        clearStatus();
    }
    
    private void showStep2() {
        currentStep = 2;
        progressBar.setValue(2);
        progressBar.setString("Step 2 of 3: Code Verification");
        instructionLabel.setText("Enter the 6-digit code sent to your email");
        
        // Keep email components visible but disabled
        emailField.setEnabled(false);
        sendCodeButton.setEnabled(false);
        
        // Show code components
        codeLabel.setVisible(true);
        codeField.setVisible(true);
        verifyCodeButton.setVisible(true);
        resendCodeButton.setVisible(true);
        
        // Hide password components
        passwordLabel.setVisible(false);
        passwordField.setVisible(false);
        confirmPasswordLabel.setVisible(false);
        confirmPasswordField.setVisible(false);
        resetPasswordButton.setVisible(false);
        
        codeField.requestFocus();
        clearStatus();
    }
    
    private void showStep3() {
        currentStep = 3;
        progressBar.setValue(3);
        progressBar.setString("Step 3 of 3: New Password");
        instructionLabel.setText("Create a strong new password");
        
        // Hide email and code components
        emailLabel.setVisible(false);
        emailField.setVisible(false);
        sendCodeButton.setVisible(false);
        codeLabel.setVisible(false);
        codeField.setVisible(false);
        verifyCodeButton.setVisible(false);
        resendCodeButton.setVisible(false);
        
        // Show password components
        passwordLabel.setVisible(true);
        passwordField.setVisible(true);
        confirmPasswordLabel.setVisible(true);
        confirmPasswordField.setVisible(true);
        resetPasswordButton.setVisible(true);
        
        passwordField.requestFocus();
        clearStatus();
    }
    
    private void handleSendCode() {
        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            showError("Please enter your email address");
            return;
        }
        
        if (!isValidEmail(email)) {
            showError("Please enter a valid email address");
            return;
        }
        
        currentEmail = email;
        showStatus("Sending verification code...", Color.BLUE);
        sendCodeButton.setEnabled(false);
        
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return controller.sendVerificationCode(email);
            }
            
            @Override
            protected void done() {
                sendCodeButton.setEnabled(true);
                try {
                    if (get()) {
                        showSuccess("Verification code sent to " + email);
                        showStep2();
                    } else {
                        showError("Email not found or failed to send code. Please check your email address.");
                    }
                } catch (Exception e) {
                    showError("Error sending verification code: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }
    
    private void handleVerifyCode() {
        String code = codeField.getText().trim();
        if (code.isEmpty()) {
            showError("Please enter the verification code");
            return;
        }
        
        if (code.length() != 6) {
            showError("Verification code must be 6 digits");
            return;
        }
        
        showStatus("Verifying code...", Color.BLUE);
        verifyCodeButton.setEnabled(false);
        
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return controller.verifyCode(currentEmail, code);
            }
            
            @Override
            protected void done() {
                verifyCodeButton.setEnabled(true);
                try {
                    if (get()) {
                        showSuccess("Code verified successfully");
                        showStep3();
                    } else {
                        showError("Invalid or expired verification code");
                    }
                } catch (Exception e) {
                    showError("Error verifying code: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }
    
    private void handleResetPassword() {
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            showError("Please fill in both password fields");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match");
            return;
        }
        
        showStatus("Resetting password...", Color.BLUE);
        resetPasswordButton.setEnabled(false);
        
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return controller.resetPassword(currentEmail, password);
            }
            
            @Override
            protected void done() {
                resetPasswordButton.setEnabled(true);
                try {
                    if (get()) {
                        showSuccess("Password reset successfully!");
                        Timer timer = new Timer(2000, e -> {
                            dispose();
                            new LoginView().setVisible(true);
                        });
                        timer.setRepeats(false);
                        timer.start();
                    } else {
                        showError("Failed to reset password. Please try again.");
                    }
                } catch (Exception e) {
                    showError("Error resetting password: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }
    
    private void handleResendCode() {
        showStatus("Resending verification code...", Color.BLUE);
        resendCodeButton.setEnabled(false);
        
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return controller.sendVerificationCode(currentEmail);
            }
            
            @Override
            protected void done() {
                resendCodeButton.setEnabled(true);
                try {
                    if (get()) {
                        showSuccess("Verification code resent to " + currentEmail);
                    } else {
                        showError("Failed to resend verification code");
                    }
                } catch (Exception e) {
                    showError("Error resending code: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }
    
    private void handleBackToLogin() {
        dispose();
        new LoginView().setVisible(true);
    }
    
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    public void showStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }
    
    public void showError(String message) {
        showStatus("Error: " + message, Color.RED);
    }
    
    public void showSuccess(String message) {
        showStatus("Success: " + message, Color.GREEN);
    }
    
    public void clearStatus() {
        statusLabel.setText("");
    }
    
    // Getters for controller
    public String getCurrentEmail() {
        return currentEmail;
    }
    
    public int getCurrentStep() {
        return currentStep;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ForgotPasswordView().setVisible(true));
    }
} 