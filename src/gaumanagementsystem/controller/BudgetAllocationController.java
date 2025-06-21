/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

import gaumanagementsystem.dao.BudgetAllocationDao;
import gaumanagementsystem.model.BudgetAllocation;
import gaumanagementsystem.view.BugdgetAllocations;
import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;

/**
 * Controller for Budget Allocation functionality
 * Handles business logic and coordinates between View and DAO
 * @author bisho
 */
public class BudgetAllocationController {
    
    private BugdgetAllocations view;
    private BudgetAllocationDao dao;
    private List<BudgetAllocation> budgetData;
    
    /**
     * Constructor
     * @param view The budget allocation view
     */
    public BudgetAllocationController(BugdgetAllocations view) {
        this.view = view;
        this.dao = new BudgetAllocationDao();
        this.budgetData = new ArrayList<>();
        initController();
    }
    
    /**
     * Initialize controller and set up event handlers
     */
    private void initController() {
        loadBudgetData();
        view.updateDisplay(budgetData);
    }
    
    /**
     * Load budget data from database
     * Handles errors and provides fallback data
     */
    public void loadBudgetData() {
        try {
            // Validate database table structure first
            if (!dao.validateProjectsTable()) {
                System.out.println("Projects table validation failed. Using sample data.");
                loadSampleData();
                return;
            }
            
            // Load actual data from database
            budgetData = dao.getBudgetAllocationsByCategory();
            
            // If no data from database, load sample data for demonstration
            if (budgetData == null || budgetData.isEmpty()) {
                System.out.println("No data found in database. Loading sample data for demonstration.");
                loadSampleData();
            } else {
                System.out.println("Successfully loaded " + budgetData.size() + " budget categories from database.");
            }
            
        } catch (Exception e) {
            System.err.println("Error loading budget data: " + e.getMessage());
            e.printStackTrace();
            
            // Show error message to user
            JOptionPane.showMessageDialog(view, 
                "Unable to load data from database. Showing sample data.\nError: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.WARNING_MESSAGE);
            
            // Load sample data as fallback
            loadSampleData();
        }
    }
    
    /**
     * Load sample data for demonstration
     */
    private void loadSampleData() {
        budgetData = new ArrayList<>();
        
        // Sample budget allocations by category for demonstration
        budgetData.add(new BudgetAllocation("Education", 3700000.0, 6));
        budgetData.add(new BudgetAllocation("Health and Medical", 2700000.0, 4));
        budgetData.add(new BudgetAllocation("Housing and Rent", 3200000.0, 4));
        budgetData.add(new BudgetAllocation("Transportation", 3700000.0, 3));
        budgetData.add(new BudgetAllocation("Food and Groceries", 800000.0, 1));
        budgetData.add(new BudgetAllocation("Savings and Investments", 1800000.0, 2));
        budgetData.add(new BudgetAllocation("Entertainment and Leisure", 2000000.0, 1));
        
        System.out.println("Sample data loaded with " + budgetData.size() + " categories.");
    }
    
    /**
     * Refresh budget data and update view
     */
    public void refreshData() {
        try {
            loadBudgetData();
            view.updateDisplay(budgetData);
            
            // Show success message
            JOptionPane.showMessageDialog(view, 
                "Budget data refreshed successfully!\nLoaded " + budgetData.size() + " categories.", 
                "Data Refreshed", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            System.err.println("Error refreshing data: " + e.getMessage());
            JOptionPane.showMessageDialog(view, 
                "Error refreshing data: " + e.getMessage(), 
                "Refresh Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Get current budget data
     * @return List of budget allocations
     */
    public List<BudgetAllocation> getBudgetData() {
        return new ArrayList<>(budgetData); // Return copy to prevent external modification
    }
    
    /**
     * Get total budget amount across all categories
     * @return Total budget amount
     */
    public double getTotalBudgetAmount() {
        return budgetData.stream()
                .mapToDouble(BudgetAllocation::getTotalAmount)
                .sum();
    }
    
    /**
     * Get total number of projects across all categories
     * @return Total project count
     */
    public int getTotalProjectCount() {
        return budgetData.stream()
                .mapToInt(BudgetAllocation::getProjectCount)
                .sum();
    }
    
    /**
     * Get budget allocation for a specific category
     * @param category Category name
     * @return BudgetAllocation object or null if not found
     */
    public BudgetAllocation getBudgetAllocationByCategory(String category) {
        try {
            return dao.getBudgetAllocationByCategory(category);
        } catch (Exception e) {
            System.err.println("Error getting budget allocation for category " + category + ": " + e.getMessage());
            
            // Fallback to local data
            return budgetData.stream()
                    .filter(allocation -> allocation.getCategory().equalsIgnoreCase(category))
                    .findFirst()
                    .orElse(null);
        }
    }
    
    /**
     * Get budget allocations grouped by ward
     * @return List of budget allocations by ward
     */
    public List<BudgetAllocation> getBudgetAllocationsByWard() {
        try {
            List<BudgetAllocation> wardAllocations = dao.getBudgetAllocationsByWard();
            
            if (wardAllocations == null || wardAllocations.isEmpty()) {
                // Return sample ward data if no database data
                return getSampleWardData();
            }
            
            return wardAllocations;
            
        } catch (Exception e) {
            System.err.println("Error getting budget allocations by ward: " + e.getMessage());
            return getSampleWardData();
        }
    }
    
    /**
     * Get sample ward allocation data
     * @return List of sample ward allocations
     */
    private List<BudgetAllocation> getSampleWardData() {
        List<BudgetAllocation> wardData = new ArrayList<>();
        wardData.add(new BudgetAllocation("Ward 1", 2500000.0, 3));
        wardData.add(new BudgetAllocation("Ward 2", 3200000.0, 4));
        wardData.add(new BudgetAllocation("Ward 3", 1800000.0, 2));
        wardData.add(new BudgetAllocation("Ward 4", 2800000.0, 3));
        wardData.add(new BudgetAllocation("Ward 5", 1900000.0, 2));
        return wardData;
    }
    
    /**
     * Handle navigation back to dashboard
     */
    public void navigateToDashboard() {
        try {
            gaumanagementsystem.view.DashboardView dashboard = new gaumanagementsystem.view.DashboardView();
            dashboard.setVisible(true);
            view.dispose();
        } catch (Exception e) {
            System.err.println("Error navigating to dashboard: " + e.getMessage());
            JOptionPane.showMessageDialog(view, 
                "Error navigating to dashboard: " + e.getMessage(), 
                "Navigation Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Validate if database connection is available
     * @return true if database is accessible
     */
    public boolean isDatabaseAvailable() {
        try {
            return dao.validateProjectsTable();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get budget statistics summary
     * @return Array containing [totalBudget, totalProjects, categoriesCount]
     */
    public double[] getBudgetStatistics() {
        double totalBudget = getTotalBudgetAmount();
        int totalProjects = getTotalProjectCount();
        int categoriesCount = budgetData.size();
        
        return new double[]{totalBudget, totalProjects, categoriesCount};
    }
    
    /**
     * Export budget data to a formatted string (for future export functionality)
     * @return Formatted budget data string
     */
    public String exportBudgetDataAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BUDGET ALLOCATION REPORT\n");
        sb.append("========================\n\n");
        
        double totalBudget = getTotalBudgetAmount();
        int totalProjects = getTotalProjectCount();
        
        sb.append(String.format("Total Budget: Rs. %.2f\n", totalBudget));
        sb.append(String.format("Total Projects: %d\n", totalProjects));
        sb.append(String.format("Categories: %d\n\n", budgetData.size()));
        
        sb.append("CATEGORY BREAKDOWN:\n");
        sb.append("-------------------\n");
        
        for (BudgetAllocation allocation : budgetData) {
            double percentage = (allocation.getTotalAmount() / totalBudget) * 100;
            sb.append(String.format("%-25s: Rs. %10.2f (%5.1f%%) - %d projects\n", 
                allocation.getCategory(), 
                allocation.getTotalAmount(), 
                percentage,
                allocation.getProjectCount()));
        }
        
        return sb.toString();
    }
} 