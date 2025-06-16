package gaumanagementsystem.view;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */


import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author ASUS
 */
public class ForgotPassword extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField repeatPasswordField;
    private JButton showPasswordButton;
    private JButton showRepeatPasswordButton;
    private boolean passwordVisible = false;
    private boolean repeatPasswordVisible = false;

    public ForgotPassword() {
        setTitle("Hamro Smart Gaun - Recover Password");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(204, 204, 255));
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Hamro Smart Gaun");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBounds(15, 10, 400, 40);
        panel.add(titleLabel);

        JLabel recoverLabel = new JLabel("Recover Password");
        recoverLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        recoverLabel.setBounds(90, 50, 200, 30);
        panel.add(recoverLabel);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usernameLabel.setBounds(30, 100, 120, 25);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(200, 100, 150, 25);
        panel.add(usernameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        emailLabel.setBounds(30, 140, 120, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(200, 140, 150, 25);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setBounds(30, 180, 120, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 180, 150, 25);
        panel.add(passwordField);

        showPasswordButton = new JButton("Show");
        showPasswordButton.setBounds(370, 180, 80, 25);
        showPasswordButton.addActionListener(e -> togglePasswordVisibility(passwordField, showPasswordButton));
        panel.add(showPasswordButton);

        JLabel repeatPasswordLabel = new JLabel("Repeat New Password");
        repeatPasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        repeatPasswordLabel.setBounds(30, 220, 160, 25);
        panel.add(repeatPasswordLabel);

        repeatPasswordField = new JPasswordField();
        repeatPasswordField.setBounds(200, 220, 150, 25);
        panel.add(repeatPasswordField);

        showRepeatPasswordButton = new JButton("Show");
        showRepeatPasswordButton.setBounds(370, 220, 80, 25);
        showRepeatPasswordButton.addActionListener(e -> togglePasswordVisibility(repeatPasswordField, showRepeatPasswordButton));
        panel.add(showRepeatPasswordButton);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        confirmButton.setBackground(Color.blue);
        confirmButton.setForeground(Color.black);
        confirmButton.setBounds(170, 270, 150, 35);
        confirmButton.addActionListener(e -> handleConfirm());
        panel.add(confirmButton);

        add(panel);
    }

    private void togglePasswordVisibility(JPasswordField field, JButton button) {
        if (field.getEchoChar() != (char) 0) {
            field.setEchoChar((char) 0);
            button.setText("Hide");
        } else {
            field.setEchoChar('•');
            button.setText("Show");
        }
    }

    private void handleConfirm() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String repeatPassword = new String(repeatPasswordField.getPassword());

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username", "Error", JOptionPane.ERROR_MESSAGE);
            usernameField.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your email", "Error", JOptionPane.ERROR_MESSAGE);
            emailField.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter new password", "Error", JOptionPane.ERROR_MESSAGE);
            passwordField.requestFocus();
            return;
        }
        if (repeatPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please repeat the new password", "Error", JOptionPane.ERROR_MESSAGE);
            repeatPasswordField.requestFocus();
            return;
        }
        if (!password.equals(repeatPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
            repeatPasswordField.setText("");
            passwordField.requestFocus();
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Password reset successful for user: " + username,
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

        // Optionally clear fields
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        repeatPasswordField.setText("");
        usernameField.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ForgotPassword().setVisible(true);
        });
    }
}

