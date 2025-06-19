package gaumanagementsystem.view;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class DashboardView extends javax.swing.JFrame {
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton serviceButton;
    private javax.swing.JButton budgetButton;
    private javax.swing.JButton complaintsButton;
    private javax.swing.JButton projectsButton;
    private javax.swing.JButton newsButton;
    private javax.swing.JButton citizensButton;
    private javax.swing.JButton logoutButton;
    private String userRole = "admin";
    
    public DashboardView() {
        this("admin");
    }
    
    public DashboardView(String userRole) {
        this.userRole = userRole;
        initComponents();
        setLocationRelativeTo(null);
        new gaumanagementsystem.controller.DashboardController(this, userRole);
    }
    
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        serviceButton = new javax.swing.JButton();
        budgetButton = new javax.swing.JButton();
        complaintsButton = new javax.swing.JButton();
        projectsButton = new javax.swing.JButton();
        newsButton = new javax.swing.JButton();
        citizensButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hamro Smart Gaun - Dashboard");
        
        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        
        titleLabel.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24));
        titleLabel.setText("Hamro Smart Gaun");
        
        menuPanel.setLayout(new GridLayout(0, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        serviceButton.setText("Services");
        
        budgetButton.setText("Budget Allocations");
        
        complaintsButton.setText("Complaints & Feedbacks");
        
        projectsButton.setText("Projects");
        
        newsButton.setText("News & Notices");
        
        citizensButton.setText("Citizens");
        
        
        logoutButton.setText("Logout");
        
        menuPanel.add(serviceButton);
        menuPanel.add(budgetButton);
        menuPanel.add(complaintsButton);
        menuPanel.add(projectsButton);
        menuPanel.add(newsButton);
        menuPanel.add(citizensButton);
        menuPanel.add(logoutButton);
        
        // Layout
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(297, 297, 297)
                .addComponent(titleLabel)
                .addContainerGap(297, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(titleLabel)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(menuPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(menuPanel, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        
        pack();
    }
    
    public void addServiceButtonListener(ActionListener listener) {
        serviceButton.addActionListener(listener);
    }

    public void addBudgetButtonListener(ActionListener listener) {
        budgetButton.addActionListener(listener);
    }

    public void addComplaintsButtonListener(ActionListener listener) {
        complaintsButton.addActionListener(listener);
    }

    public void addProjectsButtonListener(ActionListener listener) {
        projectsButton.addActionListener(listener);
    }

    public void addNewsButtonListener(ActionListener listener) {
        newsButton.addActionListener(listener);
    }

    public void addCitizensButtonListener(ActionListener listener) {
        citizensButton.addActionListener(listener);
    }


    public void addLogoutButtonListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }

    public void setUserGreeting(String greeting) {
        titleLabel.setText(greeting);
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new DashboardView("admin").setVisible(true);
        });

}

    public JButton getServiceButton() { 
        return serviceButton; }
    public JButton getBudgetButton() { 
        return budgetButton; }
    public JButton getComplaintsButton() { 
        return complaintsButton; }
    public JButton getProjectsButton() { 
        return projectsButton; }
    public JButton getNewsButton() { 
        return newsButton; }
    public JButton getCitizensButton() { 
        return citizensButton; }
    public JButton getLogoutButton() { 
        return logoutButton; }

    public String getUserRole() {
        return userRole;
    }
    }
