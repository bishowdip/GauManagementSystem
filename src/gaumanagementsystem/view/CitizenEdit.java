/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gaumanagementsystem.view;

import gaumanagementsystem.controller.CitizenController;
import gaumanagementsystem.model.CitizenData;
import gaumanagementsystem.database.MySqlConnection;
import gaumanagementsystem.view.ProfileView;
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

/**
 *
 * @author wange
 */
public class CitizenEdit extends javax.swing.JFrame {

    private String userRole = "user"; // default
    private CitizenController citizenController; // Declare CitizenController

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
        setCrudButtonsVisible(false); // Hide by default
        // Initialize CitizenController
        citizenController = new CitizenController();
        loadCitizenTableData(); // Load data when the form initializes
        addTableDoubleClickListener(); // Add double-click listener

        // Initialize custom components after initComponents
        initializeCustomComponents();
    }

    // New constructor with role
    public CitizenEdit(String userRole) {
        this();
        this.userRole = userRole;
        if ("admin".equalsIgnoreCase(userRole)) {
            setCrudButtonsVisible(true);
        } else {
            setCrudButtonsVisible(false);
        }
    }

    private void setCrudButtonsVisible(boolean visible) {
        add.setVisible(visible);
        update.setVisible(visible);
        remove.setVisible(visible);
        save.setVisible(visible);
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

    // Method to initialize and layout custom components (dummy implementation for now)
    private void initializeCustomComponents() {
        // Initialize components
        citizenIdField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        dateOfBirthField = new JTextField();
        addressField = new JTextField();
        phoneField = new JTextField();
        fatherNameField = new JTextField();
        motherNameField = new JTextField();
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        genderButtonGroup = new ButtonGroup();
        genderButtonGroup.add(maleRadioButton);
        genderButtonGroup.add(femaleRadioButton);
        profileImageLabel = new JLabel();
        profileImageLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        // Add components to panel (placeholder layout, adjust as needed in .form layout)
        // You would typically use NetBeans GUI builder for this, or manually adjust layout managers
        // For now, let's add them to jPanel2 (your main content panel) with absolute positions for demonstration
        // You will need to manually adjust these in the design view or edit the generated code for a proper layout.
        jPanel2.add(new JLabel("ID:"));
        jPanel2.add(citizenIdField);
        jPanel2.add(new JLabel("Name:"));
        jPanel2.add(nameField);
        jPanel2.add(new JLabel("Email:"));
        jPanel2.add(emailField);
        jPanel2.add(new JLabel("DOB:"));
        jPanel2.add(dateOfBirthField);
        jPanel2.add(new JLabel("Address:"));
        jPanel2.add(addressField);
        jPanel2.add(new JLabel("Phone:"));
        jPanel2.add(phoneField);
        jPanel2.add(new JLabel("Father:"));
        jPanel2.add(fatherNameField);
        jPanel2.add(new JLabel("Mother:"));
        jPanel2.add(motherNameField);
        jPanel2.add(new JLabel("Gender:"));
        jPanel2.add(maleRadioButton);
        jPanel2.add(femaleRadioButton);
        jPanel2.add(new JLabel("Image:"));
        jPanel2.add(profileImageLabel);

        // Example absolute positioning (you will need to adjust these carefully)
        // These coordinates are just placeholders and will likely overlap or be incorrect
        // You should use the NetBeans GUI builder to arrange these visually.
        int yPos = 10; // Starting Y position for input fields
        int labelWidth = 100;
        int fieldWidth = 200;
        int rowHeight = 30;
        int spacing = 5;
        
        // Adjust position based on existing components in jPanel2, or add a dedicated panel for these fields.
        // For now, I'm placing them below the existing table and search area in jPanel2 for illustration.
        // This will likely require manual adjustment in the GUI builder.

        // Citizen ID
        jPanel2.add(new JLabel("Citizen-number:")).setBounds(10, yPos, labelWidth, rowHeight);
        citizenIdField.setBounds(10 + labelWidth + spacing, yPos, fieldWidth, rowHeight);
        jPanel2.add(citizenIdField);
        yPos += rowHeight + spacing;

        // Name
        jPanel2.add(new JLabel("Name:")).setBounds(10, yPos, labelWidth, rowHeight);
        nameField.setBounds(10 + labelWidth + spacing, yPos, fieldWidth, rowHeight);
        jPanel2.add(nameField);
        yPos += rowHeight + spacing;
        
        // Email
        jPanel2.add(new JLabel("Email:")).setBounds(10, yPos, labelWidth, rowHeight);
        emailField.setBounds(10 + labelWidth + spacing, yPos, fieldWidth, rowHeight);
        jPanel2.add(emailField);
        yPos += rowHeight + spacing;

        // Date of Birth
        jPanel2.add(new JLabel("Date of Birth:")).setBounds(10, yPos, labelWidth, rowHeight);
        dateOfBirthField.setBounds(10 + labelWidth + spacing, yPos, fieldWidth, rowHeight);
        jPanel2.add(dateOfBirthField);
        yPos += rowHeight + spacing;

        // Address
        jPanel2.add(new JLabel("Address:")).setBounds(10, yPos, labelWidth, rowHeight);
        addressField.setBounds(10 + labelWidth + spacing, yPos, fieldWidth, rowHeight);
        jPanel2.add(addressField);
        yPos += rowHeight + spacing;

        // Gender
        jPanel2.add(new JLabel("Gender:")).setBounds(10, yPos, labelWidth, rowHeight);
        maleRadioButton.setBounds(10 + labelWidth + spacing, yPos, 70, rowHeight);
        femaleRadioButton.setBounds(10 + labelWidth + spacing + 70, yPos, 80, rowHeight);
        jPanel2.add(maleRadioButton);
        jPanel2.add(femaleRadioButton);
        yPos += rowHeight + spacing;
        
        // Phone
        jPanel2.add(new JLabel("Phone:")).setBounds(10, yPos, labelWidth, rowHeight);
        phoneField.setBounds(10 + labelWidth + spacing, yPos, fieldWidth, rowHeight);
        jPanel2.add(phoneField);
        yPos += rowHeight + spacing;

        // Father's Name
        jPanel2.add(new JLabel("Father's Name:")).setBounds(10, yPos, labelWidth, rowHeight);
        fatherNameField.setBounds(10 + labelWidth + spacing, yPos, fieldWidth, rowHeight);
        jPanel2.add(fatherNameField);
        yPos += rowHeight + spacing;

        // Mother's Name
        jPanel2.add(new JLabel("Mother's Name:")).setBounds(10, yPos, labelWidth, rowHeight);
        motherNameField.setBounds(10 + labelWidth + spacing, yPos, fieldWidth, rowHeight);
        jPanel2.add(motherNameField);
        yPos += rowHeight + spacing;

        // Profile Image Label (adjust position and size as needed)
        jPanel2.add(new JLabel("Profile Image:")).setBounds(10, yPos, labelWidth, rowHeight);
        profileImageLabel.setBounds(10 + labelWidth + spacing, yPos, 90, 90); // Example size
        jPanel2.add(profileImageLabel);
        yPos += 90 + spacing;

        // Revalidate and repaint the panel to ensure new components are displayed
        jPanel2.revalidate();
        jPanel2.repaint();
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
        menu = new javax.swing.JButton();
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
        save = new javax.swing.JButton();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuActionPerformed(evt);
            }
        });
        getContentPane().add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 54, 55));

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
        CitizentoDashboard.setForeground(new java.awt.Color(255, 255, 255));
        CitizentoDashboard.setText("Back");
        CitizentoDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CitizentoDashboardActionPerformed(evt);
            }
        });

        update.setBackground(new java.awt.Color(102, 102, 255));
        update.setForeground(new java.awt.Color(255, 255, 255));
        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        remove.setBackground(new java.awt.Color(102, 102, 255));
        remove.setForeground(new java.awt.Color(255, 255, 255));
        remove.setText("Remove");
        remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeActionPerformed(evt);
            }
        });

        add.setBackground(new java.awt.Color(102, 102, 255));
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

        save.setBackground(new java.awt.Color(102, 102, 255));
        save.setForeground(new java.awt.Color(255, 255, 255));
        save.setText("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(CitizentoDashboard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(add)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remove)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(update)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(save)
                .addGap(10, 10, 10))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CitizentoDashboard)
                    .addComponent(update)
                    .addComponent(remove)
                    .addComponent(add)
                    .addComponent(save))
                .addContainerGap())
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 880, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        // TODO add your handling code here:
        CitizenEdit update = new CitizenEdit();
        update.setVisible(true);
    }//GEN-LAST:event_updateActionPerformed

    private void CitizentoDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CitizentoDashboardActionPerformed
        // TODO add your handling code here:
        DashboardUser goingtodash = new DashboardUser();
        goingtodash.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_CitizentoDashboardActionPerformed

    private void menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        // Get data from input fields
        CitizenData citizenToSave = getCitizenDataFromFields();

        // Determine if it's a create or update operation
        boolean success = false;
        if (citizenToSave.getCitizenId() == null || citizenToSave.getCitizenId().trim().isEmpty()) {
            // Citizen ID is empty, so it's a new citizen (CREATE operation)
            JOptionPane.showMessageDialog(this, "Citizen ID cannot be empty for saving.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            // Citizen ID is present, check if it exists in the database
            if (citizenController.citizenExists(citizenToSave.getCitizenId())) {
                // Citizen exists, so it's an UPDATE operation
                success = citizenController.updateCitizen(citizenToSave);
            } else {
                // Citizen does not exist, so it's a CREATE operation
                success = citizenController.createCitizen(citizenToSave);
            }
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Citizen saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadCitizenTableData(); // Refresh the table after saving
            clearInputFields(); // Clear fields after successful save
        } else {
            JOptionPane.showMessageDialog(this, "Failed to save citizen. Please check your inputs.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveActionPerformed

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
        // Clear all input fields to prepare for adding a new citizen
        clearInputFields();
        JOptionPane.showMessageDialog(this, "Form cleared. Enter details for a new citizen and click Save.", "Ready to Add", JOptionPane.INFORMATION_MESSAGE);
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
                new CitizenEdit().setVisible(true);
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
    private javax.swing.JButton menu;
    private javax.swing.JButton remove;
    private javax.swing.JButton save;
    private javax.swing.JTextField search;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}