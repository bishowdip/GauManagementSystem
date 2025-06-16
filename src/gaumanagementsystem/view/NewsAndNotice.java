/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gaumanagementsystem.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class NewsAndNotice extends javax.swing.JFrame {

    /**
     * Creates new form NewsAndNotice
     */
    public NewsAndNotice() {
        initComponents();
        // Add sample data
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        Object[][] sampleData = {
            { "2024-03-20", "All Citizens", "Road Maintenance", "Annual road maintenance work will begin next week", "2024-04-20"},
            { "2024-03-19", "Students", "School Holiday", "School will remain closed for spring break", "2024-03-25"},
            { "2024-03-18", "Farmers", "Agricultural Workshop", "Free workshop on modern farming techniques", "2024-03-30"},
            { "2024-03-17", "Business Owners", "Tax Filing", "Last date for tax filing is approaching", "2024-03-31"},
            { "2024-03-16", "All Citizens", "Water Supply", "Water supply will be affected due to maintenance", "2024-03-18"}
        };
        for (Object[] row : sampleData) {
            model.addRow(row);
        }

        // ADD button
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create text fields for each input
                javax.swing.JTextField dateField = new javax.swing.JTextField();
                javax.swing.JTextField audienceField = new javax.swing.JTextField();
                javax.swing.JTextField subjectField = new javax.swing.JTextField();
                javax.swing.JTextField descriptionField = new javax.swing.JTextField();
                javax.swing.JTextField expiryDateField = new javax.swing.JTextField();
                // Create a panel and add the fields with labels
                javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1));
                panel.add(new javax.swing.JLabel("Date (YYYY-MM-DD):"));
                panel.add(dateField);
                panel.add(new javax.swing.JLabel("Audience:"));
                panel.add(audienceField);
                panel.add(new javax.swing.JLabel("Subject:"));
                panel.add(subjectField);
                panel.add(new javax.swing.JLabel("Description:"));
                panel.add(descriptionField);
                panel.add(new javax.swing.JLabel("Expiry Date (YYYY-MM-DD):"));
                panel.add(expiryDateField);
                int result = JOptionPane.showConfirmDialog(NewsAndNotice.this, panel, "Add News/Notice", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String date = dateField.getText();
                    String audience = audienceField.getText();
                    String subject = subjectField.getText();
                    String description = descriptionField.getText();
                    String expiryDate = expiryDateField.getText();
                    if (date.isEmpty() || audience.isEmpty() || subject.isEmpty() || description.isEmpty() || expiryDate.isEmpty()) {
                        JOptionPane.showMessageDialog(NewsAndNotice.this, "All fields are required!");
                        return;
                    }
                    var model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
                    model.addRow(new Object[]{date, audience, subject, description, expiryDate});
                }
            }
        });

        // DELETE button
        jButton2.addActionListener(e -> {
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Are you sure you want to delete this notice?", 
                    "Confirm Delete", 
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            }
        });

        // UPDATE button
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow != -1) {
                    
                    String date = JOptionPane.showInputDialog(NewsAndNotice.this, 
                        "Enter Date (YYYY-MM-DD):", model.getValueAt(selectedRow, 1));
                    if (date == null) return;
                    
                    String audience = JOptionPane.showInputDialog(NewsAndNotice.this, 
                        "Enter Audience:", model.getValueAt(selectedRow, 2));
                    if (audience == null) return;
                    
                    String subject = JOptionPane.showInputDialog(NewsAndNotice.this, 
                        "Enter Subject:", model.getValueAt(selectedRow, 3));
                    if (subject == null) return;
                    
                    String description = JOptionPane.showInputDialog(NewsAndNotice.this, 
                        "Enter Description:", model.getValueAt(selectedRow, 4));
                    if (description == null) return;
                    
                    String expiryDate = JOptionPane.showInputDialog(NewsAndNotice.this, 
                        "Enter Expiry Date (YYYY-MM-DD):", model.getValueAt(selectedRow, 5));
                    if (expiryDate == null) return;
                    
                   
                    model.setValueAt(date, selectedRow, 1);
                    model.setValueAt(audience, selectedRow, 2);
                    model.setValueAt(subject, selectedRow, 3);
                    model.setValueAt(description, selectedRow, 4);
                    model.setValueAt(expiryDate, selectedRow, 5);
                } else {
                    JOptionPane.showMessageDialog(NewsAndNotice.this, "Please select a row to update.");
                }
            }
        });

        // REFRESH button
        jButton4.addActionListener(e -> {
            // Clear the table
            model.setRowCount(0);
            // Reload sample data
            for (Object[] row : sampleData) {
                model.addRow(row);
            }
            JOptionPane.showMessageDialog(this, "Table refreshed successfully!");
        });
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
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

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

        panel2.setBackground(new java.awt.Color(204, 204, 255));
        panel2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Hamro Smart Gaun");
        jLabel1.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("News");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Notices");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("News and Notice");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Date", "Audience", "Subject", "Description", "ExpiryDate"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton2.setBackground(new java.awt.Color(0, 0, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton2.setText("DELETE");

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

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(264, 264, 264))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addContainerGap(75, Short.MAX_VALUE))
        );

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(NewsAndNotice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewsAndNotice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewsAndNotice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewsAndNotice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewsAndNotice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    // End of variables declaration//GEN-END:variables
}
