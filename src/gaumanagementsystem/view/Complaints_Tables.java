/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


package gaumanagementsystem.view;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import gaumanagementsystem.controller.ComplaintController;
import gaumanagementsystem.model.Complaint;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 *
 * @author SONIC
 */
public class Complaints_Tables extends javax.swing.JFrame {

    private static AtomicInteger idCounter = new AtomicInteger(1); // Auto-increment ID counter
    private String currentFilter = "All"; // Track current filter: "All", "Complaint", "Feedback"
    private String userRole = "admin"; // Store user role for navigation
    private String currentUserId = null; // Store current user ID for filtering
    private ComplaintController complaintController; // Database controller

    /**
     * Creates new form Complaints_Tables
     */
    public Complaints_Tables() {
        this("admin", null); // Default to admin for backward compatibility
    }

    public Complaints_Tables(String userRole) {
        this(userRole, null); // Backward compatibility
    }
    
    public Complaints_Tables(String userRole, String currentUserId) {
        this.userRole = userRole; // Store the user role
        this.currentUserId = currentUserId; // Store the user ID
        this.complaintController = new ComplaintController(); // Initialize database controller
        initComponents();
        
        // Make window fully responsive
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // Start maximized
        setMinimumSize(new java.awt.Dimension(800, 600)); // Set minimum size
        setLocationRelativeTo(null); // Center the window
        setResizable(true); // Override the setResizable(false) from initComponents
        
        // Make table responsive
        ComplaintTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        ComplaintTable1.setFillsViewportHeight(true);
        
        // Standardize button styling to match NewsAndNotice
        Color lightBlue = new Color(173, 216, 230);
        
        Back1.setBackground(lightBlue);
        Back1.setForeground(Color.BLACK);
        Back1.setText("Back");
        
        // Add action listener for Back button to ensure it works with absolute positioning
        Back1.addActionListener(e -> {
            gaumanagementsystem.view.DashboardView dashboard = new gaumanagementsystem.view.DashboardView(userRole, currentUserId);
            dashboard.setVisible(true);
            dispose();
        });
        
        // Add CRUD buttons using absolute positioning to avoid layout conflicts
        javax.swing.JButton addButton = new javax.swing.JButton("ADD");
        addButton.setBackground(lightBlue);
        addButton.setForeground(Color.BLACK);
        addButton.setBounds(150, 480, 100, 30);
        addButton.addActionListener(e -> showComplaintForm(false, -1));
        
        javax.swing.JButton deleteButton = new javax.swing.JButton("DELETE");
        deleteButton.setBackground(lightBlue);
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setBounds(270, 480, 100, 30);
        deleteButton.addActionListener(e -> {
            int selectedRow = ComplaintTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a complaint to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // For users, check if they can delete this complaint
            if (!"admin".equalsIgnoreCase(userRole)) {
                String category = (String) ComplaintTable1.getValueAt(selectedRow, 5); // Category column
                String email = (String) ComplaintTable1.getValueAt(selectedRow, 3); // Email column
                
                // Users cannot delete feedback at all
                if ("Feedback".equals(category)) {
                    JOptionPane.showMessageDialog(this, "You cannot delete feedback entries. Feedback is read-only for users.", "Access Denied", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Users can only delete their own complaints
                if (!"Complaint".equals(category) || !email.equals(currentUserId)) {
                    JOptionPane.showMessageDialog(this, "You can only delete your own complaints.", "Access Denied", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            
            // Get the complaint ID from the selected row
            int complaintId = (Integer) ComplaintTable1.getValueAt(selectedRow, 0);
            
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this complaint?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = complaintController.deleteComplaint(complaintId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Complaint deleted successfully!");
                    loadTableData(); // Refresh table from database
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete complaint from database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        javax.swing.JButton updateButton = new javax.swing.JButton("UPDATE");
        updateButton.setBackground(lightBlue);
        updateButton.setForeground(Color.BLACK);
        updateButton.setBounds(390, 480, 100, 30);
        updateButton.addActionListener(e -> {
            int selectedRow = ComplaintTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a complaint to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // For users, check if they can edit this complaint
            if (!"admin".equalsIgnoreCase(userRole)) {
                String category = (String) ComplaintTable1.getValueAt(selectedRow, 5); // Category column
                String email = (String) ComplaintTable1.getValueAt(selectedRow, 3); // Email column
                
                // Users cannot modify feedback at all
                if ("Feedback".equals(category)) {
                    JOptionPane.showMessageDialog(this, "You cannot modify feedback entries. Feedback is read-only for users.", "Access Denied", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Users can only modify their own complaints
                if (!"Complaint".equals(category) || !email.equals(currentUserId)) {
                    JOptionPane.showMessageDialog(this, "You can only update your own complaints.", "Access Denied", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            
            showComplaintForm(true, selectedRow);
        });
        
        javax.swing.JButton refreshButton = new javax.swing.JButton("REFRESH");
        refreshButton.setBackground(lightBlue);
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setBounds(510, 480, 100, 30);
        refreshButton.addActionListener(e -> {
            // Reset filter to "All"
            currentFilter = "All";
            
            // Refresh data from database
            loadTableData();
            
            JOptionPane.showMessageDialog(this, "Complaints table refreshed successfully!");
        });

        // Update button visibility for mixed access:
        // - Users can ADD complaints and UPDATE/DELETE their own complaints
        // - Users can see all feedbacks but cannot edit/delete them
        // - Admins have full access to everything
        boolean isAdmin = "admin".equalsIgnoreCase(userRole);
        addButton.setVisible(true);       // ADD - Visible for both (users can add complaints)
        deleteButton.setVisible(true);    // DELETE - Visible for both (with access control checks)
        updateButton.setVisible(true);    // UPDATE - Visible for both (with access control checks)
        
        // Create responsive layout instead of absolute positioning
        setLayout(new java.awt.BorderLayout());
        
        // Header panel
        add(jPanel1, java.awt.BorderLayout.NORTH);
        
        // Main content panel
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title and filter panel
        javax.swing.JPanel titleFilterPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        titleFilterPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Left side - title
        javax.swing.JPanel leftTitlePanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        leftTitlePanel.add(jLabel3);
        titleFilterPanel.add(leftTitlePanel, java.awt.BorderLayout.WEST);
        
        // Center - filter buttons
        javax.swing.JPanel filterPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));
        
        // Create filter buttons
        javax.swing.JButton complaintsBtn = new javax.swing.JButton("Complaints");
        javax.swing.JButton feedbackBtn = new javax.swing.JButton("Feedback");
        javax.swing.JButton allBtn = new javax.swing.JButton("All");
        
        // Style filter buttons
        Color activeColor = new Color(102, 178, 255);
        
        complaintsBtn.setBackground(lightBlue);
        complaintsBtn.setForeground(Color.BLACK);
        complaintsBtn.setFocusPainted(false);
        
        feedbackBtn.setBackground(lightBlue);
        feedbackBtn.setForeground(Color.BLACK);
        feedbackBtn.setFocusPainted(false);
        
        allBtn.setBackground(activeColor); // Start with "All" active
        allBtn.setForeground(Color.BLACK);
        allBtn.setFocusPainted(false);
        
        // Add filter functionality
        complaintsBtn.addActionListener(e -> {
            currentFilter = "Complaint";
            complaintsBtn.setBackground(activeColor);
            feedbackBtn.setBackground(lightBlue);
            allBtn.setBackground(lightBlue);
            filterTable();
        });
        
        feedbackBtn.addActionListener(e -> {
            currentFilter = "Feedback";
            feedbackBtn.setBackground(activeColor);
            complaintsBtn.setBackground(lightBlue);
            allBtn.setBackground(lightBlue);
            filterTable();
        });
        
        allBtn.addActionListener(e -> {
            currentFilter = "All";
            allBtn.setBackground(activeColor);
            complaintsBtn.setBackground(lightBlue);
            feedbackBtn.setBackground(lightBlue);
            filterTable();
        });
        
        filterPanel.add(allBtn);
        filterPanel.add(complaintsBtn);
        filterPanel.add(feedbackBtn);
        titleFilterPanel.add(filterPanel, java.awt.BorderLayout.CENTER);
        
        // Right side - search panel
        javax.swing.JPanel searchPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        
        javax.swing.JLabel searchLabel = new javax.swing.JLabel("Search:");
        searchLabel.setFont(new java.awt.Font("Arial", 1, 14));
        searchLabel.setForeground(new Color(153, 0, 255));
        
        javax.swing.JTextField searchField = new javax.swing.JTextField(15);
        searchField.setFont(new java.awt.Font("Arial", 0, 12));
        searchField.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new Color(153, 0, 255), 1),
            javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Add real-time search functionality
        setupSearchField(searchField);
        
        searchPanel.add(searchLabel);
        searchPanel.add(javax.swing.Box.createHorizontalStrut(5));
        searchPanel.add(searchField);
        titleFilterPanel.add(searchPanel, java.awt.BorderLayout.EAST);
        
        mainPanel.add(titleFilterPanel, java.awt.BorderLayout.NORTH);
        
        // Table panel (this will now resize with window)
        mainPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);
        
        add(mainPanel, java.awt.BorderLayout.CENTER);
        
        // Button panel
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(javax.swing.Box.createHorizontalStrut(20)); // Add space
        buttonPanel.add(Back1);
        
        add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        // Load data after all UI components are initialized and layout is complete
        SwingUtilities.invokeLater(() -> {
            loadTableData();
        });
    }

    /**
     * Load complaint data from database into the table
     */
    private void loadTableData() {
        try {
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) ComplaintTable1.getModel();
            model.setRowCount(0); // Clear existing data
            
            List<Complaint> allComplaints = complaintController.getAllComplaints();
            List<Complaint> filteredComplaints = new ArrayList<>();
            
            // Apply user-based filtering
            if ("user".equalsIgnoreCase(userRole)) {
                // For regular users: show their own complaints + all feedback (but not other users' complaints)
                for (Complaint complaint : allComplaints) {
                    if (currentUserId != null) {
                        // Show if it's their own complaint (by email) OR if it's feedback (category = "Feedback")
                        if (complaint.getEmail().equals(currentUserId) || "Feedback".equals(complaint.getCategory())) {
                            filteredComplaints.add(complaint);
                        }
                    }
                }
                System.out.println("Loaded " + filteredComplaints.size() + " items for user: " + currentUserId + 
                                 " (own complaints + all feedback)");
            } else {
                // For admin, show all complaints and feedback
                filteredComplaints = allComplaints;
                System.out.println("Loaded " + filteredComplaints.size() + " items for admin role");
            }
            
            for (Complaint complaint : filteredComplaints) {
                Object[] row = {
                    complaint.getId(),
                    complaint.getName(),
                    complaint.getDate(),
                    complaint.getEmail(),
                    complaint.getDescription(),
                    complaint.getCategory(),
                    complaint.getStatus()
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            System.err.println("Error loading complaint data: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error loading data from database: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadInitialData() {
        // This method is kept for backward compatibility but now calls loadTableData()
        loadTableData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ComplaintTable = new javax.swing.JTable();
        jMenu1 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ComplaintTable1 = new javax.swing.JTable();
        Back1 = new javax.swing.JButton();

        ComplaintTable.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        ComplaintTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Date", "Email", "Description", "Category", "Status"
            }
        ));
        ComplaintTable.setGridColor(new java.awt.Color(153, 51, 255));
        ComplaintTable.setShowGrid(true);
        jScrollPane1.setViewportView(ComplaintTable);

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(153, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("🏛️ Hamro Smart Gaun 🏛️");
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Ensure emoji visibility by setting a Unicode-compatible font
        try {
            java.awt.Font unicodeFont = new java.awt.Font("Segoe UI Emoji", java.awt.Font.BOLD, 32);
            String testEmoji = "🏛️";
            if (unicodeFont.canDisplayUpTo(testEmoji) == -1) {
                jLabel1.setFont(unicodeFont);
            } else {
                // Fallback to system default font
                jLabel1.setFont(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 32));
            }
        } catch (Exception e) {
            // If emoji font fails, use text alternative
            jLabel1.setText("⌂ Hamro Smart Gaun ⌂");
            jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 32));
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 0, 255));
        jLabel3.setText(" Complaints and Feedback");

        ComplaintTable1.setBackground(new java.awt.Color(204, 204, 255));
        ComplaintTable1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ComplaintTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Date", "Email", "Description", "Category", "Status"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make ID column non-editable
                return column != 0;
            }
        });
        ComplaintTable1.setGridColor(new java.awt.Color(153, 51, 255));
        ComplaintTable1.setShowGrid(true);
        jScrollPane2.setViewportView(ComplaintTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(355, 355, 355)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(Back1)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Back1)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Back1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Back1ActionPerformed
        gaumanagementsystem.view.DashboardView dashboard = new gaumanagementsystem.view.DashboardView(userRole, null);
        dashboard.setVisible(true);
        dispose();
    }//GEN-LAST:event_Back1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Complaints_Tables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Complaints_Tables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Complaints_Tables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Complaints_Tables.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Complaints_Tables().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Back1;
    private javax.swing.JTable ComplaintTable;
    private javax.swing.JTable ComplaintTable1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

    private void filterTable() {
        try {
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) ComplaintTable1.getModel();
            model.setRowCount(0); // Clear current table
            
            List<Complaint> allComplaints;
            
            // Get filtered data from database based on current filter
            if (currentFilter.equals("All")) {
                allComplaints = complaintController.getAllComplaints();
            } else if (currentFilter.equals("Complaint")) {
                allComplaints = complaintController.getComplaintsByType("Complaint");
            } else if (currentFilter.equals("Feedback")) {
                allComplaints = complaintController.getComplaintsByType("Feedback");
            } else {
                allComplaints = complaintController.getAllComplaints();
            }
            
            // Apply user-based access control filtering (same logic as loadTableData)
            List<Complaint> filteredComplaints = new ArrayList<>();
            
            if ("user".equalsIgnoreCase(userRole)) {
                // For regular users: show their own complaints + all feedback (but not other users' complaints)
                for (Complaint complaint : allComplaints) {
                    if (currentUserId != null) {
                        // Show if it's their own complaint (by email) OR if it's feedback (category = "Feedback")
                        if (complaint.getEmail().equals(currentUserId) || "Feedback".equals(complaint.getCategory())) {
                            filteredComplaints.add(complaint);
                        }
                    }
                }
            } else {
                // For admin, show all complaints and feedback
                filteredComplaints = allComplaints;
            }
            
            // Add filtered complaints to table
            for (Complaint complaint : filteredComplaints) {
                Object[] row = {
                    complaint.getId(),
                    complaint.getName(),
                    complaint.getDate(),
                    complaint.getEmail(),
                    complaint.getDescription(),
                    complaint.getCategory(),
                    complaint.getStatus()
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            System.err.println("Error filtering table: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error filtering data: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void performSearch(String searchText) {
        try {
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) ComplaintTable1.getModel();
            model.setRowCount(0); // Clear current table
            
            // If search text is empty, show filtered results based on current filter
            if (searchText == null || searchText.trim().isEmpty()) {
                filterTable();
                return;
            }
            
            // Get search results from database
            List<Complaint> searchResults = complaintController.searchComplaints(searchText.trim());
            
            // Apply user-based access control and filter search results
            for (Complaint complaint : searchResults) {
                // Check user access control first
                boolean hasAccess = false;
                if ("user".equalsIgnoreCase(userRole)) {
                    // For regular users: show their own complaints + all feedback
                    if (currentUserId != null) {
                        hasAccess = complaint.getEmail().equals(currentUserId) || "Feedback".equals(complaint.getCategory());
                    }
                } else {
                    // For admin, show all
                    hasAccess = true;
                }
                
                // Then check if it matches the current filter
                boolean matchesFilter = currentFilter.equals("All") || 
                    (currentFilter.equals("Complaint") && complaint.getCategory().equals("Complaint")) ||
                    (currentFilter.equals("Feedback") && complaint.getCategory().equals("Feedback"));
                
                if (hasAccess && matchesFilter) {
                    Object[] row = {
                        complaint.getId(),
                        complaint.getName(),
                        complaint.getDate(),
                        complaint.getEmail(),
                        complaint.getDescription(),
                        complaint.getCategory(),
                        complaint.getStatus()
                    };
                    model.addRow(row);
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error performing search: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error searching data: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    


    private void showComplaintForm(boolean isUpdate, int selectedRow) {
        // Create form fields with proper sizing
        javax.swing.JTextField nameField = new javax.swing.JTextField(20);
        nameField.setPreferredSize(new java.awt.Dimension(250, 25));
        nameField.setMinimumSize(new java.awt.Dimension(200, 25));
        
        javax.swing.JTextField emailField = new javax.swing.JTextField(20);
        emailField.setPreferredSize(new java.awt.Dimension(250, 25));
        emailField.setMinimumSize(new java.awt.Dimension(200, 25));
        
        // Phone field removed as requested
        
        javax.swing.JSpinner wardSpinner = new javax.swing.JSpinner(new javax.swing.SpinnerNumberModel(1, 1, 10, 1));
        wardSpinner.setPreferredSize(new java.awt.Dimension(250, 25));
        
        javax.swing.JTextArea descriptionArea = new javax.swing.JTextArea(4, 25);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setPreferredSize(new java.awt.Dimension(250, 80));
        javax.swing.JScrollPane descScrollPane = new javax.swing.JScrollPane(descriptionArea);
        descScrollPane.setPreferredSize(new java.awt.Dimension(250, 80));
        
        // Category dropdown - only two options: Complaints and Feedbacks
        String[] categories = {"Complaint", "Feedback"};
        javax.swing.JComboBox<String> categoryCombo = new javax.swing.JComboBox<>(categories);
        categoryCombo.setPreferredSize(new java.awt.Dimension(250, 25));
        
        // Status dropdown - restrict for users
        String[] statuses;
        if ("admin".equalsIgnoreCase(userRole)) {
            statuses = new String[]{"Pending", "In Progress", "Resolved", "Closed"};
        } else {
            statuses = new String[]{"Pending"}; // Users can only set status to Pending
        }
        javax.swing.JComboBox<String> statusCombo = new javax.swing.JComboBox<>(statuses);
        statusCombo.setPreferredSize(new java.awt.Dimension(250, 25));
        
        // Create date field with calendar picker
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        javax.swing.JTextField dateField = new javax.swing.JTextField(sdf.format(new java.util.Date()));
        dateField.setPreferredSize(new java.awt.Dimension(180, 25));
        dateField.setMinimumSize(new java.awt.Dimension(150, 25));
        dateField.setEditable(false); // Make it read-only so users must use calendar
        
        javax.swing.JButton calendarButton = new javax.swing.JButton("📅");
        calendarButton.setPreferredSize(new java.awt.Dimension(40, 25));
        calendarButton.setToolTipText("Select Date");
        
        // Create date panel with text field and calendar button
        javax.swing.JPanel datePanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        datePanel.add(dateField);
        datePanel.add(calendarButton);
        datePanel.setPreferredSize(new java.awt.Dimension(250, 25));
        
        // Add calendar button action
        calendarButton.addActionListener(e -> {
            Date selectedDate = showCalendarDialog(dateField.getText());
            if (selectedDate != null) {
                dateField.setText(sdf.format(selectedDate));
            }
        });
        
        // If updating, populate fields with existing data
        if (isUpdate && selectedRow >= 0) {
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) ComplaintTable1.getModel();
            nameField.setText((String) model.getValueAt(selectedRow, 1)); // Name
            
            // Handle date field - could be String or Date object
            Object dateValue = model.getValueAt(selectedRow, 2);
            if (dateValue instanceof java.sql.Date) {
                dateField.setText(dateValue.toString()); // java.sql.Date.toString() returns yyyy-MM-dd format
            } else if (dateValue instanceof String) {
                dateField.setText((String) dateValue);
            } else {
                dateField.setText(sdf.format(new java.util.Date())); // Default to today
            }
            
            emailField.setText((String) model.getValueAt(selectedRow, 3)); // Email
            descriptionArea.setText((String) model.getValueAt(selectedRow, 4)); // Description
            categoryCombo.setSelectedItem((String) model.getValueAt(selectedRow, 5)); // Category
            statusCombo.setSelectedItem((String) model.getValueAt(selectedRow, 6)); // Status
            
            // Set default values for ward if not available in table
            wardSpinner.setValue(1); // Default ward 1
        }
        
        // Create form panel with better layout
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridBagLayout());
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(8, 8, 8, 8);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        
        // Add form fields with proper constraints
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        panel.add(new javax.swing.JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(new javax.swing.JLabel("Date:"), gbc);
        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(datePanel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(new javax.swing.JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(emailField, gbc);
        
        // Phone field removed from layout
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(new javax.swing.JLabel("Ward:"), gbc);
        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(wardSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(new javax.swing.JLabel("Category:"), gbc);
        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(categoryCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(new javax.swing.JLabel("Status:"), gbc);
        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(statusCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.anchor = java.awt.GridBagConstraints.NORTHWEST;
        panel.add(new javax.swing.JLabel("Description:"), gbc);
        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(descScrollPane, gbc);
        
        // Show dialog with larger size
        String title = isUpdate ? "Update Complaint" : "Add New Complaint";
        
        // Create a custom dialog for better control
        javax.swing.JDialog dialog = new javax.swing.JDialog(this, title, true);
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new java.awt.BorderLayout());
        dialog.add(panel, java.awt.BorderLayout.CENTER);
        
        // Create button panel
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        javax.swing.JButton okButton = new javax.swing.JButton("OK");
        javax.swing.JButton cancelButton = new javax.swing.JButton("Cancel");
        
        // Add button actions
        okButton.addActionListener(e -> {
            // Perform validation BEFORE closing dialog
            String name = nameField.getText().trim();
            String date = dateField.getText().trim();
            String email = emailField.getText().trim();
            String phone = ""; // Phone field removed, set to empty string
            int ward = (Integer) wardSpinner.getValue();
            String description = descriptionArea.getText().trim();
            String category = (String) categoryCombo.getSelectedItem();
            String status = (String) statusCombo.getSelectedItem();
            
            System.out.println("Form validation - Name: '" + name + "', Email: '" + email + "', Description: '" + description + "'");
            System.out.println("Description field text area content: '" + descriptionArea.getText() + "'");
            System.out.println("Description length: " + description.length());
            
            // Validation
            if (name.isEmpty() || email.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Name, Email, and Description are required fields!");
                return; // Don't close dialog, let user fix the issue
            }
            
            // Email validation
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid email address!");
                return; // Don't close dialog, let user fix the issue
            }
            
            // If validation passes, process the form
            try {
                // Convert date string to SQL Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(date);
                
                if (isUpdate) {
                    // Get the complaint ID from the selected row
                    javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) ComplaintTable1.getModel();
                    int complaintId = (Integer) model.getValueAt(selectedRow, 0);
                    
                    // Update complaint in database
                    boolean success = complaintController.updateComplaint(complaintId, name, ward, phone, email, 
                                                                        category, description, status, sqlDate, "");
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "Complaint updated successfully!");
                        loadTableData(); // Refresh table from database
                        dialog.dispose(); // Close dialog only on success
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Failed to update complaint in database.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // Add new complaint to database
                    boolean success = complaintController.createComplaint(name, ward, phone, email, 
                                                                        category, description, sqlDate);
                    if (success) {
                        JOptionPane.showMessageDialog(dialog, "Complaint added successfully!");
                        loadTableData(); // Refresh table from database
                        dialog.dispose(); // Close dialog only on success
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Failed to add complaint to database.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                System.err.println("Error processing complaint form: " + ex.getMessage());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Error processing complaint: " + ex.getMessage(), 
                                            "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> {
            dialog.dispose();
        });
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        // Set dialog size and position - make it larger to show all fields
        dialog.setSize(450, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private Date showCalendarDialog(String currentDate) {
        // Parse current date
        Date initialDate = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (currentDate != null && !currentDate.trim().isEmpty()) {
                initialDate = sdf.parse(currentDate);
            }
        } catch (Exception e) {
            // Use current date if parsing fails
            initialDate = new Date();
        }
        
        // Create calendar dialog
        javax.swing.JDialog calendarDialog = new javax.swing.JDialog(this, "Select Date", true);
        calendarDialog.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
        calendarDialog.setLayout(new java.awt.BorderLayout());
        
        // Create calendar panel
        Calendar cal = Calendar.getInstance();
        cal.setTime(initialDate);
        
        // Year spinner
        SpinnerModel yearModel = new javax.swing.SpinnerNumberModel(cal.get(Calendar.YEAR), 1900, 2100, 1);
        javax.swing.JSpinner yearSpinner = new javax.swing.JSpinner(yearModel);
        
        // Month combo box
        String[] months = {"January", "February", "March", "April", "May", "June",
                          "July", "August", "September", "October", "November", "December"};
        javax.swing.JComboBox<String> monthCombo = new javax.swing.JComboBox<>(months);
        monthCombo.setSelectedIndex(cal.get(Calendar.MONTH));
        
        // Day spinner
        SpinnerModel dayModel = new javax.swing.SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 31, 1);
        javax.swing.JSpinner daySpinner = new javax.swing.JSpinner(dayModel);
        
        // Update day spinner when month/year changes
        Runnable updateDaySpinner = () -> {
            int year = (Integer) yearSpinner.getValue();
            int month = monthCombo.getSelectedIndex();
            Calendar tempCal = Calendar.getInstance();
            tempCal.set(year, month, 1);
            int maxDay = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            int currentDay = (Integer) daySpinner.getValue();
            if (currentDay > maxDay) {
                daySpinner.setValue(maxDay);
            }
            ((javax.swing.SpinnerNumberModel) daySpinner.getModel()).setMaximum(maxDay);
        };
        
        yearSpinner.addChangeListener(e -> updateDaySpinner.run());
        monthCombo.addActionListener(e -> updateDaySpinner.run());
        
        // Create top panel for date selection
        javax.swing.JPanel datePanel = new javax.swing.JPanel(new java.awt.GridBagLayout());
        datePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Select Date"));
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        datePanel.add(new javax.swing.JLabel("Year:"), gbc);
        gbc.gridx = 1;
        datePanel.add(yearSpinner, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        datePanel.add(new javax.swing.JLabel("Month:"), gbc);
        gbc.gridx = 1;
        datePanel.add(monthCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        datePanel.add(new javax.swing.JLabel("Day:"), gbc);
        gbc.gridx = 1;
        datePanel.add(daySpinner, gbc);
        
        // Create preview label
        javax.swing.JLabel previewLabel = new javax.swing.JLabel();
        previewLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        previewLabel.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Date"));
        
        // Update preview
        Runnable updatePreview = () -> {
            try {
                int year = (Integer) yearSpinner.getValue();
                int month = monthCombo.getSelectedIndex();
                int day = (Integer) daySpinner.getValue();
                Calendar previewCal = Calendar.getInstance();
                previewCal.set(year, month, day);
                SimpleDateFormat displayFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
                previewLabel.setText(displayFormat.format(previewCal.getTime()));
            } catch (Exception e) {
                previewLabel.setText("Invalid Date");
            }
        };
        
        yearSpinner.addChangeListener(e -> updatePreview.run());
        monthCombo.addActionListener(e -> updatePreview.run());
        daySpinner.addChangeListener(e -> updatePreview.run());
        updatePreview.run(); // Initial update
        
        // Create button panel
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        javax.swing.JButton okButton = new javax.swing.JButton("OK");
        javax.swing.JButton cancelButton = new javax.swing.JButton("Cancel");
        javax.swing.JButton todayButton = new javax.swing.JButton("Today");
        
        final Date[] selectedDate = {null};
        
        okButton.addActionListener(e -> {
            try {
                int year = (Integer) yearSpinner.getValue();
                int month = monthCombo.getSelectedIndex();
                int day = (Integer) daySpinner.getValue();
                Calendar resultCal = Calendar.getInstance();
                resultCal.set(year, month, day);
                selectedDate[0] = resultCal.getTime();
                calendarDialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(calendarDialog, "Please select a valid date!");
            }
        });
        
        cancelButton.addActionListener(e -> {
            selectedDate[0] = null;
            calendarDialog.dispose();
        });
        
        todayButton.addActionListener(e -> {
            Calendar today = Calendar.getInstance();
            yearSpinner.setValue(today.get(Calendar.YEAR));
            monthCombo.setSelectedIndex(today.get(Calendar.MONTH));
            daySpinner.setValue(today.get(Calendar.DAY_OF_MONTH));
        });
        
        buttonPanel.add(todayButton);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        // Assemble dialog
        calendarDialog.add(datePanel, java.awt.BorderLayout.NORTH);
        calendarDialog.add(previewLabel, java.awt.BorderLayout.CENTER);
        calendarDialog.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        // Set dialog properties
        calendarDialog.setSize(300, 250);
        calendarDialog.setLocationRelativeTo(this);
        calendarDialog.setVisible(true);
        
        return selectedDate[0];
    }

    private void setupSearchField(javax.swing.JTextField searchField) {
        // Add real-time search functionality
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                performSearch(searchField.getText());
            }
        });
    }
}
