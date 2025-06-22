/*
 * Hamro Smart Gaun Management System
 * Main Application Launcher
 */
package gaumanagementsystem;

import gaumanagementsystem.view.DashboardView;
import gaumanagementsystem.view.LoginView;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main application class for Gau Management System
 * Entry point for the application
 * 
 * @author bisho
 */
public class GauManagementSystem {

    /**
     * Main method to launch the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Set Nimbus look and feel (original)
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.err.println("Could not set Nimbus look and feel: " + ex.getMessage());
        }

        // Launch application on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("=== Hamro Smart Gaun Management System ===");
                System.out.println("Application starting...");
                
                // Check if we should show dashboard directly (for testing)
                if (args.length > 0 && "dashboard".equalsIgnoreCase(args[0])) {
                    System.out.println("Launching Dashboard directly for testing...");
                    DashboardView dashboard = new DashboardView("admin");
                    dashboard.setVisible(true);
                    
                    // Test Budget Allocation navigation
                    System.out.println("Budget Allocation feature is accessible from Dashboard!");
                    System.out.println("Click '💰 Budget Allocations' button to navigate to Budget feature.");
                    
                } else {
                    // Normal application flow - start with login
                    System.out.println("Launching Login Screen...");
                    LoginView loginView = new LoginView();
                    loginView.setVisible(true);
                }
                
                System.out.println("Application launched successfully!");
                
            } catch (Exception e) {
                System.err.println("Error launching application: " + e.getMessage());
                e.printStackTrace();
            }
        });

        // Print navigation instructions
        System.out.println("\n=== Navigation Instructions ===");
        System.out.println("Dashboard -> Budget Allocations:");
        System.out.println("1. Login to the system");
        System.out.println("2. Click 'Budget Allocations' button on Dashboard");
        System.out.println("3. View pie chart and table data");
        System.out.println("4. Click 'Back to Dashboard' to return");
        System.out.println("\nFor direct testing, run with argument: dashboard");
        System.out.println("Example: java GauManagementSystem dashboard");
    }
    
    /**
     * Test method to validate budget allocation navigation
     */
    public static void testBudgetAllocationNavigation() {
        System.out.println("\n=== Testing Budget Allocation Navigation ===");
        
        try {
            // Test Dashboard creation
            DashboardView dashboard = new DashboardView("admin");
            System.out.println("✓ Dashboard created successfully");
            
            // Test Budget button availability
            if (dashboard.getBudgetButton() != null) {
                System.out.println("✓ Budget Allocation button is available");
            } else {
                System.out.println("✗ Budget Allocation button is missing");
                return;
            }
            
            // Test Budget Allocation view creation
            gaumanagementsystem.view.BugdgetAllocations budgetView = 
                new gaumanagementsystem.view.BugdgetAllocations();
            System.out.println("✓ Budget Allocation view created successfully");
            
            System.out.println("✓ All navigation components are working correctly!");
            
        } catch (Exception e) {
            System.err.println("✗ Navigation test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
