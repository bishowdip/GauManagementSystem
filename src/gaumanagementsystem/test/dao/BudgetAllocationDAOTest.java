package gaumanagementsystem.test.dao;

import gaumanagementsystem.dao.BudgetAllocationDao;
import gaumanagementsystem.model.BudgetAllocation;
import gaumanagementsystem.database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Unit tests for BudgetAllocationDao
 * Tests all CRUD operations and business logic methods
 * 
 * @author Test Suite
 */
public class BudgetAllocationDAOTest {
    
    private BudgetAllocationDao budgetAllocationDAO;
    private static final String TEST_CATEGORY = "Test Category";
    private static final BigDecimal TEST_ALLOCATED_AMOUNT = new BigDecimal("50000.00");
    private static final BigDecimal TEST_SPENT_AMOUNT = new BigDecimal("25000.00");
    private static final int TEST_FISCAL_YEAR = 2024;
    private static final String TEST_DESCRIPTION = "Test budget allocation for testing purposes";
    
    public BudgetAllocationDAOTest() {
        this.budgetAllocationDAO = new BudgetAllocationDao();
    }
    
    /**
     * Run all tests
     */
    public static void main(String[] args) {
        BudgetAllocationDAOTest test = new BudgetAllocationDAOTest();
        
        System.out.println("=== BudgetAllocationDAO Test Suite ===");
        System.out.println("Starting BudgetAllocationDAO tests...\n");
        
        try {
            // Setup test environment
            test.setupTestEnvironment();
            
            // Run all tests
            test.testCreateBudgetAllocation();
            test.testGetAllBudgetAllocations();
            test.testGetBudgetAllocationsByCategory();
            test.testGetBudgetAllocationsByFiscalYear();
            test.testUpdateBudgetAllocation();
            test.testUpdateAllocatedAmount();
            test.testUpdateSpentAmount();
            test.testGetTotalAllocatedBudget();
            test.testGetTotalSpentBudget();
            test.testGetRemainingBudget();
            test.testGetBudgetUtilizationPercentage();
            test.testDeleteBudgetAllocation();
            
            // Cleanup
            test.cleanupTestData();
            
            System.out.println("\n=== All BudgetAllocationDAO Tests Completed ===");
            
        } catch (Exception e) {
            System.err.println("Test execution failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Setup test environment and clean any existing test data
     */
    private void setupTestEnvironment() {
        System.out.println("Setting up test environment...");
        cleanupTestData(); // Clean any existing test data
        System.out.println("✓ Test environment ready\n");
    }
    
    /**
     * Test budget allocation creation
     */
    private void testCreateBudgetAllocation() {
        System.out.println("Testing createBudgetAllocation()...");
        
        BudgetAllocation testBudget = new BudgetAllocation();
        testBudget.setCategory(TEST_CATEGORY);
        testBudget.setAllocatedAmount(TEST_ALLOCATED_AMOUNT);
        testBudget.setSpentAmount(TEST_SPENT_AMOUNT);
        testBudget.setFiscalYear(TEST_FISCAL_YEAR);
        testBudget.setDescription(TEST_DESCRIPTION);
        
        boolean result = budgetAllocationDAO.createBudgetAllocation(testBudget);
        
        if (result) {
            System.out.println("✓ Budget allocation creation test passed");
        } else {
            System.out.println("✗ Budget allocation creation test failed");
        }
    }
    
    /**
     * Test getting all budget allocations
     */
    private void testGetAllBudgetAllocations() {
        System.out.println("Testing getAllBudgetAllocations()...");
        
        List<BudgetAllocation> allBudgets = budgetAllocationDAO.getAllBudgetAllocations();
        
        if (allBudgets != null && allBudgets.size() > 0) {
            System.out.println("✓ Get all budget allocations test passed - Found " + allBudgets.size() + " allocations");
        } else {
            System.out.println("✗ Get all budget allocations test failed");
        }
    }
    
    /**
     * Test getting budget allocations by category
     */
    private void testGetBudgetAllocationsByCategory() {
        System.out.println("Testing getBudgetAllocationsByCategory()...");
        
        List<BudgetAllocation> budgetsByCategory = budgetAllocationDAO.getBudgetAllocationsByCategory(TEST_CATEGORY);
        
        if (budgetsByCategory != null && budgetsByCategory.size() > 0) {
            System.out.println("✓ Get budget allocations by category test passed - Found " + budgetsByCategory.size() + " allocations in category " + TEST_CATEGORY);
        } else {
            System.out.println("✗ Get budget allocations by category test failed");
        }
    }
    
    /**
     * Test getting budget allocations by fiscal year
     */
    private void testGetBudgetAllocationsByFiscalYear() {
        System.out.println("Testing getBudgetAllocationsByFiscalYear()...");
        
        List<BudgetAllocation> budgetsByYear = budgetAllocationDAO.getBudgetAllocationsByFiscalYear(TEST_FISCAL_YEAR);
        
        if (budgetsByYear != null && budgetsByYear.size() > 0) {
            System.out.println("✓ Get budget allocations by fiscal year test passed - Found " + budgetsByYear.size() + " allocations for year " + TEST_FISCAL_YEAR);
        } else {
            System.out.println("✗ Get budget allocations by fiscal year test failed");
        }
    }
    
    /**
     * Test updating budget allocation information
     */
    private void testUpdateBudgetAllocation() {
        System.out.println("Testing updateBudgetAllocation()...");
        
        List<BudgetAllocation> budgets = budgetAllocationDAO.getAllBudgetAllocations();
        BudgetAllocation testBudget = null;
        
        for (BudgetAllocation budget : budgets) {
            if (TEST_CATEGORY.equals(budget.getCategory())) {
                testBudget = budget;
                break;
            }
        }
        
        if (testBudget != null) {
            String originalDescription = testBudget.getDescription();
            testBudget.setDescription("Updated description for testing");
            
            boolean result = budgetAllocationDAO.updateBudgetAllocation(testBudget);
            
            if (result) {
                System.out.println("✓ Update budget allocation test passed");
                
                // Restore original description
                testBudget.setDescription(originalDescription);
                budgetAllocationDAO.updateBudgetAllocation(testBudget);
            } else {
                System.out.println("✗ Update budget allocation test failed");
            }
        } else {
            System.out.println("✗ Update budget allocation test failed - no test budget available");
        }
    }
    
    /**
     * Test updating allocated amount
     */
    private void testUpdateAllocatedAmount() {
        System.out.println("Testing updateAllocatedAmount()...");
        
        List<BudgetAllocation> budgets = budgetAllocationDAO.getAllBudgetAllocations();
        BudgetAllocation testBudget = null;
        
        for (BudgetAllocation budget : budgets) {
            if (TEST_CATEGORY.equals(budget.getCategory())) {
                testBudget = budget;
                break;
            }
        }
        
        if (testBudget != null) {
            BigDecimal originalAmount = testBudget.getAllocatedAmount();
            BigDecimal newAmount = new BigDecimal("75000.00");
            
            boolean result = budgetAllocationDAO.updateAllocatedAmount(testBudget.getId(), newAmount);
            
            if (result) {
                System.out.println("✓ Update allocated amount test passed");
                
                // Restore original amount
                budgetAllocationDAO.updateAllocatedAmount(testBudget.getId(), originalAmount);
            } else {
                System.out.println("✗ Update allocated amount test failed");
            }
        } else {
            System.out.println("✗ Update allocated amount test failed - no test budget available");
        }
    }
    
    /**
     * Test updating spent amount
     */
    private void testUpdateSpentAmount() {
        System.out.println("Testing updateSpentAmount()...");
        
        List<BudgetAllocation> budgets = budgetAllocationDAO.getAllBudgetAllocations();
        BudgetAllocation testBudget = null;
        
        for (BudgetAllocation budget : budgets) {
            if (TEST_CATEGORY.equals(budget.getCategory())) {
                testBudget = budget;
                break;
            }
        }
        
        if (testBudget != null) {
            BigDecimal originalAmount = testBudget.getSpentAmount();
            BigDecimal newAmount = new BigDecimal("35000.00");
            
            boolean result = budgetAllocationDAO.updateSpentAmount(testBudget.getId(), newAmount);
            
            if (result) {
                System.out.println("✓ Update spent amount test passed");
                
                // Restore original amount
                budgetAllocationDAO.updateSpentAmount(testBudget.getId(), originalAmount);
            } else {
                System.out.println("✗ Update spent amount test failed");
            }
        } else {
            System.out.println("✗ Update spent amount test failed - no test budget available");
        }
    }
    
    /**
     * Test getting total allocated budget
     */
    private void testGetTotalAllocatedBudget() {
        System.out.println("Testing getTotalAllocatedBudget()...");
        
        BigDecimal totalAllocated = budgetAllocationDAO.getTotalAllocatedBudget();
        
        if (totalAllocated != null && totalAllocated.compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("✓ Get total allocated budget test passed - Total allocated: " + totalAllocated);
        } else {
            System.out.println("✗ Get total allocated budget test failed");
        }
    }
    
    /**
     * Test getting total spent budget
     */
    private void testGetTotalSpentBudget() {
        System.out.println("Testing getTotalSpentBudget()...");
        
        BigDecimal totalSpent = budgetAllocationDAO.getTotalSpentBudget();
        
        if (totalSpent != null && totalSpent.compareTo(BigDecimal.ZERO) >= 0) {
            System.out.println("✓ Get total spent budget test passed - Total spent: " + totalSpent);
        } else {
            System.out.println("✗ Get total spent budget test failed");
        }
    }
    
    /**
     * Test getting remaining budget
     */
    private void testGetRemainingBudget() {
        System.out.println("Testing getRemainingBudget()...");
        
        BigDecimal remainingBudget = budgetAllocationDAO.getRemainingBudget();
        
        if (remainingBudget != null) {
            System.out.println("✓ Get remaining budget test passed - Remaining budget: " + remainingBudget);
        } else {
            System.out.println("✗ Get remaining budget test failed");
        }
    }
    
    /**
     * Test getting budget utilization percentage
     */
    private void testGetBudgetUtilizationPercentage() {
        System.out.println("Testing getBudgetUtilizationPercentage()...");
        
        double utilizationPercentage = budgetAllocationDAO.getBudgetUtilizationPercentage();
        
        if (utilizationPercentage >= 0.0 && utilizationPercentage <= 100.0) {
            System.out.println("✓ Get budget utilization percentage test passed - Utilization: " + utilizationPercentage + "%");
        } else {
            System.out.println("✗ Get budget utilization percentage test failed");
        }
    }
    
    /**
     * Test budget allocation deletion
     */
    private void testDeleteBudgetAllocation() {
        System.out.println("Testing deleteBudgetAllocation()...");
        
        List<BudgetAllocation> budgets = budgetAllocationDAO.getAllBudgetAllocations();
        BudgetAllocation testBudget = null;
        
        for (BudgetAllocation budget : budgets) {
            if (TEST_CATEGORY.equals(budget.getCategory())) {
                testBudget = budget;
                break;
            }
        }
        
        if (testBudget != null) {
            boolean result = budgetAllocationDAO.deleteBudgetAllocation(testBudget.getId());
            
            if (result) {
                System.out.println("✓ Delete budget allocation test passed");
            } else {
                System.out.println("✗ Delete budget allocation test failed");
            }
        } else {
            System.out.println("✗ Delete budget allocation test failed - no test budget available");
        }
    }
    
    /**
     * Clean up test data
     */
    private void cleanupTestData() {
        try {
            Connection conn = MySqlConnection.openConnection();
            if (conn != null) {
                String sql = "DELETE FROM budget_allocations WHERE category = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, TEST_CATEGORY);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            // Ignore cleanup errors
        }
    }
} 