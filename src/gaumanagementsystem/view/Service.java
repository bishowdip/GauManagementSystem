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

    /**
     * Creates new form ProjectRequest
     */
    public Service() {
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
            new DashboardView().setVisible(true);
            this.dispose();
        });

        // Add functional button listeners with row selection validation
        jButton2.addActionListener(e -> {
            // DELETE button - check if row is selected
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a service to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this service?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ((javax.swing.table.DefaultTableModel) jTable1.getModel()).removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Service deleted successfully!");
            }
        });
        
        jButton3.addActionListener(e -> {
            // UPDATE button - check if row is selected
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a service to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // TODO: Implement update service functionality
            JOptionPane.showMessageDialog(this, "Update Service functionality to be implemented for selected row: " + (selectedRow + 1));
        });
        
        jButton4.addActionListener(e -> {
            // REFRESH button
            JOptionPane.showMessageDialog(this, "Table refreshed!");
        });
        
        jButton5.addActionListener(e -> {
            // ADD button functionality - open service form dialog
            showAddServiceDialog();
        });

        // Fix image loading for jLabel2
        try {
            java.net.URL imgUrl = getClass().getResource("/gaumanagementsystem/view/village_icon_180434.png");
            if (imgUrl != null) {
                jLabel2.setIcon(new javax.swing.ImageIcon(imgUrl));
            } else {
                // If image not found, set a simple text or leave empty
                jLabel2.setText("🏘️"); // Village emoji as fallback
                jLabel2.setFont(new java.awt.Font("Arial", 0, 24));
            }
        } catch (Exception e) {
            jLabel2.setText("🏘️"); // Village emoji as fallback
            jLabel2.setFont(new java.awt.Font("Arial", 0, 24));
        }
    }

    /**
     * Show dialog to add new service
     */
    private void showAddServiceDialog() {
        // Create form fields
        JTextField serviceNameField = new JTextField(20);
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
        panel.add(serviceNameField, gbc);

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
            String serviceName = serviceNameField.getText().trim();
            String citizenName = citizenNameField.getText().trim();
            String ward = wardField.getText().trim();
            String description = descriptionArea.getText().trim();
            String status = (String) statusCombo.getSelectedItem();

            if (serviceName.isEmpty() || citizenName.isEmpty() || ward.isEmpty()) {
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
            
            String sql = "INSERT INTO services (service_name, submitted_at, citizen_name, ward, description, status) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, serviceName);
            stmt.setString(2, currentTimestamp);
            stmt.setString(3, citizenName);
            stmt.setString(4, ward);
            stmt.setString(5, description);
            stmt.setString(6, status);

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
     * Create services table if it doesn't exist
     */
    private void createServicesTableIfNotExists(Connection conn) throws SQLException {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS services (
                service_id INT AUTO_INCREMENT PRIMARY KEY,
                service_name VARCHAR(255) NOT NULL,
                submitted_at DATETIME NOT NULL,
                citizen_name VARCHAR(255) NOT NULL,
                ward VARCHAR(50) NOT NULL,
                description TEXT,
                status VARCHAR(50) DEFAULT 'Pending',
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
            )
        """;
        
        try (PreparedStatement stmt = conn.prepareStatement(createTableSQL)) {
            stmt.executeUpdate();
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

            String sql = "SELECT service_id, service_name, submitted_at, citizen_name, ward, description, status FROM services ORDER BY service_id DESC";
            stmt = conn.prepareStatement(sql);
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
                    rs.getString("citizen_name"),
                    rs.getString("ward"),
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel1.setText("Hamro Smart Gaun");

        // Fix image loading for jLabel2 - handle missing image gracefully
        try {
            java.net.URL imgUrl = getClass().getResource("/gaumanagementsystem/view/village_icon_180434.png");
            if (imgUrl != null) {
                jLabel2.setIcon(new javax.swing.ImageIcon(imgUrl));
            } else {
                // If image not found, set a simple text or leave empty
                jLabel2.setText("🏘️"); // Village emoji as fallback
                jLabel2.setFont(new java.awt.Font("Arial", 0, 24));
            }
        } catch (Exception e) {
            jLabel2.setText("🏘️"); // Village emoji as fallback
            jLabel2.setFont(new java.awt.Font("Arial", 0, 24));
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(297, 297, 297)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap(337, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 51, 255));
        jLabel3.setText("Service");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Service_id", "ServiceName", "SubmittedAT", "NameOFCitizen", "Ward", "Description", "Status"
            }
        ));
        jTable1.setGridColor(new java.awt.Color(102, 51, 255));
        jTable1.setPreferredSize(new java.awt.Dimension(400, 200));
        jTable1.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        jButton2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton2.setText("Delete");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton3.setText("Update");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setText("Refesh");
        jButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton5.setBackground(new java.awt.Color(51, 51, 255));
        jButton5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton5.setText("+ Add Service");
        jButton5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        backButton = new javax.swing.JButton("Back");
        backButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        backButton.setText("Back");
        backButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 51, 255));
        jLabel5.setText("SEARCH");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(23, 23, 23)
                .addComponent(jButton3)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(backButton))
                .addGap(54, 54, 54))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // DELETE functionality handled in constructor
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // UPDATE functionality handled in constructor
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // REFRESH functionality handled in constructor
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // ADD functionality handled in constructor
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
