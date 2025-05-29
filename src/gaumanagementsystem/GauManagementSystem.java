/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gaumanagementsystem;

import gaumanagementsystem.view.LoginView;

/**
 *
 * @author bisho
 */
public class GauManagementSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Create and display the login form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }
    
}
