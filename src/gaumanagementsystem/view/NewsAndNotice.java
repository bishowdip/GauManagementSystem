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
import javax.swing.JSpinner;

import java.awt.GridLayout;
import java.util.Calendar;
import javax.swing.SpinnerModel;

/**
 *
 * @author bishowdip
 */
public class NewsAndNotice extends javax.swing.JFrame {

    private final NewsAndNoticeController controller = new NewsAndNoticeController();
    private String currentTypeFilter = "";
    private JButton backButton;
    private String userRole = "admin"; // Store user role for navigation

    /**
     * Creates new form NewsAndNotice
     */
    public NewsAndNotice() {
        this("admin"); // Default to admin for backward compatibility
    }

    public NewsAndNotice(String userRole) {
        this.userRole = userRole; // Store the user role
        initComponents();
        setTitle("News and Notice - Hamro Smart Gaun");
        
        // Make window fully responsive
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // Start maximized
        setMinimumSize(new java.awt.Dimension(800, 600)); // Set minimum size
        setLocationRelativeTo(null); // Center the window
        
        // Make table responsive
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setFillsViewportHeight(true);
        
        // Create and style the back button BEFORE setting up layout
        backButton = new JButton("Back");
        backButton.setBackground(new Color(173, 216, 230));
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> {
            new DashboardView(userRole, null).setVisible(true);
            this.dispose();
        });
        
        // Set up a simple layout for panel2 since the original was removed
        setupPanel2Layout();
        
        // Ensure emoji is visible in header
        setupEmojiFont();
        
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

        // REFRESH button
        jButton4.addActionListener(e -> {
            loadTableData();
            JOptionPane.showMessageDialog(this, "Data refreshed from database!");
        });

        // Back button already created earlier

        // Set button colors for visibility
        Color lightBlue = new Color(173, 216, 230);
        jButton1.setBackground(lightBlue); jButton1.setForeground(Color.BLACK);
        jButton2.setBackground(lightBlue); jButton2.setForeground(Color.BLACK);
        jButton3.setBackground(lightBlue); jButton3.setForeground(Color.BLACK);
        jButton4.setBackground(lightBlue); jButton4.setForeground(Color.BLACK);

