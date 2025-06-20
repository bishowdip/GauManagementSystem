/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gaumanagementsystem.view;

import gaumanagementsystem.controller.CitizenController;
import gaumanagementsystem.model.CitizenData;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import java.awt.Image; // For image scaling
import javax.swing.ImageIcon; // For image icons
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JPanel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.GridLayout;

/**
 *
 * @author wange
 */
public class CitizenEdit extends javax.swing.JFrame {

    private String userRole = "user"; // default
    private CitizenController citizenController; // Declare CitizenController
    private String currentCitizenNumber; // Add this to track the logged-in user's citizen number

    // Declare new UI components for Citizen Data
    private JTextField citizenIdField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField dateOfBirthField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField fatherNameField;
    private JTextField motherNameField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private ButtonGroup genderButtonGroup;
    private JLabel profileImageLabel; // For displaying citizen's image

    /**
     * Creates new form CitizenEdit
     */
    public CitizenEdit() {
        initComponents();
        citizenController = new CitizenController();
        loadCitizenTableData();
        addTableDoubleClickListener();
        
        // Standardize button styling to match NewsAndNotice
        Color lightBlue = new Color(173, 216, 230);
        add.setBackground(lightBlue); 
        add.setForeground(Color.BLACK);
        add.setText("ADD");
        
        remove.setBackground(lightBlue); 
        remove.setForeground(Color.BLACK);
        remove.setText("DELETE");
        
        update.setBackground(lightBlue); 
        update.setForeground(Color.BLACK);
        update.setText("UPDATE");
        
        CitizentoDashboard.setBackground(lightBlue); 
        CitizentoDashboard.setForeground(Color.BLACK);
        CitizentoDashboard.setText("Back");
    }

    // New constructor with role
    public CitizenEdit(String userRole, String currentCitizenNumber) {
        this();
        this.userRole = userRole;
        this.currentCitizenNumber = currentCitizenNumber;
        
        // Load data based on role
        if ("user".equalsIgnoreCase(userRole)) {
            loadCitizenTableDataForUser();
        } else {
            loadCitizenTableData();
        }
    }

    // Method to load data into the table
    private void loadCitizenTableData() {
        DefaultTableModel model = (DefaultTableModel) citizen_table.getModel();
        model.setRowCount(0); // Clear existing data
        List<CitizenData> citizens = citizenController.getAllCitizens();
        for (CitizenData citizen : citizens) {
            model.addRow(new Object[]{
                citizen.getCitizenId(),
                citizen.getName(),
                "Ward", // Placeholder, assuming this will be filled or derived
                citizen.getGender(),
                citizen.getPhone(),
                citizen.getAddress(),
                citizen.getEmail()
            });
        }
    }

    // Load only the current user's data for user role
    private void loadCitizenTableDataForUser() {
        DefaultTableModel model = (DefaultTableModel) citizen_table.getModel();
        model.setRowCount(0);
        CitizenData citizen = citizenController.getCitizenById(currentCitizenNumber);
        if (citizen != null) {
            model.addRow(new Object[]{
                citizen.getCitizenId(),
                citizen.getName(),
                "Ward",
                citizen.getGender(),
                citizen.getPhone(),
                citizen.getAddress(),
                citizen.getEmail()
            });
        }
    }

