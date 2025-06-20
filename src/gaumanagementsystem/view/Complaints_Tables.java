/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gaumanagementsystem.view;

import java.awt.Color;
import javax.swing.JOptionPane;
import java.util.Date;

/**
 *
 * @author SONIC
 */
public class Complaints_Tables extends javax.swing.JFrame {

    /**
     * Creates new form Complaints_Tables
     */
    public Complaints_Tables() {
        initComponents();
        setSize(900, 650); // Set appropriate window size
        setLocationRelativeTo(null); // Center the window
        setResizable(true); // Override the setResizable(false) from initComponents
        
        // Standardize button styling to match NewsAndNotice
        Color lightBlue = new Color(173, 216, 230);
        
        Back1.setBackground(lightBlue);
        Back1.setForeground(Color.BLACK);
        Back1.setText("Back");
        
        // Add action listener for Back button to ensure it works with absolute positioning
        Back1.addActionListener(e -> {
            gaumanagementsystem.view.DashboardView dashboard = new gaumanagementsystem.view.DashboardView();
            dashboard.setVisible(true);
            dispose();
        });
        
        // Add CRUD buttons using absolute positioning to avoid layout conflicts
        javax.swing.JButton addButton = new javax.swing.JButton("ADD");
        addButton.setBackground(lightBlue);
        addButton.setForeground(Color.BLACK);
        addButton.setBounds(150, 480, 100, 30);
        addButton.addActionListener(e -> {
            // Implement add complaint functionality
            javax.swing.JTextField nameField = new javax.swing.JTextField();
            javax.swing.JTextField emailField = new javax.swing.JTextField();
            javax.swing.JTextField descriptionField = new javax.swing.JTextField();
            javax.swing.JTextField statusField = new javax.swing.JTextField("Pending");
            javax.swing.JTextField feedbackField = new javax.swing.JTextField();
            javax.swing.JTextField checkingField = new javax.swing.JTextField("Not Checked");
            
            // Create date field with current date
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            javax.swing.JTextField dateField = new javax.swing.JTextField(sdf.format(new java.util.Date()));
            
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1));
            panel.add(new javax.swing.JLabel("Name:"));
            panel.add(nameField);
            panel.add(new javax.swing.JLabel("Date:"));
            panel.add(dateField);
            panel.add(new javax.swing.JLabel("Email:"));
            panel.add(emailField);
            panel.add(new javax.swing.JLabel("Description:"));
            panel.add(descriptionField);
            panel.add(new javax.swing.JLabel("Status:"));
            panel.add(statusField);
            panel.add(new javax.swing.JLabel("Feedback:"));
            panel.add(feedbackField);
            panel.add(new javax.swing.JLabel("Checking:"));
            panel.add(checkingField);
            
            int result = JOptionPane.showConfirmDialog(this, panel, "Add Complaint", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();
                String date = dateField.getText().trim();
                String email = emailField.getText().trim();
                String description = descriptionField.getText().trim();
                String status = statusField.getText().trim();
                String feedback = feedbackField.getText().trim();
                String checking = checkingField.getText().trim();
                
                if (name.isEmpty() || email.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name, Email, and Description are required fields!");
                    return;
                }
                
                // Add to table
                javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) ComplaintTable1.getModel();
                model.addRow(new Object[]{name, date, email, description, status, feedback, checking});
                JOptionPane.showMessageDialog(this, "Complaint added successfully!");
            }
        });
        
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
            
            // Get current values from the selected row
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) ComplaintTable1.getModel();
            String currentName = (String) model.getValueAt(selectedRow, 0);
            String currentDate = (String) model.getValueAt(selectedRow, 1);
            String currentEmail = (String) model.getValueAt(selectedRow, 2);
            String currentDescription = (String) model.getValueAt(selectedRow, 3);
            String currentStatus = (String) model.getValueAt(selectedRow, 4);
            String currentFeedback = (String) model.getValueAt(selectedRow, 5);
            String currentChecking = (String) model.getValueAt(selectedRow, 6);
            
            // Create form fields with current values
            javax.swing.JTextField nameField = new javax.swing.JTextField(currentName);
            javax.swing.JTextField dateField = new javax.swing.JTextField(currentDate);
            javax.swing.JTextField emailField = new javax.swing.JTextField(currentEmail);
            javax.swing.JTextField descriptionField = new javax.swing.JTextField(currentDescription);
            javax.swing.JTextField statusField = new javax.swing.JTextField(currentStatus);
            javax.swing.JTextField feedbackField = new javax.swing.JTextField(currentFeedback);
            javax.swing.JTextField checkingField = new javax.swing.JTextField(currentChecking);
            
            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1));
            panel.add(new javax.swing.JLabel("Name:"));
            panel.add(nameField);
            panel.add(new javax.swing.JLabel("Date:"));
            panel.add(dateField);
            panel.add(new javax.swing.JLabel("Email:"));
            panel.add(emailField);
            panel.add(new javax.swing.JLabel("Description:"));
            panel.add(descriptionField);
            panel.add(new javax.swing.JLabel("Status:"));
            panel.add(statusField);
            panel.add(new javax.swing.JLabel("Feedback:"));
            panel.add(feedbackField);
            panel.add(new javax.swing.JLabel("Checking:"));
            panel.add(checkingField);
            
            int result = JOptionPane.showConfirmDialog(this, panel, "Update Complaint", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String name = nameField.getText().trim();
                String date = dateField.getText().trim();
                String email = emailField.getText().trim();
                String description = descriptionField.getText().trim();
                String status = statusField.getText().trim();
                String feedback = feedbackField.getText().trim();
                String checking = checkingField.getText().trim();
                
                if (name.isEmpty() || email.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name, Email, and Description are required fields!");
                    return;
                }
                
                // Update the table
                model.setValueAt(name, selectedRow, 0);
                model.setValueAt(date, selectedRow, 1);
                model.setValueAt(email, selectedRow, 2);
                model.setValueAt(description, selectedRow, 3);
                model.setValueAt(status, selectedRow, 4);
                model.setValueAt(feedback, selectedRow, 5);
                model.setValueAt(checking, selectedRow, 6);
                
                JOptionPane.showMessageDialog(this, "Complaint updated successfully!");
            }
        });
        
        javax.swing.JButton refreshButton = new javax.swing.JButton("REFRESH");
        refreshButton.setBackground(lightBlue);
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setBounds(510, 480, 100, 30);
        refreshButton.addActionListener(e -> {
            // Implement refresh functionality - clear and reload table
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) ComplaintTable1.getModel();
            model.setRowCount(0); // Clear all rows
            
            // Add some sample data for demonstration (in a real app, this would load from database)
            model.addRow(new Object[]{"John Doe", "2024-01-15", "john@email.com", "Street light not working", "Pending", "", "Not Checked"});
            model.addRow(new Object[]{"Jane Smith", "2024-01-14", "jane@email.com", "Water supply issue", "In Progress", "Under review", "Checked"});
            model.addRow(new Object[]{"Bob Wilson", "2024-01-13", "bob@email.com", "Road repair needed", "Resolved", "Fixed last week", "Checked"});
            
            JOptionPane.showMessageDialog(this, "Table refreshed with latest data!");
        });
        
        // Add buttons directly to content pane with absolute positioning
        getContentPane().setLayout(null);
        
        // Re-add all the original components with their positions
        jPanel1.setBounds(6, 6, 888, 80);
        jLabel3.setBounds(355, 98, 180, 17);
        jScrollPane2.setBounds(0, 127, 900, 350);
        Back1.setBounds(750, 480, 100, 30);
        
        getContentPane().add(jPanel1);
        getContentPane().add(jLabel3);
        getContentPane().add(jScrollPane2);
        getContentPane().add(addButton);
        getContentPane().add(deleteButton);
        getContentPane().add(updateButton);
        getContentPane().add(refreshButton);
        getContentPane().add(Back1);
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
                "Name", "Date", "Email", "Description", "Status", "Feedback", "Checking"
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
                        .addGap(326, 326, 326)
                        .addComponent(jLabel1)))
                .addContainerGap(367, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addContainerGap(14, Short.MAX_VALUE))
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
                "Name", "Date", "Email", "Description", "Status", "Feedback", "Checking"
            }
        ));
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
        gaumanagementsystem.view.DashboardView dashboard = new gaumanagementsystem.view.DashboardView();
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
}