        // ADD button
        jButton1.addActionListener(e -> {
            // Create date field with calendar picker
            javax.swing.JTextField dateField = new javax.swing.JTextField();
            javax.swing.JButton calendarButton1 = new javax.swing.JButton("📅");
            javax.swing.JPanel datePanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateField.setText(sdf.format(new Date()));
            dateField.setPreferredSize(new java.awt.Dimension(120, 25));
            calendarButton1.setPreferredSize(new java.awt.Dimension(30, 25));
            datePanel.add(dateField);
            datePanel.add(calendarButton1);
            
            // Create expiry date field with calendar picker
            javax.swing.JTextField expiryDateField = new javax.swing.JTextField();
            javax.swing.JButton calendarButton2 = new javax.swing.JButton("📅");
            javax.swing.JPanel expiryDatePanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
            expiryDateField.setText(sdf.format(new Date()));
            expiryDateField.setPreferredSize(new java.awt.Dimension(120, 25));
            calendarButton2.setPreferredSize(new java.awt.Dimension(30, 25));
            expiryDatePanel.add(expiryDateField);
            expiryDatePanel.add(calendarButton2);
            
            // Calendar button actions
            calendarButton1.addActionListener(evt -> {
                Date selectedDate = showCalendarDialog(dateField.getText(), "Select Date");
                if (selectedDate != null) {
                    dateField.setText(sdf.format(selectedDate));
                }
            });
            
            calendarButton2.addActionListener(evt -> {
                Date selectedDate = showCalendarDialog(expiryDateField.getText(), "Select Expiry Date");
                if (selectedDate != null) {
                    expiryDateField.setText(sdf.format(selectedDate));
                }
            });
            
            // Create audience dropdown with predefined options
            String[] audiences = {
                "Local Citizens / Residents",
                "Ward Officials and Members", 
                "Local Businesses and Entrepreneurs",
                "Educational Institutions",
                "Health Post and Medical Staff",
                "Non-Governmental Organizations (NGOs) and Development Partners",
                "Government and Administrative Staff",
                "Students"
            };
            JComboBox<String> audienceCombo = new JComboBox<>(audiences);
            
            JTextField subjectField = new JTextField();
            JTextField descriptionField = new JTextField();
            String[] types = {"News", "Notice"};
            JComboBox<String> typeCombo = new JComboBox<>(types);

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Date:"));
            panel.add(datePanel);
            panel.add(new JLabel("Audience:"));
            panel.add(audienceCombo);
            panel.add(new JLabel("Subject:"));
            panel.add(subjectField);
            panel.add(new JLabel("Description:"));
            panel.add(descriptionField);
            panel.add(new JLabel("Expiry Date:"));
            panel.add(expiryDatePanel);
            panel.add(new JLabel("Type:"));
            panel.add(typeCombo);

            int result = JOptionPane.showConfirmDialog(this, panel, "Add News/Notice", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String date = dateField.getText();
                String audience = (String) audienceCombo.getSelectedItem();
                String subject = subjectField.getText();
                String description = descriptionField.getText();
                String expiryDate = expiryDateField.getText();
                String type = (String) typeCombo.getSelectedItem();
                if (date.isEmpty() || audience.isEmpty() || subject.isEmpty() || description.isEmpty() || expiryDate.isEmpty() || type.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required!");
                    return;
                }
                gaumanagementsystem.model.NewsAndNotice newNotice = new gaumanagementsystem.model.NewsAndNotice(date, audience, subject, description, expiryDate, type);
                boolean success = controller.add(newNotice);
                if (success) {
                    JOptionPane.showMessageDialog(this, "News/Notice added successfully!");
                    loadTableData(); // Refresh table from database
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add news/notice to database!", 
                                                "Database Error", JOptionPane.ERROR_MESSAGE);
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
                    // Get subject from selected row to find ID for deletion
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    String subject = (String) model.getValueAt(selectedRow, 2); // Subject is column 2
                    
                    // Get ID using the DAO helper method
                    gaumanagementsystem.dao.impl.NewsAndNoticeDAOImpl dao = new gaumanagementsystem.dao.impl.NewsAndNoticeDAOImpl();
                    int id = dao.getIdBySubject(subject);
                    
                    if (id != -1) {
                        boolean success = controller.delete(id);
                        if (success) {
                            JOptionPane.showMessageDialog(this, "News/Notice deleted successfully!");
                            loadTableData(); // Refresh table from database
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to delete news/notice!", 
                                                        "Database Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Could not find news/notice to delete!", 
                                                    "Error", JOptionPane.ERROR_MESSAGE);
                    }
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

                // Create audience dropdown with predefined options for update
                String[] audiences = {
                    "Local Citizens / Residents",
                    "Ward Officials and Members", 
                    "Local Businesses and Entrepreneurs",
                    "Educational Institutions",
                    "Health Post and Medical Staff",
                    "Non-Governmental Organizations (NGOs) and Development Partners",
                    "Government and Administrative Staff",
                    "Students"
                };
                JComboBox<String> audienceCombo = new JComboBox<>(audiences);
                audienceCombo.setSelectedItem(audience); // Set current value
                JTextField subjectField = new JTextField(subject);
                JTextField descriptionField = new JTextField(description);
                String[] types = {"News", "Notice"};
                JComboBox<String> typeCombo = new JComboBox<>(types);
                typeCombo.setSelectedItem(type);

                // Create date field with calendar picker for update
                javax.swing.JTextField dateFieldUpdate = new javax.swing.JTextField(date);
                javax.swing.JButton calendarButton1Update = new javax.swing.JButton("📅");
                javax.swing.JPanel datePanelUpdate = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
                dateFieldUpdate.setPreferredSize(new java.awt.Dimension(120, 25));
                calendarButton1Update.setPreferredSize(new java.awt.Dimension(30, 25));
                datePanelUpdate.add(dateFieldUpdate);
                datePanelUpdate.add(calendarButton1Update);
                
                // Create expiry date field with calendar picker for update
                javax.swing.JTextField expiryDateFieldUpdate = new javax.swing.JTextField(expiryDate);
                javax.swing.JButton calendarButton2Update = new javax.swing.JButton("📅");
                javax.swing.JPanel expiryDatePanelUpdate = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
                expiryDateFieldUpdate.setPreferredSize(new java.awt.Dimension(120, 25));
                calendarButton2Update.setPreferredSize(new java.awt.Dimension(30, 25));
                expiryDatePanelUpdate.add(expiryDateFieldUpdate);
                expiryDatePanelUpdate.add(calendarButton2Update);
                
                // Calendar button actions for update
                SimpleDateFormat sdfUpdate = new SimpleDateFormat("yyyy-MM-dd");
                calendarButton1Update.addActionListener(evt -> {
                    Date selectedDate = showCalendarDialog(dateFieldUpdate.getText(), "Select Date");
                    if (selectedDate != null) {
                        dateFieldUpdate.setText(sdfUpdate.format(selectedDate));
                    }
                });
                
                calendarButton2Update.addActionListener(evt -> {
                    Date selectedDate = showCalendarDialog(expiryDateFieldUpdate.getText(), "Select Expiry Date");
                    if (selectedDate != null) {
                        expiryDateFieldUpdate.setText(sdfUpdate.format(selectedDate));
                    }
                });

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(new JLabel("Date:"));
                panel.add(datePanelUpdate);
                panel.add(new JLabel("Audience:"));
                panel.add(audienceCombo);
                panel.add(new JLabel("Subject:"));
                panel.add(subjectField);
                panel.add(new JLabel("Description:"));
                panel.add(descriptionField);
                panel.add(new JLabel("Expiry Date:"));
                panel.add(expiryDatePanelUpdate);
                panel.add(new JLabel("Type:"));
                panel.add(typeCombo);

                int result = JOptionPane.showConfirmDialog(this, panel, "Update News/Notice", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String newDate = dateFieldUpdate.getText();
                    String newAudience = (String) audienceCombo.getSelectedItem();
                    String newSubject = subjectField.getText();
                    String newDescription = descriptionField.getText();
                    String newExpiryDate = expiryDateFieldUpdate.getText();
                    String newType = (String) typeCombo.getSelectedItem();
                    if (newDate.isEmpty() || newAudience.isEmpty() || newSubject.isEmpty() || newDescription.isEmpty() || newExpiryDate.isEmpty() || newType.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "All fields are required!");
                        return;
                    }
                    gaumanagementsystem.model.NewsAndNotice updatedNotice = new gaumanagementsystem.model.NewsAndNotice(newDate, newAudience, newSubject, newDescription, newExpiryDate, newType);
                    
                    // Get the original subject to find the database ID for update
                    String originalSubject = subject;
                    gaumanagementsystem.dao.impl.NewsAndNoticeDAOImpl dao = new gaumanagementsystem.dao.impl.NewsAndNoticeDAOImpl();
                    int id = dao.getIdBySubject(originalSubject);
                    
                    if (id != -1) {
                        boolean success = controller.update(id, updatedNotice);
                        if (success) {
                            JOptionPane.showMessageDialog(this, "News/Notice updated successfully!");
                            loadTableData(); // Refresh table from database
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to update news/notice in database!", 
                                                        "Database Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Could not find news/notice to update!", 
                                                    "Error", JOptionPane.ERROR_MESSAGE);
                    }
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

        // SEARCH filter (single search field)
        ActionListener filterListener = (ActionEvent e) -> {
            filterTable(getSearchText(), currentTypeFilter);
        };
        jTextField3.addActionListener(filterListener);
        jTextField3.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterTable(getSearchText(), currentTypeFilter); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterTable(getSearchText(), currentTypeFilter); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(getSearchText(), currentTypeFilter); }
        });
    }

    private void loadTableData() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        try {
            List<gaumanagementsystem.model.NewsAndNotice> allNews = controller.getAll();
            for (gaumanagementsystem.model.NewsAndNotice notice : allNews) {
                model.addRow(notice.toTableRow());
            }
            System.out.println("Successfully loaded " + allNews.size() + " news/notices from database.");
        } catch (Exception e) {
            System.err.println("Error loading news/notices from database: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error loading data from database: " + e.getMessage(), 
                                        "Database Error", JOptionPane.ERROR_MESSAGE);
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
        return jTextField3.getText().trim();
    }
    
    private void makeLayoutResponsive() {
        // Set the window to start maximized
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        
        // Create header panel with beautiful design
        javax.swing.JPanel headerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        headerPanel.setBackground(new java.awt.Color(153, 102, 255));
        headerPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        headerPanel.setPreferredSize(new java.awt.Dimension(1000, 80));
        
        // Center the main header title
        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 32));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("🏛️ Hamro Smart Gaun 🏛️");
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        headerPanel.add(jLabel1, java.awt.BorderLayout.CENTER);
        
        // Call the setup method for the rest of the layout
        setupPanel2Layout();
    }
    
    private void setupPanel2Layout() {
        // Set up a simple BorderLayout for panel2 to arrange components
        panel2.setLayout(new java.awt.BorderLayout());
        
        // Create beautiful header panel with our new design
        javax.swing.JPanel headerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        headerPanel.setBackground(new java.awt.Color(153, 102, 255));
        headerPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        headerPanel.setPreferredSize(new java.awt.Dimension(1000, 80));
        
        // Center the main header title
        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 32));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("🏛️ Hamro Smart Gaun 🏛️");
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        headerPanel.add(jLabel1, java.awt.BorderLayout.CENTER);
        
        // Create control panel
        javax.swing.JPanel controlPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        controlPanel.setBackground(new java.awt.Color(153, 102, 255));
        controlPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Left side - News and Notice title
        javax.swing.JPanel titlePanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        titlePanel.setBackground(new java.awt.Color(153, 102, 255));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 24));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        titlePanel.add(jLabel4); // "News and Notice"
        
        // Search section - positioned at center
        javax.swing.JPanel searchPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));
        searchPanel.setBackground(new java.awt.Color(153, 102, 255));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Arial", 1, 14));
        searchPanel.add(jLabel5); // Search
        searchPanel.add(javax.swing.Box.createHorizontalStrut(10)); // Gap between label and field
        
        // Make search field larger and responsive
        jTextField3.setPreferredSize(new java.awt.Dimension(250, 25));
        jTextField3.setMinimumSize(new java.awt.Dimension(200, 25));
        searchPanel.add(jTextField3);
        
        // Right side - filter labels and add button
        javax.swing.JPanel rightControlPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        rightControlPanel.setBackground(new java.awt.Color(153, 102, 255));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        rightControlPanel.add(jLabel2); // News
        rightControlPanel.add(jLabel3); // Notices
        rightControlPanel.add(jButton1); // ADD
        
        controlPanel.add(titlePanel, java.awt.BorderLayout.WEST);
        controlPanel.add(searchPanel, java.awt.BorderLayout.CENTER);
        controlPanel.add(rightControlPanel, java.awt.BorderLayout.EAST);
        
        // Create main content panel
        javax.swing.JPanel mainPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        mainPanel.setBackground(new java.awt.Color(240, 240, 240));
        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        // Add table (this will now resize with window)
        mainPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        
        // Create button panel
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout());
        buttonPanel.setBackground(new java.awt.Color(204, 204, 255));
        buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 10, 10));
        buttonPanel.add(jButton2); // DELETE
        buttonPanel.add(jButton3); // UPDATE
        buttonPanel.add(jButton4); // REFRESH
        buttonPanel.add(javax.swing.Box.createHorizontalStrut(20));
        buttonPanel.add(backButton); // Back
        
        // Assemble the layout
        panel2.add(headerPanel, java.awt.BorderLayout.NORTH);
        
        // Create center panel to hold controls and table
        javax.swing.JPanel centerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        centerPanel.setBackground(new java.awt.Color(240, 240, 240));
        centerPanel.add(controlPanel, java.awt.BorderLayout.NORTH);
        centerPanel.add(mainPanel, java.awt.BorderLayout.CENTER);
        
        panel2.add(centerPanel, java.awt.BorderLayout.CENTER);
        panel2.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        // Refresh the layout
        panel2.revalidate();
        panel2.repaint();
    }
    
    private void setupEmojiFont() {
        // Multiple approaches to ensure emoji visibility in all instances
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

    private Date showCalendarDialog(String currentDate, String title) {
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
        javax.swing.JDialog calendarDialog = new javax.swing.JDialog(this, title, true);
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

        panel2.setBackground(new java.awt.Color(153, 102, 255));
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
