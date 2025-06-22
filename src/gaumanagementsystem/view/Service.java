/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gaumanagementsystem.view;

import java.awt.Color;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import gaumanagementsystem.database.MySqlConnection;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Bishowdip
 */
public class Service extends javax.swing.JFrame {

    private String userRole = "admin"; // Store user role for navigation
    private String currentUserId = null; // Store current user ID for filtering

    /**
     * Creates new form ProjectRequest
     */
    public Service() {
        this("admin", null); // Default to admin for backward compatibility
    }

    public Service(String userRole) {
        this(userRole, null); // Backward compatibility
    }
    
    public Service(String userRole, String currentUserId) {
        this.userRole = userRole; // Store the user role
        this.currentUserId = currentUserId; // Store the user ID
        initComponents();
        
        // Make window fully responsive
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // Start maximized
        setMinimumSize(new java.awt.Dimension(800, 600)); // Set minimum size
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        // Make table responsive
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setFillsViewportHeight(true);

        // Standardize button styling to match NewsAndNotice
        Color lightBlue = new Color(173, 216, 230);
        
        jButton5.setBackground(lightBlue);
        jButton5.setForeground(Color.BLACK);
        jButton5.setText("ADD");
        
        jButton2.setBackground(lightBlue);
        jButton2.setForeground(Color.BLACK);
        jButton2.setText("DELETE");
        
        jButton3.setBackground(lightBlue);
        jButton3.setForeground(Color.BLACK);
        jButton3.setText("UPDATE");
        
        jButton4.setBackground(lightBlue);
        jButton4.setForeground(Color.BLACK);
        jButton4.setText("REFRESH");
        
        // Setup back button styling
        backButton.setBackground(lightBlue);
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> {
            new DashboardView(userRole, currentUserId).setVisible(true);
            this.dispose();
        });

        // Update button visibility: Users can ADD and UPDATE their own services, only admins can DELETE
        boolean isAdmin = "admin".equalsIgnoreCase(userRole);
        jButton5.setVisible(true);        // ADD - Visible for both admin and user
        jButton2.setVisible(isAdmin);     // DELETE - Admin only
        jButton3.setVisible(true);        // UPDATE - Visible for both admin and user
        
        // Set up a simple layout for panel2 since the original was removed
        setupPanel2Layout();
        
        // Ensure emoji is visible in header
        setupEmojiFont();
        
        // Load initial data based on user role
        refreshTableData();
        
        // Add search functionality to match News and Notice
        addSearchFunctionality();

        // Remove duplicate event handlers - they are already handled in the generated code section
        // The generated code section handles all button actions properly

