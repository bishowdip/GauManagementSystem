/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


package gaumanagementsystem.view;

import java.awt.Color;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;

/**
 *
 * @author SONIC
 */
public class Complaints_Tables extends javax.swing.JFrame {

    private static AtomicInteger idCounter = new AtomicInteger(1); // Auto-increment ID counter
    private String currentFilter = "All"; // Track current filter: "All", "Complaint", "Feedback"
    private String userRole = "admin"; // Store user role for navigation
    private String currentUserId = null; // Store current user ID for filtering

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
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this complaint?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ((javax.swing.table.DefaultTableModel) ComplaintTable1.getModel()).removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Complaint deleted successfully!");
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
                if (!"Complaint".equals(category)) {
                    JOptionPane.showMessageDialog(this, "You can only update your own complaints, not feedbacks.", "Access Denied", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Additional check: verify this complaint belongs to the current user (implement later with database)
            }
            
            showComplaintForm(true, selectedRow);
        });
        
        javax.swing.JButton refreshButton = new javax.swing.JButton("REFRESH");
        refreshButton.setBackground(lightBlue);
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setBounds(510, 480, 100, 30);
        refreshButton.addActionListener(e -> {
            // Implement refresh functionality - clear table
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) ComplaintTable1.getModel();
            model.setRowCount(0); // Clear all rows
            
            // No sample data - data should be loaded from database via DAO
            // Reset counter
            idCounter.set(1);
            
            // Reset filter to "All"
            currentFilter = "All";
            
            JOptionPane.showMessageDialog(this, "Table refreshed!");
        });

        // Update button visibility for mixed access:
        // - Users can ADD complaints and UPDATE their own complaints
        // - Users can see all feedbacks but cannot edit them
        // - Only admins can DELETE anything
        boolean isAdmin = "admin".equalsIgnoreCase(userRole);
        addButton.setVisible(true);       // ADD - Visible for both (users can add complaints)
        deleteButton.setVisible(isAdmin); // DELETE - Admin only
        updateButton.setVisible(true);    // UPDATE - Visible for both (with restrictions for users)
        
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
        
        // Add initial sample data
        loadInitialData();
    }

    private void loadInitialData() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) ComplaintTable1.getModel();
        // No initial data - data should be loaded from database via DAO
        // Table will be empty until real data is added
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
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) ComplaintTable1.getModel();
        javax.swing.table.DefaultTableModel originalModel = new javax.swing.table.DefaultTableModel(
            new String[]{"ID", "Name", "Date", "Email", "Description", "Category", "Status"}, 0
        );
        
        // Get all data (you would normally get this from a database)
        Object[][] allData = getAllTableData();
        
        // Clear current table
        model.setRowCount(0);
        
        // Filter and add rows based on current filter
        for (Object[] row : allData) {
            String category = (String) row[5]; // Category is at index 5
            
            if (currentFilter.equals("All") || 
                (currentFilter.equals("Complaint") && category.equals("Complaint")) ||
                (currentFilter.equals("Feedback") && category.equals("Suggestion"))) { // Map "Feedback" to "Suggestion" category
                model.addRow(row);
            }
        }
    }
    
    private void performSearch(String searchText) {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) ComplaintTable1.getModel();
        
        // Get all data
        Object[][] allData = getAllTableData();
        
        // Clear current table
        model.setRowCount(0);
        
        // If search text is empty, show filtered results based on current filter
        if (searchText == null || searchText.trim().isEmpty()) {
            filterTable();
            return;
        }
        
        // Convert search text to lowercase for case-insensitive search
        String searchLower = searchText.toLowerCase().trim();
        
        // Search through all columns and filter by current category filter
        for (Object[] row : allData) {
            String category = (String) row[5]; // Category is at index 5
            
            // First check if row matches current filter
            boolean matchesFilter = currentFilter.equals("All") || 
                (currentFilter.equals("Complaint") && category.equals("Complaint")) ||
                (currentFilter.equals("Feedback") && category.equals("Suggestion"));
            
            if (!matchesFilter) {
                continue; // Skip if doesn't match current filter
            }
            
            // Check if any field contains the search text
            boolean matchesSearch = false;
            for (int i = 0; i < row.length; i++) {
                if (row[i] != null && row[i].toString().toLowerCase().contains(searchLower)) {
                    matchesSearch = true;
                    break;
                }
            }
            
            if (matchesSearch) {
                model.addRow(row);
            }
        }
    }
    
    private Object[][] getAllTableData() {
        // This method should get data from database only
        // Return empty array - data will be loaded from database via DAO
        return new Object[][] {};
    }

    private void showComplaintForm(boolean isUpdate, int selectedRow) {
        // Create form fields with proper sizing
        javax.swing.JTextField nameField = new javax.swing.JTextField(20);
        nameField.setPreferredSize(new java.awt.Dimension(250, 25));
        nameField.setMinimumSize(new java.awt.Dimension(200, 25));
        
        javax.swing.JTextField emailField = new javax.swing.JTextField(20);
        emailField.setPreferredSize(new java.awt.Dimension(250, 25));
        emailField.setMinimumSize(new java.awt.Dimension(200, 25));
        
        javax.swing.JTextArea descriptionArea = new javax.swing.JTextArea(4, 25);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setPreferredSize(new java.awt.Dimension(250, 80));
        javax.swing.JScrollPane descScrollPane = new javax.swing.JScrollPane(descriptionArea);
        descScrollPane.setPreferredSize(new java.awt.Dimension(250, 80));
        
        // Category dropdown - restrict for users
        String[] categories;
        if ("admin".equalsIgnoreCase(userRole)) {
            categories = new String[]{"Complaint", "Suggestion", "General", "Infrastructure", "Service", "Other"};
        } else {
            categories = new String[]{"Complaint"}; // Users can only add complaints
        }
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
            dateField.setText((String) model.getValueAt(selectedRow, 2)); // Date
            emailField.setText((String) model.getValueAt(selectedRow, 3)); // Email
            descriptionArea.setText((String) model.getValueAt(selectedRow, 4)); // Description
            categoryCombo.setSelectedItem((String) model.getValueAt(selectedRow, 5)); // Category
            statusCombo.setSelectedItem((String) model.getValueAt(selectedRow, 6)); // Status
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
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(new javax.swing.JLabel("Category:"), gbc);
        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(categoryCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = java.awt.GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        panel.add(new javax.swing.JLabel("Status:"), gbc);
        gbc.gridx = 1;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(statusCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
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
        final boolean[] dialogResult = {false};
        okButton.addActionListener(e -> {
            dialogResult[0] = true;
            dialog.dispose();
        });
        cancelButton.addActionListener(e -> {
            dialogResult[0] = false;
            dialog.dispose();
        });
        
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        // Set dialog size and position
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
        // Process result
        if (dialogResult[0]) {
            String name = nameField.getText().trim();
            String date = dateField.getText().trim();
            String email = emailField.getText().trim();
            String description = descriptionArea.getText().trim();
            String category = (String) categoryCombo.getSelectedItem();
            String status = (String) statusCombo.getSelectedItem();
            
            // Validation
            if (name.isEmpty() || email.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name, Email, and Description are required fields!");
                return;
            }
            
            // Email validation
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address!");
                return;
            }
            
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) ComplaintTable1.getModel();
            
            if (isUpdate) {
                // Update existing row (keep the same ID)
                model.setValueAt(name, selectedRow, 1);
                model.setValueAt(date, selectedRow, 2);
                model.setValueAt(email, selectedRow, 3);
                model.setValueAt(description, selectedRow, 4);
                model.setValueAt(category, selectedRow, 5);
                model.setValueAt(status, selectedRow, 6);
                JOptionPane.showMessageDialog(this, "Complaint updated successfully!");
            } else {
                // Add new row with auto-generated ID
                int newId = idCounter.getAndIncrement();
                model.addRow(new Object[]{newId, name, date, email, description, category, status});
                JOptionPane.showMessageDialog(this, "Complaint added successfully!");
            }
        }
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
