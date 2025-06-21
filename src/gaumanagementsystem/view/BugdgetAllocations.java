/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gaumanagementsystem.view;

import gaumanagementsystem.model.BudgetAllocation;
import gaumanagementsystem.controller.BudgetAllocationController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Budget Allocations View - Pure UI Component
 * Handles only UI display and user interactions
 * Business logic is handled by BudgetAllocationController
 * @author bisho
 */
public class BugdgetAllocations extends javax.swing.JFrame {
    
    // UI Components
    private JTable budgetTable;
    private JPanel chartPanel;
    private JPanel tablePanel;
    private JButton refreshButton;
    private JButton backButton;
    private DefaultTableModel tableModel;
    
    // Controller reference
    private BudgetAllocationController controller;
    
    // Current data for display
    private List<BudgetAllocation> currentBudgetData;

    /**
     * Creates new form BugdgetAllocations
     */
    public BugdgetAllocations() {
        currentBudgetData = new ArrayList<>();
        // Don't call initComponents() as it overrides our custom layout
        initCustomComponents();
        
        // Initialize controller after view setup
        controller = new BudgetAllocationController(this);
    }
    
    /**
     * Initialize custom UI components
     * This method handles all UI setup without business logic
     */
    private void initCustomComponents() {
        // Window setup
        setTitle("Budget Allocations - Hamro Smart Gaun");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start maximized
        setMinimumSize(new Dimension(800, 600)); // Set minimum size
        setLocationRelativeTo(null);
        
        // Create main panel with BorderLayout
        setLayout(new BorderLayout());
        
        // Create header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Create main content panel
        JPanel mainPanel = createMainContentPanel();
        add(mainPanel, BorderLayout.CENTER);
        
        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Create header panel with title
     * @return Header panel
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(153, 153, 255));
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        
        JLabel titleLabel = new JLabel("Budget Allocations Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(titleLabel);
        return headerPanel;
    }
    
    /**
     * Create main content panel with chart and table
     * @return Main content panel
     */
    private JPanel createMainContentPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Chart Panel
        chartPanel = createChartPanel();
        mainPanel.add(chartPanel);
        
        // Table Panel
        tablePanel = createTablePanel();
        mainPanel.add(tablePanel);
        
