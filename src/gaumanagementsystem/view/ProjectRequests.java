/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gaumanagementsystem.view;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;

/**
 *
 * @author SONIC
 */
public class ProjectRequests extends JFrame {

    /**
     * Creates new form ProjectRequests
     */
    public ProjectRequests() {
        setTitle("Project Requests");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        
        // Make window fully responsive
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // Start maximized
        setMinimumSize(new java.awt.Dimension(800, 600)); // Set minimum size
        setResizable(true);
        
        // Override the generated layout to make it truly responsive
        makeLayoutResponsive();
        
        // Standardize button styling to match NewsAndNotice
        Color lightBlue = new Color(173, 216, 230);
        
        AddRequest.setBackground(lightBlue);
        AddRequest.setForeground(Color.BLACK);
        AddRequest.setText("ADD");
        
        jButton2.setBackground(lightBlue);
        jButton2.setForeground(Color.BLACK);
        jButton2.setText("DELETE");
        
        jButton3.setBackground(lightBlue);
        jButton3.setForeground(Color.BLACK);
        jButton3.setText("UPDATE");
        
        jButton4.setBackground(lightBlue);
        jButton4.setForeground(Color.BLACK);
        jButton4.setText("REFRESH");
        
        Back1.setBackground(lightBlue);
        Back1.setForeground(Color.BLACK);
        Back1.setText("Back");
        
        // Make AddRequest button functional
        AddRequest.addActionListener(e -> showAddProjectDialog());
        
        // Add functional button listeners
        jButton2.addActionListener(e -> {
            // DELETE button - check if row is selected
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a project to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this project?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ((javax.swing.table.DefaultTableModel) jTable1.getModel()).removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Project deleted successfully!");
            }
        });
        
        jButton3.addActionListener(e -> {
            // UPDATE button - check if row is selected
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a project to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // TODO: Implement update project functionality
            JOptionPane.showMessageDialog(this, "Update Project functionality to be implemented for selected row: " + (selectedRow + 1));
        });
        
        jButton4.addActionListener(e -> {
            // REFRESH button
            JOptionPane.showMessageDialog(this, "Table refreshed!");
        });
        
        // Override the Back button action listener to ensure it works
        Back1.addActionListener(e -> {
            DashboardView dashboard = new DashboardView();
            dashboard.setVisible(true);
            this.dispose();
        });
        
