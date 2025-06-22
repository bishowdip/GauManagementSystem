package gaumanagementsystem.view;

import gaumanagementsystem.controller.UserController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends javax.swing.JFrame {
    private javax.swing.JButton loginButton;
    private javax.swing.JButton showButton;
    private javax.swing.JLabel welcomeLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel forgotLabel;
    private javax.swing.JLabel noAccountLabel;
    private javax.swing.JLabel registerLabel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField emailField;

 /**
 *
 * @author bisho
 */
    
    public LoginView() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        welcomeLabel = new JLabel("Welcome to Gau Management System");
        titleLabel = new JLabel("Enter your credentials to login");
        emailLabel = new JLabel("Email");
        passwordLabel = new JLabel("Password");
        forgotLabel = new JLabel("Forgot Password?");
        noAccountLabel = new JLabel("Do not have an account?");
        registerLabel = new JLabel("Register");
        emailField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        showButton = new JButton("Show");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Set bounds with increased spacing to accommodate welcome text
        welcomeLabel.setBounds(80, 20, 350, 35);
        titleLabel.setBounds(100, 70, 300, 25);
        emailLabel.setBounds(50, 120, 100, 25);
        emailField.setBounds(150, 120, 150, 25);
        passwordLabel.setBounds(50, 160, 100, 25);
        passwordField.setBounds(150, 160, 150, 25);
        showButton.setBounds(310, 160, 70, 25);
        forgotLabel.setBounds(150, 190, 200, 25);
        loginButton.setBounds(150, 220, 150, 30);
        noAccountLabel.setBounds(100, 260, 150, 25);
        registerLabel.setBounds(250, 260, 100, 25);

        // Styling welcome label
        welcomeLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        welcomeLabel.setForeground(new java.awt.Color(0, 102, 51));
        welcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Styling register label
        registerLabel.setForeground(new java.awt.Color(51, 153, 255));
        registerLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Styling forgot password label
        forgotLabel.setForeground(new java.awt.Color(51, 153, 255));
        forgotLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Action Listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                loginAction(evt);
            }
        });
        
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                togglePasswordVisibility(evt);
            }
        });
        
        registerLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RegisterView().setVisible(true);
                dispose();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {}
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            
            @Override
            public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        forgotLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ForgotPasswordView().setVisible(true);
                dispose();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {}
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            
            @Override
            public void mouseEntered(MouseEvent e) {}
            
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        

        // Add components
        add(welcomeLabel); add(titleLabel); add(emailLabel); add(emailField); add(passwordLabel);
        add(passwordField); add(showButton); add(forgotLabel); add(loginButton);
        add(noAccountLabel); add(registerLabel);

        setSize(450, 360);
    }

    private void loginAction(ActionEvent evt) {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        new UserController().handleLogin(this, email, password);
    }

    private void togglePasswordVisibility(ActionEvent evt) {
        if (passwordField.getEchoChar() == 0) {
            passwordField.setEchoChar('•');
            showButton.setText("Show");
        } else {
            passwordField.setEchoChar((char) 0);
            showButton.setText("Hide");
        }
    }

    public javax.swing.JTextField getEmailTextField() {
        return emailField;
    }
    public javax.swing.JPasswordField getPasswordField() {
        return passwordField;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