        // jLabel2 and jLabel3 are no longer used since we removed Services/Requests labels
    }

    /**
     * Show dialog to add new service
     */
    private void showAddServiceDialog() {
        // Create form fields with predefined service names
        String[] serviceNames = {
            "Birth Registration & Certificate Issuance",
            "Death Registration & Certificate Issuance", 
            "Marriage Registration & Certificate Issuance",
            "Social Security Allowance Processing",
            "Residential Building Permit Issuance",
            "Property (Land/House) Tax Assessment & Payment",
            "Household Waste Collection Service",
            "Drinking Water Connection & Billing",
            "Basic Health Services",
            "Local Recommendation for Citizenship Certificate"
        };
        JComboBox<String> serviceNameCombo = new JComboBox<>(serviceNames);
        JTextField citizenNameField = new JTextField(20);
        JTextField wardField = new JTextField(10);
        JTextArea descriptionArea = new JTextArea(4, 20);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{
            "Pending", "In Progress", "Completed", "Rejected"
        });
        statusCombo.setSelectedIndex(0); // Default to "Pending"

        // Create panel with form layout
        JPanel panel = new JPanel(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        gbc.anchor = java.awt.GridBagConstraints.WEST;

        // Add form fields
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Service Name:"), gbc);
        gbc.gridx = 1;
        panel.add(serviceNameCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Citizen Name:"), gbc);
        gbc.gridx = 1;
        panel.add(citizenNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Ward:"), gbc);
        gbc.gridx = 1;
        panel.add(wardField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        panel.add(descScrollPane, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        panel.add(statusCombo, gbc);

        // Show dialog
        int result = JOptionPane.showConfirmDialog(
            this, 
            panel, 
            "Add New Service", 
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            // Validate input
            String serviceName = (String) serviceNameCombo.getSelectedItem();
            String citizenName = citizenNameField.getText().trim();
            String ward = wardField.getText().trim();
            String description = descriptionArea.getText().trim();
            String status = (String) statusCombo.getSelectedItem();

            if (serviceName == null || serviceName.isEmpty() || citizenName.isEmpty() || ward.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please fill in all required fields (Service Name, Citizen Name, Ward).", 
                    "Validation Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Save to database and update table
            if (saveServiceToDatabase(serviceName, citizenName, ward, description, status)) {
                JOptionPane.showMessageDialog(this, 
                    "Service added successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                refreshTableData(); // Refresh the table to show new data
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to add service. Please try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Show dialog to update existing service
     */
    private void showUpdateServiceDialog(int selectedRow) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        // Get current values from selected row - handle type conversions properly
        // Table columns: "Service_id", "ServiceName", "SubmittedAT", "NameOFCitizen", "Ward", "Description", "Status"
        String currentServiceName = (String) model.getValueAt(selectedRow, 1); // ServiceName column
        String currentCitizenName = (String) model.getValueAt(selectedRow, 3); // NameOFCitizen column
        // Ward might be Integer or String, convert safely
        Object wardObj = model.getValueAt(selectedRow, 4); // Ward column
        String currentWard = wardObj != null ? wardObj.toString() : "";
        String currentDescription = (String) model.getValueAt(selectedRow, 5); // Description column
        String currentStatus = (String) model.getValueAt(selectedRow, 6); // Status column
        
        // Create form fields with predefined service names
        String[] serviceNames = {
            "Birth Registration & Certificate Issuance",
            "Death Registration & Certificate Issuance", 
            "Marriage Registration & Certificate Issuance",
            "Social Security Allowance Processing",
            "Residential Building Permit Issuance",
            "Property (Land/House) Tax Assessment & Payment",
            "Household Waste Collection Service",
            "Drinking Water Connection & Billing",
            "Basic Health Services",
            "Local Recommendation for Citizenship Certificate"
        };
        JComboBox<String> serviceNameCombo = new JComboBox<>(serviceNames);
        serviceNameCombo.setSelectedItem(currentServiceName); // Set current value
        
        JTextField citizenNameField = new JTextField(currentCitizenName, 20);
        JTextField wardField = new JTextField(currentWard, 10);
        JTextArea descriptionArea = new JTextArea(currentDescription, 4, 20);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{
            "Pending", "In Progress", "Completed", "Rejected"
        });
        statusCombo.setSelectedItem(currentStatus); // Set current value

        // Create panel with form layout
        JPanel panel = new JPanel(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        gbc.anchor = java.awt.GridBagConstraints.WEST;

        // Add form fields
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Service Name:"), gbc);
        gbc.gridx = 1;
        panel.add(serviceNameCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Citizen Name:"), gbc);
        gbc.gridx = 1;
        panel.add(citizenNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Ward:"), gbc);
        gbc.gridx = 1;
        panel.add(wardField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        panel.add(descScrollPane, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        panel.add(statusCombo, gbc);

        // Show dialog
        int result = JOptionPane.showConfirmDialog(
            this, 
            panel, 
            "Update Service", 
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            // Validate input
            String serviceName = (String) serviceNameCombo.getSelectedItem();
            String citizenName = citizenNameField.getText().trim();
            String ward = wardField.getText().trim();
            String description = descriptionArea.getText().trim();
            String status = (String) statusCombo.getSelectedItem();

            if (serviceName == null || serviceName.isEmpty() || citizenName.isEmpty() || ward.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please fill in all required fields (Service Name, Citizen Name, Ward).", 
                    "Validation Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Get the service ID from the first column (hidden or visible)
            // We need to identify the service by citizen name and original service name for now
            boolean success = updateServiceInDatabase(currentCitizenName, currentServiceName, 
                                                    serviceName, citizenName, ward, description, status);
            
            if (success) {
                // Update the table display only if database update was successful
                // Table columns: "Service_id", "ServiceName", "SubmittedAT", "NameOFCitizen", "Ward", "Description", "Status"
                model.setValueAt(serviceName, selectedRow, 1); // ServiceName column
                model.setValueAt(citizenName, selectedRow, 3); // NameOFCitizen column
                model.setValueAt(ward, selectedRow, 4); // Ward column
                model.setValueAt(description, selectedRow, 5); // Description column
                model.setValueAt(status, selectedRow, 6); // Status column
                
                JOptionPane.showMessageDialog(this, 
                    "Service updated successfully in database!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
                // Refresh table to show updated data from database
                refreshTableData();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to update service in database!", 
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Save service to database
     */
    private boolean saveServiceToDatabase(String serviceName, String citizenName, String ward, String description, String status) {
        MySqlConnection dbConnection = new MySqlConnection();
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                System.err.println("Failed to establish database connection");
                return false;
            }

            // Create services table if it doesn't exist
            createServicesTableIfNotExists(conn);

            // Auto-generate service ID and current timestamp
            String currentTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            
            String sql = "INSERT INTO services (service_name, submitted_at, name_of_citizen, ward, description, status, created_by_user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, serviceName);
            stmt.setString(2, currentTimestamp);
            stmt.setString(3, citizenName);
            stmt.setString(4, ward);
            stmt.setString(5, description);
            stmt.setString(6, status);
            stmt.setString(7, currentUserId); // Store the user ID who created the service

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) dbConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }
    }

    /**
     * Delete service from database
     */
    private boolean deleteServiceFromDatabase(String citizenName, String serviceName) {
        MySqlConnection dbConnection = new MySqlConnection();
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                System.err.println("Failed to establish database connection");
                return false;
            }

            // Delete the service record using citizen name and service name as identifiers
            String sql = "DELETE FROM services WHERE name_of_citizen = ? AND service_name = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, citizenName);
            stmt.setString(2, serviceName);

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Service deleted successfully from database. Rows affected: " + rowsAffected);
                return true;
            } else {
                System.err.println("No service found to delete with citizen: " + citizenName + " and service: " + serviceName);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Database error during delete: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) dbConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }
    }

    /**
     * Update service in database
     */
    private boolean updateServiceInDatabase(String originalCitizenName, String originalServiceName,
                                          String newServiceName, String newCitizenName, String newWard, 
                                          String newDescription, String newStatus) {
        MySqlConnection dbConnection = new MySqlConnection();
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                System.err.println("Failed to establish database connection");
                return false;
            }

            // Update the service record using original citizen name and service name as identifiers
            String sql = "UPDATE services SET service_name = ?, name_of_citizen = ?, ward = ?, description = ?, status = ?, updated_at = CURRENT_TIMESTAMP " +
                        "WHERE name_of_citizen = ? AND service_name = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newServiceName);
            stmt.setString(2, newCitizenName);
            stmt.setString(3, newWard);
            stmt.setString(4, newDescription);
            stmt.setString(5, newStatus);
            stmt.setString(6, originalCitizenName);  // WHERE condition
            stmt.setString(7, originalServiceName);   // WHERE condition

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Service updated successfully in database. Rows affected: " + rowsAffected);
                return true;
            } else {
                System.err.println("No service found to update with citizen: " + originalCitizenName + " and service: " + originalServiceName);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Database error during update: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) dbConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }
    }

    /**
     * Create services table if it doesn't exist
     */
    private void createServicesTableIfNotExists(Connection conn) throws SQLException {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS services (
                service_id INT AUTO_INCREMENT PRIMARY KEY,
                service_name VARCHAR(255) NOT NULL,
                submitted_at DATETIME NOT NULL,
                name_of_citizen VARCHAR(255) NOT NULL,
                ward INT NOT NULL,
                phone VARCHAR(20),
                email VARCHAR(255),
                description TEXT,
                status VARCHAR(50) DEFAULT 'Pending',
                created_by_user_id VARCHAR(50),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
            )
        """;
        
        try (PreparedStatement stmt = conn.prepareStatement(createTableSQL)) {
            stmt.executeUpdate();
        }
        
        // Add the created_by_user_id column if it doesn't exist (for existing tables)
        try {
            String addColumnSQL = "ALTER TABLE services ADD COLUMN created_by_user_id VARCHAR(50)";
            PreparedStatement addColumnStmt = conn.prepareStatement(addColumnSQL);
            addColumnStmt.executeUpdate();
            addColumnStmt.close();
        } catch (SQLException e) {
            // Column already exists, ignore the error
        }
    }

    /**
     * Refresh table data from database
     */
    private void refreshTableData() {
        MySqlConnection dbConnection = new MySqlConnection();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                System.err.println("Failed to establish database connection");
                return;
            }

            // Create services table if it doesn't exist
            createServicesTableIfNotExists(conn);
            
            String sql;
            boolean isAdmin = "admin".equalsIgnoreCase(userRole);
            
            if (isAdmin) {
                // Admin sees all services
                sql = "SELECT service_id, service_name, submitted_at, name_of_citizen, ward, description, status FROM services ORDER BY service_id DESC";
                stmt = conn.prepareStatement(sql);
            } else {
                // Users see only their own services
                sql = "SELECT service_id, service_name, submitted_at, name_of_citizen, ward, description, status FROM services WHERE created_by_user_id = ? ORDER BY service_id DESC";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, currentUserId);
            }
            
            rs = stmt.executeQuery();

            // Clear existing table data
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            // Add data from database
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("service_id"),
                    rs.getString("service_name"),
                    rs.getString("submitted_at"),
                    rs.getString("name_of_citizen"),
                    rs.getInt("ward"),
                    rs.getString("description"),
                    rs.getString("status")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            System.err.println("Database error while refreshing table: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) dbConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }
    }

    private void setupPanel2Layout() {
        // Create a GroupLayout for panel2 with clean layout
        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        
        // Add components to panel2 (removed jLabel2 and jLabel3 - Services and Requests labels)
        panel2.add(jLabel1);
        panel2.add(jLabel4);
        panel2.add(jLabel5);
        panel2.add(jLabel6);
        panel2.add(jTextField1);
        panel2.add(jTextField3);
        panel2.add(jScrollPane1);
        panel2.add(jButton5);
        panel2.add(jButton2);
        panel2.add(jButton3);
        panel2.add(jButton4);
        panel2.add(backButton);
        
        // Set up horizontal layout - both search fields on same line
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(backButton)))
                .addContainerGap())
        );
        
        // Set up vertical layout - single row for both search fields
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(backButton))
                .addContainerGap())
        );
    }

    private void addSearchFunctionality() {
        // Add document listener to search field (jTextField3) to filter table as user types
        jTextField3.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { 
                filterTable(jTextField3.getText().trim()); 
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { 
                filterTable(jTextField3.getText().trim()); 
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { 
                filterTable(jTextField3.getText().trim()); 
            }
        });
        
        // Add document listener to citizen field (jTextField1) for additional filtering
        jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { 
                filterTable(jTextField1.getText().trim()); 
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { 
                filterTable(jTextField1.getText().trim()); 
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { 
                filterTable(jTextField1.getText().trim()); 
            }
        });
    }
    
    private void setupEmojiFont() {
        // Multiple approaches to ensure emoji visibility
        try {
            // Try different fonts that support emojis
            java.awt.Font[] emojiCompatibleFonts = {
                new java.awt.Font("Segoe UI Emoji", java.awt.Font.BOLD, 32),
                new java.awt.Font("Apple Color Emoji", java.awt.Font.BOLD, 32),
                new java.awt.Font("Noto Color Emoji", java.awt.Font.BOLD, 32),
                new java.awt.Font("Symbola", java.awt.Font.BOLD, 32),
                new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.BOLD, 32)
            };
            
            boolean emojiSet = false;
            for (java.awt.Font font : emojiCompatibleFonts) {
                if (font.canDisplayUpTo("🏛️") == -1) {
                    jLabel1.setFont(font);
                    emojiSet = true;
                    break;
                }
            }
            
            if (!emojiSet) {
                // If no emoji font works, use a clear text alternative
                jLabel1.setText("⌂ Hamro Smart Gaun ⌂");
                jLabel1.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 32));
            }
            
            // Force repaint to ensure changes are visible
            jLabel1.repaint();
            
        } catch (Exception e) {
            // Ultimate fallback
            jLabel1.setText("HAMRO SMART GAUN");
            jLabel1.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 28));
        }
    }
    
    private void filterTable(String searchText) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        if (searchText.isEmpty()) {
            // Show all rows - reload from database
            refreshTableData();
        } else {
            // Filter the current table data
            DefaultTableModel filteredModel = new DefaultTableModel();
            // Copy column structure
            for (int i = 0; i < model.getColumnCount(); i++) {
                filteredModel.addColumn(model.getColumnName(i));
            }
            
            // Filter rows based on search text (case-insensitive)
            for (int i = 0; i < model.getRowCount(); i++) {
                boolean matchFound = false;
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Object value = model.getValueAt(i, j);
                    if (value != null && value.toString().toLowerCase().contains(searchText.toLowerCase())) {
                        matchFound = true;
                        break;
                    }
                }
                if (matchFound) {
                    Object[] row = new Object[model.getColumnCount()];
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        row[j] = model.getValueAt(i, j);
                    }
                    filteredModel.addRow(row);
                }
            }
            
            jTable1.setModel(filteredModel);
            System.out.println("Filtered table with search text: '" + searchText + "' - Found " + filteredModel.getRowCount() + " matches");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new java.awt.Panel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        panel2 = new java.awt.Panel();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        backButton = new javax.swing.JButton();

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jFormattedTextField1.setText("jFormattedTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel2.setBackground(new java.awt.Color(153, 102, 255));
        panel2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jButton5.setBackground(new java.awt.Color(0, 0, 255));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gaumanagementsystem/view/plus (1).png"))); // NOI18N
        jButton5.setText("ADD");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("🏛️ Hamro Smart Gaun 🏛️");
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setToolTipText("");
        
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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Services");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Requests");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Service");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Service_id", "ServiceName", "SubmittedAT", "NameOFCitizen", "Ward", "Description", "Status"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton2.setBackground(new java.awt.Color(0, 0, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setText("DELETE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 51, 204));
        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton3.setText("UPDATE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 51, 204));
        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton4.setText("REFRESH");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Search");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Citizen");

        backButton.setBackground(new java.awt.Color(0, 51, 204));
        backButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        backButton.setText("Back");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // DELETE button - check if row is selected
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a service to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this service?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Get service details for database deletion
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            String citizenName = (String) model.getValueAt(selectedRow, 3); // NameOFCitizen column
            String serviceName = (String) model.getValueAt(selectedRow, 1); // ServiceName column
            
            // Delete from database first
            if (deleteServiceFromDatabase(citizenName, serviceName)) {
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Service deleted successfully!");
                refreshTableData(); // Refresh to show updated data
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete service from database!", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // UPDATE button - check if row is selected
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a service to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        showUpdateServiceDialog(selectedRow);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // REFRESH button - reload data from database
        refreshTableData();
        JOptionPane.showMessageDialog(this, "Table refreshed from database!");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // ADD button functionality - open service form dialog
        showAddServiceDialog();
    }//GEN-LAST:event_jButton5ActionPerformed


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
            java.util.logging.Logger.getLogger(ProjectRequests.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProjectRequests.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProjectRequests.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProjectRequests.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProjectRequests().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton backButton;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    // End of variables declaration//GEN-END:variables
}