        // Add search functionality
        addSearchFunctionality();
    }
    
    private void makeLayoutResponsive() {
        // Remove the existing layout and create a new responsive one
        getContentPane().removeAll();
        setLayout(new java.awt.BorderLayout());
        
        // Header panel
        add(jPanel1, java.awt.BorderLayout.NORTH);
        
        // Main content panel
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel with title, search, and add button
        javax.swing.JPanel topPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        javax.swing.JPanel leftTopPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        leftTopPanel.add(jLabel3);
        
        // Search section - positioned at left quarter with search field to the right
        javax.swing.JPanel searchSection = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        searchSection.add(javax.swing.Box.createHorizontalStrut(200)); // Left quarter spacing
        searchSection.add(jLabel5);
        searchSection.add(javax.swing.Box.createHorizontalStrut(10)); // Small gap between label and field
        
        // Make search field larger and responsive
        jTextField1.setPreferredSize(new java.awt.Dimension(250, 25));
        jTextField1.setMinimumSize(new java.awt.Dimension(200, 25));
        searchSection.add(jTextField1);
        
        // Right side - add button only
        javax.swing.JPanel rightTopPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        rightTopPanel.add(AddRequest);
        
        topPanel.add(leftTopPanel, java.awt.BorderLayout.WEST);
        topPanel.add(searchSection, java.awt.BorderLayout.CENTER);
        topPanel.add(rightTopPanel, java.awt.BorderLayout.EAST);
        
        mainPanel.add(topPanel, java.awt.BorderLayout.NORTH);
        
        // Table panel (this will now resize with window)
        mainPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        
        add(mainPanel, java.awt.BorderLayout.CENTER);
        
        // Button panel
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton3);
        buttonPanel.add(jButton4);
        buttonPanel.add(javax.swing.Box.createHorizontalStrut(20)); // Add space
        buttonPanel.add(Back1);
        
        add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        // Refresh the layout
        revalidate();
        repaint();
    }
    
    private void addSearchFunctionality() {
        // Add real-time search functionality
        jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
        });
        
        // Add some sample data for demonstration
        populateTableWithSampleData();
    }
    
    private void performSearch() {
        String searchText = jTextField1.getText().toLowerCase().trim();
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        
        if (searchText.isEmpty()) {
            // If search is empty, show all data
            populateTableWithSampleData();
            return;
        }
        
        // Clear current data
        model.setRowCount(0);
        
        // Filter and add matching rows
        Object[][] sampleData = getSampleProjectData();
        for (Object[] row : sampleData) {
            boolean matches = false;
            for (Object cell : row) {
                if (cell != null && cell.toString().toLowerCase().contains(searchText)) {
                    matches = true;
                    break;
                }
            }
            if (matches) {
                model.addRow(row);
            }
        }
    }
    
    private void populateTableWithSampleData() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing data
        
        Object[][] sampleData = getSampleProjectData();
        for (Object[] row : sampleData) {
            model.addRow(row);
        }
    }
    
    private Object[][] getSampleProjectData() {
        return new Object[][] {
            {"REQ001", "Road Construction", "2024-01-15", "Ward 1", "Infrastructure", "2024-06-15", "Main road repair and expansion", "In Progress", 500000},
            {"REQ002", "School Building", "2024-02-01", "Ward 2", "Education", "2024-12-01", "New primary school construction", "Approved", 1200000},
            {"REQ003", "Water Supply", "2024-01-20", "Ward 3", "Utilities", "2024-08-20", "Clean water distribution system", "Planning", 800000},
            {"REQ004", "Health Center", "2024-03-01", "Ward 1", "Healthcare", "2024-11-01", "Community health center establishment", "Pending", 1500000},
            {"REQ005", "Bridge Construction", "2024-02-15", "Ward 4", "Infrastructure", "2024-09-15", "Suspension bridge over river", "In Progress", 2000000},
            {"REQ006", "Solar Installation", "2024-03-10", "Ward 2", "Energy", "2024-07-10", "Solar panels for street lighting", "Approved", 600000},
            {"REQ007", "Community Hall", "2024-01-25", "Ward 3", "Community", "2024-10-25", "Multi-purpose community center", "Planning", 900000},
            {"REQ008", "Waste Management", "2024-02-20", "Ward 5", "Environment", "2024-08-20", "Waste collection and recycling system", "Pending", 400000}
        };
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        AddRequest = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Back1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(153, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Hamro Smart Gaun");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(297, 297, 297)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        jLabel3.setText("Project Requests");

        jTextField1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextField1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 51, 255));
        jLabel5.setText("SEARCH");

        jTable1.setBackground(new java.awt.Color(204, 204, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Request_ID", "Projects_Name", "Started_Date", "Ward", "Category", "Expected to End", "Description", "Status", "Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(102, 51, 255));
        jTable1.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTable1.setShowGrid(true);
        
        // Make table responsive
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setFillsViewportHeight(true);
        
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(AddRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 821, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(312, 312, 312)
                                .addComponent(jButton2)
                                .addGap(29, 29, 29)
                                .addComponent(jButton3)
                                .addGap(32, 32, 32)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Back1)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField1)
                            .addComponent(jLabel5))
                        .addGap(10, 10, 10))
                    .addComponent(AddRequest))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Back1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void AddRequestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddRequestMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_AddRequestMouseClicked

    private void AddRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddRequestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddRequestActionPerformed

    private void Back1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Back1ActionPerformed
        DashboardView dashboard = new DashboardView();
        dashboard.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_Back1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // DELETE functionality handled in constructor
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // UPDATE functionality handled in constructor
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // REFRESH functionality handled in constructor
    }//GEN-LAST:event_jButton4ActionPerformed

    private void showAddProjectDialog() {
        JTextField requestIdField = new JTextField();
        JTextField projectNameField = new JTextField();
        // Date pickers for started date and expected end date
        JSpinner startedDateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor startedDateEditor = new JSpinner.DateEditor(startedDateSpinner, "yyyy-MM-dd");
        startedDateSpinner.setEditor(startedDateEditor);
        startedDateSpinner.setValue(new java.util.Date());
        JSpinner expectedEndSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor expectedEndEditor = new JSpinner.DateEditor(expectedEndSpinner, "yyyy-MM-dd");
        expectedEndSpinner.setEditor(expectedEndEditor);
        expectedEndSpinner.setValue(new java.util.Date());
        JTextField wardField = new JTextField();
        // Fixed category options
        String[] categories = {
            "Education",
            "Health and Medical",
            "Housing and Rent",
            "Transportation",
            "Food and Groceries",
            "Savings and Investments",
            "Entertainment and Leisure"
        };
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        JTextField descriptionField = new JTextField();
        JTextField statusField = new JTextField();
        JTextField budgetField = new JTextField();

        JPanel panel = new JPanel(new java.awt.GridLayout(0, 1));
        panel.add(new JLabel("Request ID:"));
        panel.add(requestIdField);
        panel.add(new JLabel("Project Name:"));
        panel.add(projectNameField);
        panel.add(new JLabel("Started Date:"));
        panel.add(startedDateSpinner);
        panel.add(new JLabel("Ward:"));
        panel.add(wardField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryComboBox);
        panel.add(new JLabel("Expected to End:"));
        panel.add(expectedEndSpinner);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Status:"));
        panel.add(statusField);
        panel.add(new JLabel("Budget:"));
        panel.add(budgetField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Project", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String[] row = new String[] {
                requestIdField.getText(),
                projectNameField.getText(),
                dateFormat.format((Date) startedDateSpinner.getValue()),
                wardField.getText(),
                (String) categoryComboBox.getSelectedItem(),
                dateFormat.format((Date) expectedEndSpinner.getValue()),
                descriptionField.getText(),
                statusField.getText(),
                budgetField.getText()
            };
            for (String s : row) {
                if (s == null || s.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required.");
                    return;
                }
            }
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
            model.addRow(row);
        }
    }

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
            public void run() {
                new ProjectRequests().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddRequest;
    private javax.swing.JButton Back1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
