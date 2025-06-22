package gaumanagementsystem.test.dao;

/**
 * Master test runner for all DAO tests
 * Executes all DAO test suites in sequence
 * 
 * @author Test Suite
 */
public class AllDAOTestRunner {
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("    GAU MANAGEMENT SYSTEM - DAO TESTS");
        System.out.println("========================================");
        System.out.println("Running comprehensive tests for all DAO classes...\n");
        
        long startTime = System.currentTimeMillis();
        
        try {
            // Run all DAO tests
            System.out.println("1. Running UserDAO Tests...");
            UserDAOTest.main(new String[]{});
            System.out.println("\n" + "=".repeat(50) + "\n");
            
            System.out.println("2. Running ComplaintDAO Tests...");
            ComplaintDAOTest.main(new String[]{});
            System.out.println("\n" + "=".repeat(50) + "\n");
            
            System.out.println("3. Running ProjectRequestDAO Tests...");
            ProjectRequestDAOTest.main(new String[]{});
            System.out.println("\n" + "=".repeat(50) + "\n");
            
            System.out.println("4. Running NewsAndNoticeDAO Tests...");
            NewsAndNoticeDAOTest.main(new String[]{});
            System.out.println("\n" + "=".repeat(50) + "\n");
            
            System.out.println("5. Running ServiceDAO Tests...");
            ServiceDAOTest.main(new String[]{});
            System.out.println("\n" + "=".repeat(50) + "\n");
            
            System.out.println("6. Running BudgetAllocationDAO Tests...");
            BudgetAllocationDAOTest.main(new String[]{});
            System.out.println("\n" + "=".repeat(50) + "\n");
            
            System.out.println("7. Running CitizenDAO Tests...");
            CitizenDAOTest.main(new String[]{});
            System.out.println("\n" + "=".repeat(50) + "\n");
            
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            
            System.out.println("========================================");
            System.out.println("        ALL DAO TESTS COMPLETED");
            System.out.println("========================================");
            System.out.println("Total execution time: " + (duration / 1000.0) + " seconds");
            System.out.println("Tests completed successfully!");
            System.out.println("========================================");
            
        } catch (Exception e) {
            System.err.println("Error during test execution: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 