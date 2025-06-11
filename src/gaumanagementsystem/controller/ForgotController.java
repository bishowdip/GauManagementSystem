/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;
import gaumanagementsystem.dao.UserDao;
import gaumanagementsystem.view.ForgotPasswordView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




/**
 *
 * @author ASUS
 */
public class ForgotController {

    private static class ForgotPasswordView {

        public ForgotPasswordView() {
        }

        private void addResetButtonListener(ActionListener actionListener) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private void dispose() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getNewPassword() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getEmail() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }



public class ForgotPasswordController {

    private ForgotPasswordView forgotView;

        /**
         *
         * @param forgotView
         */
        public ForgotPasswordController(ForgotPasswordView forgotView) {
        this.forgotView = forgotView;

        // Add listener to the Reset Password button
        this.forgotView.addResetButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePasswordReset();
            }
        });
    }

    private void handlePasswordReset() {
        String email = forgotView.getEmail();
        String newPassword = forgotView.getNewPassword();

        if (email.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return;
        }

        UserDao dao = new UserDao();
        boolean success = dao.forgotPassword(email, newPassword);

        if (success) {
            JOptionPane.showMessageDialog(null, "Password reset successfully.");
            forgotView.dispose(); // Close the forgot password window
        } else {
            JOptionPane.showMessageDialog(null, "Failed to reset password. Please check your email.");
        }
    }
    }


  



    
}