        return mainPanel;
    }
    
    /**
     * Create chart panel for pie chart display
     * @return Chart panel
     */
    private JPanel createChartPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Budget Distribution by Category"));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        
        // Initially show loading message
        JLabel loadingLabel = new JLabel("Loading chart...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(loadingLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Create table panel for data display
     * @return Table panel
     */
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Budget Allocation Details"));
        
        // Create table with proper model
        String[] columnNames = {"Category", "Total Amount (Rs.)", "Project Count"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        budgetTable = new JTable(tableModel);
        configureTable();
        
        JScrollPane scrollPane = new JScrollPane(budgetTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Configure table appearance and behavior
     */
    private void configureTable() {
        budgetTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        budgetTable.getTableHeader().setReorderingAllowed(false);
        
        // Style the table
        budgetTable.setBackground(new Color(240, 248, 255));
        budgetTable.setGridColor(new Color(173, 216, 230));
        budgetTable.setSelectionBackground(new Color(135, 206, 235));
        budgetTable.setRowHeight(25);
        
        // Set column widths
        budgetTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        budgetTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        budgetTable.getColumnModel().getColumn(2).setPreferredWidth(100);
    }
    
    /**
     * Create button panel with action buttons
     * @return Button panel
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(204, 204, 255));
        
        // Refresh button
        refreshButton = createStyledButton("Refresh Data");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRefreshAction();
            }
        });
        
        // Back button
        backButton = createStyledButton("Back to Dashboard");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBackAction();
            }
        });
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); // Add spacing
        buttonPanel.add(backButton);
        
        return buttonPanel;
    }
    
    /**
     * Create styled button with consistent appearance
     * @param text Button text
     * @return Styled JButton
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(173, 216, 230));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(150, 35));
        return button;
    }
    
    /**
     * Handle refresh button action
     * Delegates to controller
     */
    private void handleRefreshAction() {
        if (controller != null) {
            controller.refreshData();
        }
    }
    
    /**
     * Handle back button action
     * Delegates to controller
     */
    private void handleBackAction() {
        if (controller != null) {
            controller.navigateToDashboard();
        }
    }
    
    /**
     * Update the display with new budget data
     * Called by controller when data changes
     * @param budgetData New budget data to display
     */
    public void updateDisplay(List<BudgetAllocation> budgetData) {
        this.currentBudgetData = new ArrayList<>(budgetData);
        updateTableDisplay();
        updateChartDisplay();
    }
    
    /**
     * Update table with current budget data
     */
    private void updateTableDisplay() {
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Add budget data to table
        double totalBudget = 0;
        for (BudgetAllocation allocation : currentBudgetData) {
            Object[] row = {
                allocation.getCategory(),
                String.format("%.2f", allocation.getTotalAmount()),
                allocation.getProjectCount()
            };
            tableModel.addRow(row);
            totalBudget += allocation.getTotalAmount();
        }
        
        // Add total row
        if (!currentBudgetData.isEmpty()) {
            tableModel.addRow(new Object[]{"TOTAL", String.format("%.2f", totalBudget), "-"});
        }
        
        // Refresh table display
        budgetTable.revalidate();
        budgetTable.repaint();
    }
    
    /**
     * Update pie chart with current budget data
     */
    private void updateChartDisplay() {
        chartPanel.removeAll();
        
        if (currentBudgetData.isEmpty()) {
            // Show no data message
            JLabel noDataLabel = new JLabel("No budget data available", SwingConstants.CENTER);
            noDataLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            chartPanel.add(noDataLabel, BorderLayout.CENTER);
        } else {
            // Create and add pie chart
            PieChartPanel pieChart = new PieChartPanel(currentBudgetData);
            chartPanel.add(pieChart, BorderLayout.CENTER);
            
            // Add legend
            JPanel legendPanel = createLegend();
            chartPanel.add(legendPanel, BorderLayout.SOUTH);
        }
        
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    
    /**
     * Create legend panel for pie chart
     * @return Legend panel
     */
    private JPanel createLegend() {
        JPanel legendPanel = new JPanel(new GridLayout(0, 2, 5, 2));
        legendPanel.setBorder(BorderFactory.createTitledBorder("Legend"));
        
        Color[] colors = getChartColors();
        
        for (int i = 0; i < currentBudgetData.size() && i < colors.length; i++) {
            BudgetAllocation allocation = currentBudgetData.get(i);
            
            // Create color box
            JPanel colorBox = new JPanel();
            colorBox.setBackground(colors[i]);
            colorBox.setPreferredSize(new Dimension(20, 20));
            colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            // Create label
            JLabel label = new JLabel(allocation.getCategory());
            label.setFont(new Font("Arial", Font.PLAIN, 12));
            
            // Create legend item
            JPanel legendItem = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            legendItem.add(colorBox);
            legendItem.add(label);
            
            legendPanel.add(legendItem);
        }
        
        return legendPanel;
    }
    
    /**
     * Get colors for chart segments
     * @return Array of colors
     */
    private Color[] getChartColors() {
        return new Color[]{
            Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, 
            Color.MAGENTA, Color.CYAN, Color.PINK, Color.YELLOW,
            Color.LIGHT_GRAY, Color.DARK_GRAY
        };
    }
    
    /**
     * Custom Pie Chart Panel for rendering pie chart
     */
    private class PieChartPanel extends JPanel {
        private List<BudgetAllocation> data;
        private double totalAmount;
        
        public PieChartPanel(List<BudgetAllocation> data) {
            this.data = new ArrayList<>(data);
            this.totalAmount = data.stream().mapToDouble(BudgetAllocation::getTotalAmount).sum();
            setPreferredSize(new Dimension(400, 300));
            setBackground(Color.WHITE);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if (data.isEmpty() || totalAmount <= 0) {
                return;
            }
            
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Calculate chart dimensions
            int width = getWidth();
            int height = getHeight();
            int diameter = Math.min(width, height) - 50;
            int x = (width - diameter) / 2;
            int y = (height - diameter) / 2;
            
            Color[] colors = getChartColors();
            double currentAngle = 0;
            
            // Draw pie chart segments
            for (int i = 0; i < data.size(); i++) {
                BudgetAllocation allocation = data.get(i);
                double percentage = allocation.getTotalAmount() / totalAmount;
                double arcAngle = percentage * 360;
                
                // Fill segment
                g2d.setColor(colors[i % colors.length]);
                g2d.fillArc(x, y, diameter, diameter, (int) currentAngle, (int) arcAngle);
                
                // Draw percentage label
                if (percentage > 0.05) { // Only show label if segment is large enough
                    drawPercentageLabel(g2d, x, y, diameter, currentAngle, arcAngle, percentage);
                }
                
                currentAngle += arcAngle;
            }
            
            // Draw border
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(x, y, diameter, diameter);
        }
        
        /**
         * Draw percentage label on pie chart segment
         */
        private void drawPercentageLabel(Graphics2D g2d, int x, int y, int diameter, 
                                       double startAngle, double arcAngle, double percentage) {
            double labelAngle = Math.toRadians(startAngle + arcAngle / 2);
            int labelX = (int) (x + diameter/2 + Math.cos(labelAngle) * diameter/3);
            int labelY = (int) (y + diameter/2 - Math.sin(labelAngle) * diameter/3);
            
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 11));
            
            String percentText = String.format("%.1f%%", percentage * 100);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(percentText);
            int textHeight = fm.getHeight();
            
            // Draw background for better readability
            g2d.setColor(Color.BLACK);
            g2d.fillRect(labelX - textWidth/2 - 2, labelY - textHeight/2 - 2, 
                        textWidth + 4, textHeight);
            
            // Draw text
            g2d.setColor(Color.WHITE);
            g2d.drawString(percentText, labelX - textWidth/2, labelY + fm.getAscent()/2);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 703, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 459, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(BugdgetAllocations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BugdgetAllocations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BugdgetAllocations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BugdgetAllocations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BugdgetAllocations().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
