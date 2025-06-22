package gaumanagementsystem.view;

import javax.swing.*;
import java.awt.*;

/**
 * Simplified version of ProjectRequests for testing table selection fix
 */
public class ProjectRequestsSimple extends JFrame {

    private JTable jTable1;
    private JScrollPane jScrollPane1;

    public ProjectRequestsSimple() {
        setTitle("Project Requests - Table Selection Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        initComponents();
        populateTestData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(153, 102, 255));
        headerPanel.setPreferredSize(new Dimension(800, 80));
        JLabel titleLabel = new JLabel("🏛️ Hamro Smart Gaun 🏛️");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel projectLabel = new JLabel("Project Requests - Table Selection Test");
        projectLabel.setFont(new Font("Arial", Font.BOLD, 18));
        projectLabel.setForeground(new Color(102, 51, 255));
        mainPanel.add(projectLabel, BorderLayout.NORTH);
        
        // Create table with the same settings as the original
        jTable1 = new JTable();
        jTable1.setBackground(new Color(204, 204, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Request_ID", "Projects_Name", "Started_Date", "Ward", "Category", "Expected to End", "Description", "Status", "Amount"
            }
        ));
        jTable1.setGridColor(new Color(102, 51, 255));
        
        // THE FIX: Changed from white to light blue selection background
        jTable1.setSelectionBackground(new Color(173, 216, 230)); // Light blue selection
        jTable1.setSelectionForeground(new Color(0, 0, 0)); // Black text for selected rows
        jTable1.setShowGrid(true);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setFillsViewportHeight(true);
        
        jScrollPane1 = new JScrollPane(jTable1);
        mainPanel.add(jScrollPane1, BorderLayout.CENTER);
        
        // Instructions
        JPanel instructionPanel = new JPanel();
        instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
        instructionPanel.setBorder(BorderFactory.createTitledBorder("Test Instructions"));
        
        JLabel instr1 = new JLabel("1. Click on any row in the table above");
        JLabel instr2 = new JLabel("2. The selected row should show LIGHT BLUE background (not white)");
        JLabel instr3 = new JLabel("3. Row data should remain VISIBLE when selected");
        JLabel instr4 = new JLabel("4. If you see white overlay hiding the data, the fix didn't work");
        
        instr1.setFont(new Font("Arial", Font.PLAIN, 12));
        instr2.setFont(new Font("Arial", Font.BOLD, 12));
        instr2.setForeground(new Color(0, 100, 0));
        instr3.setFont(new Font("Arial", Font.BOLD, 12));
        instr3.setForeground(new Color(0, 100, 0));
        instr4.setFont(new Font("Arial", Font.PLAIN, 12));
        instr4.setForeground(Color.RED);
        
        instructionPanel.add(instr1);
        instructionPanel.add(instr2);
        instructionPanel.add(instr3);
        instructionPanel.add(instr4);
        
        add(mainPanel, BorderLayout.CENTER);
        add(instructionPanel, BorderLayout.SOUTH);
    }
    
    private void populateTestData() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        
        // Add sample data to test the selection
        Object[][] testData = {
            {1, "Road Construction", "2024-01-15", "Ward 1", "Infrastructure", "2024-06-15", "Building new road connecting villages", "In Progress", 500000},
            {2, "School Building", "2024-02-01", "Ward 2", "Education", "2024-12-31", "Constructing new primary school", "Planned", 800000},
            {3, "Water Supply", "2024-01-10", "Ward 3", "Utilities", "2024-05-30", "Installing water supply system", "Completed", 300000},
            {4, "Health Center", "2024-03-01", "Ward 4", "Healthcare", "2024-10-15", "Building community health center", "Pending", 600000},
            {5, "Bridge Construction", "2024-02-15", "Ward 5", "Infrastructure", "2024-08-30", "Building bridge over river", "In Progress", 400000}
        };
        
        for (Object[] row : testData) {
            model.addRow(row);
        }
    }

    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new ProjectRequestsSimple().setVisible(true);
        });
    }
} 