    // Add double-click listener to the table and Right-Click listener
    private void addTableDoubleClickListener() {
        citizen_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) { // Double-left-click
                    int selectedRow = citizen_table.getSelectedRow();
                    if (selectedRow != -1) {
                        String citizenId = (String) citizen_table.getValueAt(selectedRow, 0); // Assuming Citizen-number is in the first column (index 0)
                        loadCitizenDetailsForEditing(citizenId);
                    }
                } else if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) { // Single right-click
                    int row = citizen_table.rowAtPoint(e.getPoint());
                    if (row != -1) {
                        citizen_table.setRowSelectionInterval(row, row); // Select the row that was right-clicked
                        showRightClickMenu(e.getX(), e.getY());
                    }
                }
            }
        });
    }

    // Method to load citizen details into the fields for editing
    private void loadCitizenDetailsForEditing(String citizenId) {
        CitizenData citizen = citizenController.getCitizenById(citizenId);
        if (citizen != null) {
            citizenIdField.setText(citizen.getCitizenId());
            nameField.setText(citizen.getName());
            emailField.setText(citizen.getEmail());
            dateOfBirthField.setText(citizen.getDateOfBirth());
            addressField.setText(citizen.getAddress());
            phoneField.setText(citizen.getPhone());
            fatherNameField.setText(citizen.getFatherName());
            motherNameField.setText(citizen.getMotherName());

            // Set gender
            if ("Male".equalsIgnoreCase(citizen.getGender())) {
                maleRadioButton.setSelected(true);
            } else if ("Female".equalsIgnoreCase(citizen.getGender())) {
                femaleRadioButton.setSelected(true);
            } else {
                genderButtonGroup.clearSelection();
            }

            // Load image
            if (citizen.getImagePath() != null && !citizen.getImagePath().isEmpty()) {
                try {
                    ImageIcon originalIcon = new ImageIcon(citizen.getImagePath());
                    Image scaledImage = originalIcon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                    profileImageLabel.setIcon(new ImageIcon(scaledImage));
                } catch (Exception ex) {
                    profileImageLabel.setIcon(null); // Clear image if error
                    ex.printStackTrace();
                }
            } else {
                profileImageLabel.setIcon(null); // Clear image if no path
            }
        } else {
            JOptionPane.showMessageDialog(this, "Citizen not found!", "Error", JOptionPane.ERROR_MESSAGE);
            clearInputFields(); // Clear fields if citizen not found
        }
    }

    // Method to clear all input fields
    private void clearInputFields() {
        citizenIdField.setText("");
        nameField.setText("");
        emailField.setText("");
        dateOfBirthField.setText("");
        addressField.setText("");
        phoneField.setText("");
        fatherNameField.setText("");
        motherNameField.setText("");
        genderButtonGroup.clearSelection();
        profileImageLabel.setIcon(null);
    }
    
    // Method to open date picker dialog
    private void openDatePickerForField(JTextField dateField) {
        // Create date picker dialog
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        
        // Set current value from text field if valid
        String currentText = dateField.getText().trim();
        if (!currentText.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date currentDate = sdf.parse(currentText);
                dateSpinner.setValue(currentDate);
            } catch (Exception ex) {
                dateSpinner.setValue(new Date());
            }
        } else {
            dateSpinner.setValue(new Date());
        }
        
        // Create panel for dialog
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Select Date:"));
        panel.add(dateSpinner);
        
        // Show dialog
        int result = JOptionPane.showConfirmDialog(
            this, 
            panel, 
            "Date Picker", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            // Format and set the selected date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format((Date) dateSpinner.getValue());
            dateField.setText(formattedDate);
        }
    }
    
    // Method to get CitizenData from fields (for Add/Update)
    private CitizenData getCitizenDataFromFields() {
        String citizenId = citizenIdField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String dateOfBirth = dateOfBirthField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String fatherName = fatherNameField.getText();
        String motherName = motherNameField.getText();
        String gender = maleRadioButton.isSelected() ? "Male" : (femaleRadioButton.isSelected() ? "Female" : "");
        // Assuming imagePath handling will be integrated with an upload button later, for now just retrieve the existing one if any
        // For a new image, you would save it and get the path here
        String imagePath = (profileImageLabel.getIcon() != null && profileImageLabel.getIcon() instanceof ImageIcon)
                           ? ((ImageIcon) profileImageLabel.getIcon()).getDescription() : null; // This might need refinement for actual file path

        return new CitizenData(citizenId, name, email, dateOfBirth, address, gender, phone, fatherName, motherName, imagePath);
    }

    // Show right-click context menu
    private void showRightClickMenu(int x, int y) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem viewProfileItem = new JMenuItem("View Profile");
        viewProfileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = citizen_table.getSelectedRow();
                if (selectedRow != -1) {
                    String citizenId = (String) citizen_table.getValueAt(selectedRow, 0);
                    // Open ProfileView and hide the edit button
                    ProfileView profileView = new ProfileView(citizenId, false);
                    profileView.setVisible(true);
                }
            }
        });
        popupMenu.add(viewProfileItem);
        popupMenu.show(citizen_table, x, y);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        CitizentoDashboard = new javax.swing.JButton();
        update = new javax.swing.JButton();
        remove = new javax.swing.JButton();
        add = new javax.swing.JButton();
        search = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        citizen_table = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel1.setText("Hamro Smart Gaun");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(285, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(267, 267, 267))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 80));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        CitizentoDashboard.setBackground(new java.awt.Color(102, 102, 255));
        CitizentoDashboard.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        CitizentoDashboard.setForeground(new java.awt.Color(255, 255, 255));
        CitizentoDashboard.setText("Back");
        CitizentoDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CitizentoDashboardActionPerformed(evt);
            }
        });

        update.setBackground(new java.awt.Color(102, 102, 255));
        update.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        update.setForeground(new java.awt.Color(255, 255, 255));
        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        remove.setBackground(new java.awt.Color(102, 102, 255));
        remove.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        remove.setForeground(new java.awt.Color(255, 255, 255));
        remove.setText("Delete");
        remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeActionPerformed(evt);
            }
        });

        add.setBackground(new java.awt.Color(102, 102, 255));
        add.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        add.setForeground(new java.awt.Color(255, 255, 255));
        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        search.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        citizen_table.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Citizen-number", "Name", "Ward", "Gender", "Phone", "Address", "Email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        citizen_table.setShowGrid(true);
        jScrollPane1.setViewportView(citizen_table);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText(" Search Citizen");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(remove, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115)
                        .addComponent(CitizentoDashboard)
                        .addGap(29, 29, 29)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CitizentoDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remove, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 880, 430));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        int selectedRow = citizen_table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a citizen from the table to update.", "No Citizen Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String citizenId = (String) citizen_table.getValueAt(selectedRow, 0); // Citizen-number is the primary key
        // Open EditProfileView in edit mode with selected citizen's number
        EditProfileView editProfileView = new EditProfileView(userRole, citizenId, true);
        editProfileView.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_updateActionPerformed

    private void CitizentoDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CitizentoDashboardActionPerformed
        // TODO add your handling code here:
        DashboardView goingtodash = new DashboardView();
        goingtodash.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_CitizentoDashboardActionPerformed

    private void removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeActionPerformed
        int selectedRow = citizen_table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a citizen from the table to remove.", "No Citizen Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String citizenIdToRemove = (String) citizen_table.getValueAt(selectedRow, 0); // Assuming Citizen-number is in the first column (index 0)

        // The CitizenController.deleteCitizen already includes a confirmation dialog
        boolean success = citizenController.deleteCitizen(citizenIdToRemove);

        if (success) {
            // The success message is already handled by CitizenController.deleteCitizen
            loadCitizenTableData(); // Refresh the table after successful removal
            clearInputFields(); // Clear fields as the selected citizen is removed
        } else {
            // The error message is already handled by CitizenController.deleteCitizen
            // No specific action needed here unless additional custom error handling is desired
        }
    }//GEN-LAST:event_removeActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        // Open EditProfileView in add mode (empty fields)
        EditProfileView editProfileView = new EditProfileView(userRole, null, false);
        editProfileView.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_addActionPerformed

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
            java.util.logging.Logger.getLogger(CitizenEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CitizenEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CitizenEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CitizenEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CitizenEdit("admin", "admin").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CitizentoDashboard;
    private javax.swing.JButton add;
    private javax.swing.JTable citizen_table;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton remove;
    private javax.swing.JTextField search;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}