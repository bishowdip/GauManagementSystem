package gaumanagementsystem.view;

import gaumanagementsystem.database.MySqlConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginView extends javax.swing.JFrame {

    private MySqlConnection dbConnection;

    // Components
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;

    public LoginView() {
        initComponents();
        dbConnection = new MySqlConnection();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Initialize all components
        jLabel1 = new JLabel("Enter your credentials to login");
        jLabel2 = new JLabel("Email");
        jLabel3 = new JLabel("Password");
        jLabel4 = new JLabel("Forgot Password?");
        jLabel5 = new JLabel("Do not have an account?");
        jLabel6 = new JLabel("Register");
        jTextField1 = new JTextField();
        jPasswordField1 = new JPasswordField();
        jButton1 = new JButton("Login");
        jButton2 = new JButton("Show");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Set bounds
        jLabel1.setBounds(100, 30, 300, 25);
        jLabel2.setBounds(50, 80, 100, 25);
        jTextField1.setBounds(150, 80, 150, 25);
        jLabel3.setBounds(50, 120, 100, 25);
        jPasswordField1.setBounds(150, 120, 150, 25);
        jButton2.setBounds(310, 120, 70, 25);
        jLabel4.setBounds(150, 150, 200, 25);
        jButton1.setBounds(150, 180, 150, 30);
        jLabel5.setBounds(100, 220, 150, 25);
        jLabel6.setBounds(250, 220, 100, 25);

        // Styling register label
        jLabel6.setForeground(new java.awt.Color(51, 153, 255));
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Action Listeners
        jButton1.addActionListener(this::loginAction);
        jButton2.addActionListener(this::togglePasswordVisibility);
        jLabel6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterView registerView = new RegisterView();
                registerView.setVisible(true);
                dispose(); // close login window
            }
        });

        // Add components
        add(jLabel1); add(jLabel2); add(jTextField1); add(jLabel3);
        add(jPasswordField1); add(jButton2); add(jLabel4); add(jButton1);
        add(jLabel5); add(jLabel6);

        setSize(450, 320);
    }

    private void loginAction(ActionEvent evt) {
        String email = jTextField1.getText().trim();
        String password = new String(jPasswordField1.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both email and password", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = dbConnection.openConnection();
            String query = "SELECT * FROM users WHERE email = ? AND fpassword = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Navigate to main window
                new DashboardUser().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }

            rs.close();
            pst.close();
            dbConnection.closeConnection(conn);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void togglePasswordVisibility(ActionEvent evt) {
        if (jPasswordField1.getEchoChar() == 0) {
            jPasswordField1.setEchoChar('•');
            jButton2.setText("Show");
        } else {
            jPasswordField1.setEchoChar((char) 0);
            jButton2.setText("Hide");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
