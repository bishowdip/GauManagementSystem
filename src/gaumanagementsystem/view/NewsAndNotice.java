package gaumanagementsystem.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import gaumanagementsystem.controller.NewsAndNoticeController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import java.awt.Color;

/**
 *
 * @author ASUS
 */
public class NewsAndNotice extends javax.swing.JFrame {

    private final NewsAndNoticeController controller = new NewsAndNoticeController();
    private String currentTypeFilter = "";
    private JButton backButton;

    /**
     * Creates new form NewsAndNotice
     */
    public NewsAndNotice() {
        this("admin"); // Default to admin for backward compatibility
    }

    public NewsAndNotice(String userRole) {
        initComponents();
        setSize(900, 600); // Set preferred window size
        setLocationRelativeTo(null); // Center the window
        loadTableData();

        // Hide add, delete, update buttons for non-admin users
        boolean isAdmin = "admin".equalsIgnoreCase(userRole);
        jButton1.setVisible(isAdmin); // ADD
        jButton2.setVisible(isAdmin); // DELETE
        jButton3.setVisible(isAdmin); // UPDATE

        // Make News and Notices labels clickable for filtering
        jLabel2.setForeground(Color.BLUE);
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                currentTypeFilter = "News";
                filterTable(getSearchText(), currentTypeFilter);
            }
        });
        jLabel3.setForeground(Color.BLUE);
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                currentTypeFilter = "Notice";
                filterTable(getSearchText(), currentTypeFilter);
            }
        });

        // Create and style the back button
        backButton = new JButton("Back");
        backButton.setBackground(new Color(173, 216, 230));
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> {
            new DashboardView().setVisible(true);
            this.dispose();
        });
        // Set panel2's layout to GroupLayout before using it
        panel2.setLayout(new javax.swing.GroupLayout(panel2));
        javax.swing.GroupLayout panel2Layout = (javax.swing.GroupLayout) panel2.getLayout();
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addGap(192, 192, 192)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(33, 33, 33)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)
                            .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(264, 264, 264))))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addComponent(jLabel5)
                    .addGap(18, 18, 18)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(39, 39, 39)
                    .addComponent(jLabel6)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel3)
                    .addGap(18, 18, 18)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel2Layout.createSequentialGroup()
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addGap(24, 24, 24)
                            .addComponent(jLabel4)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(24, 24, 24)
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4)
                        .addComponent(jButton3)
                        .addComponent(jButton2)
                        .addComponent(backButton))
                    .addContainerGap(67, Short.MAX_VALUE))
        );

        // Set button colors for visibility
        Color lightBlue = new Color(173, 216, 230);
        jButton1.setBackground(lightBlue); jButton1.setForeground(Color.BLACK);
        jButton2.setBackground(lightBlue); jButton2.setForeground(Color.BLACK);
        jButton3.setBackground(lightBlue); jButton3.setForeground(Color.BLACK);
        jButton4.setBackground(lightBlue); jButton4.setForeground(Color.BLACK);

        // ADD button
        jButton1.addActionListener(e -> {
            JTextField dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            dateField.setEditable(false);
            JTextField audienceField = new JTextField();
            JTextField subjectField = new JTextField();
            JTextField descriptionField = new JTextField();
            JTextField expiryDateField = new JTextField();
            String[] types = {"News", "Notice"};
            JComboBox<String> typeCombo = new JComboBox<>(types);

            JPanel panel = new JPanel(new java.awt.GridLayout(0, 1));
            panel.add(new JLabel("Date (auto):"));
            panel.add(dateField);
            panel.add(new JLabel("Audience:"));
            panel.add(audienceField);
            panel.add(new JLabel("Subject:"));
            panel.add(subjectField);
            panel.add(new JLabel("Description:"));
            panel.add(descriptionField);
            panel.add(new JLabel("Expiry Date (YYYY-MM-DD):"));
            panel.add(expiryDateField);
            panel.add(new JLabel("Type:"));
            panel.add(typeCombo);

            int result = JOptionPane.showConfirmDialog(this, panel, "Add News/Notice", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String date = dateField.getText();
                String audience = audienceField.getText();
                String subject = subjectField.getText();
                String description = descriptionField.getText();
                String expiryDate = expiryDateField.getText();
                String type = (String) typeCombo.getSelectedItem();
                if (date.isEmpty() || audience.isEmpty() || subject.isEmpty() || description.isEmpty() || expiryDate.isEmpty() || type.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required!");
                    return;
                }
                gaumanagementsystem.model.NewsAndNotice newNotice = new gaumanagementsystem.model.NewsAndNotice(date, audience, subject, description, expiryDate, type);
                controller.add(newNotice);
                addRowToTable(newNotice);
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
                    controller.delete(selectedRow);
                    ((DefaultTableModel) jTable1.getModel()).removeRow(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            }
        });

        // UPDATE button
        jButton3.addActionListener(e -> {
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow != -1) {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                String date = (String) model.getValueAt(selectedRow, 0);
                String audience = (String) model.getValueAt(selectedRow, 1);
                String subject = (String) model.getValueAt(selectedRow, 2);
                String description = (String) model.getValueAt(selectedRow, 3);
                String expiryDate = (String) model.getValueAt(selectedRow, 4);
                String type = (String) model.getValueAt(selectedRow, 5);

                JTextField dateField = new JTextField(date);
                dateField.setEditable(false);
                JTextField audienceField = new JTextField(audience);
                JTextField subjectField = new JTextField(subject);
                JTextField descriptionField = new JTextField(description);
                JTextField expiryDateField = new JTextField(expiryDate);
                String[] types = {"News", "Notice"};
                JComboBox<String> typeCombo = new JComboBox<>(types);
                typeCombo.setSelectedItem(type);

                JPanel panel = new JPanel(new java.awt.GridLayout(0, 1));
                panel.add(new JLabel("Date (auto):"));
                panel.add(dateField);
                panel.add(new JLabel("Audience:"));
                panel.add(audienceField);
                panel.add(new JLabel("Subject:"));
                panel.add(subjectField);
                panel.add(new JLabel("Description:"));
                panel.add(descriptionField);
                panel.add(new JLabel("Expiry Date (YYYY-MM-DD):"));
                panel.add(expiryDateField);
                panel.add(new JLabel("Type:"));
                panel.add(typeCombo);

                int result = JOptionPane.showConfirmDialog(this, panel, "Update News/Notice", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String newDate = dateField.getText();
                    String newAudience = audienceField.getText();
                    String newSubject = subjectField.getText();
                    String newDescription = descriptionField.getText();
                    String newExpiryDate = expiryDateField.getText();
                    String newType = (String) typeCombo.getSelectedItem();
                    if (newDate.isEmpty() || newAudience.isEmpty() || newSubject.isEmpty() || newDescription.isEmpty() || newExpiryDate.isEmpty() || newType.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "All fields are required!");
                        return;
                    }
                    gaumanagementsystem.model.NewsAndNotice updatedNotice = new gaumanagementsystem.model.NewsAndNotice(newDate, newAudience, newSubject, newDescription, newExpiryDate, newType);
                    controller.update(selectedRow, updatedNotice);
                    model.setValueAt(newDate, selectedRow, 0);
                    model.setValueAt(newAudience, selectedRow, 1);
                    model.setValueAt(newSubject, selectedRow, 2);
                    model.setValueAt(newDescription, selectedRow, 3);
                    model.setValueAt(newExpiryDate, selectedRow, 4);
                    model.setValueAt(newType, selectedRow, 5);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to update.");
            }
        });

        // REFRESH button
        jButton4.addActionListener(e -> {
            loadTableData();
            JOptionPane.showMessageDialog(this, "Table refreshed successfully!");
        });

        // SEARCH and AUDIENCE filter (fix logic)
        ActionListener filterListener = (ActionEvent e) -> {
            filterTable(getSearchText(), currentTypeFilter);
        };
        jTextField3.addActionListener(filterListener);
        jTextField1.addActionListener(filterListener);
        jTextField3.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterTable(getSearchText(), currentTypeFilter); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterTable(getSearchText(), currentTypeFilter); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(getSearchText(), currentTypeFilter); }
        });
        jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterTable(getSearchText(), currentTypeFilter); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterTable(getSearchText(), currentTypeFilter); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(getSearchText(), currentTypeFilter); }
        });
    }

    private void loadTableData() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        for (gaumanagementsystem.model.NewsAndNotice notice : controller.getAll()) {
            model.addRow(notice.toTableRow());
        }
    }

    private void addRowToTable(gaumanagementsystem.model.NewsAndNotice notice) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addRow(notice.toTableRow());
    }

    private void filterTable(String search, String type) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        List<gaumanagementsystem.model.NewsAndNotice> filteredList = controller.search(search, type);
        for (gaumanagementsystem.model.NewsAndNotice notice : filteredList) {
            model.addRow(notice.toTableRow());
        }
    }

    private String getSearchText() {
        String search = jTextField3.getText().trim();
        String audience = jTextField1.getText().trim();
        return (search + " " + audience).trim();
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
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

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

        jButton1.setBackground(new java.awt.Color(0, 0, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gaumanagementsystem/view/plus (1).png"))); // NOI18N
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
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Date", "Audience", "Subject", "Description", "ExpiryDate", "Type"
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

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Search");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Audience");

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
