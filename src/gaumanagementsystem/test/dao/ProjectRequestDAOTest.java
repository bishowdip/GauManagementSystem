package gaumanagementsystem.test.dao;

import gaumanagementsystem.dao.ProjectRequestDAO;
import gaumanagementsystem.dao.impl.ProjectRequestDAOImpl;
import gaumanagementsystem.model.ProjectRequest;
import gaumanagementsystem.database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Unit tests for ProjectRequestDAO interface and ProjectRequestDAOImpl
 * Tests all CRUD operations and business logic methods
 * 
 * @author Test Suite
 */
public class ProjectRequestDAOTest {
    
    private ProjectRequestDAO projectRequestDAO;
    private static final String TEST_PROJECT_NAME = "Test Project";
    private static final String TEST_DESCRIPTION = "Test project description for testing purposes";
    private static final String TEST_STATUS = "Pending";
    private static final String TEST_PRIORITY = "Medium";
    private static final String TEST_CATEGORY = "Infrastructure";
    private static final int TEST_WARD = 1;
    private static final BigDecimal TEST_BUDGET = new BigDecimal("100000.00");
    
    public ProjectRequestDAOTest() {
        this.projectRequestDAO = new ProjectRequestDAOImpl();
    }
    
    /**
     * Run all tests
     */
    public static void main(String[] args) {
        ProjectRequestDAOTest test = new ProjectRequestDAOTest();
        
        System.out.println("=== ProjectRequestDAO Test Suite ===");
        System.out.println("Starting ProjectRequestDAO tests...\n");
        
        try {
            // Setup test environment
            test.setupTestEnvironment();
            
            // Run all tests
            test.testCreateProjectRequest();
            test.testFindById();
            test.testGetAllProjectRequests();
            test.testGetProjectRequestsByStatus();
            test.testGetProjectRequestsByWard();
            test.testGetProjectRequestsByPriority();
            test.testSearchByProjectName();
            test.testGetProjectRequestsByBudgetRange();
            test.testUpdateProjectRequest();
            test.testUpdateProjectStatus();
            test.testUpdateProjectPriority();
            test.testUpdateProjectBudget();
            test.testGetProjectRequestCount();
            test.testGetProjectRequestCountByStatus();
            test.testGetProjectRequestCountByWard();
            test.testGetTotalProjectBudget();
            test.testGetTotalBudgetByStatus();
            test.testDeleteProjectRequest();
            
            // Cleanup
            test.cleanupTestData();
            
            System.out.println("\n=== All ProjectRequestDAO Tests Completed ===");
            
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
     * Test project request creation
     */
    private void testCreateProjectRequest() {
        System.out.println("Testing createProjectRequest()...");
        
        ProjectRequest testProject = new ProjectRequest();
        testProject.setProjectName(TEST_PROJECT_NAME);
        testProject.setDescription(TEST_DESCRIPTION);
        testProject.setStatus(TEST_STATUS);
        testProject.setPriority(TEST_PRIORITY);
        testProject.setCategory(TEST_CATEGORY);
        testProject.setWard(TEST_WARD);
        testProject.setBudget(TEST_BUDGET);
        testProject.setStartDate(new Date(System.currentTimeMillis()));
        testProject.setExpectedEndDate(new Date(System.currentTimeMillis() + 86400000L * 30)); // 30 days later
        
        boolean result = projectRequestDAO.createProjectRequest(testProject);
        
        if (result) {
            System.out.println("✓ Project request creation test passed");
        } else {
            System.out.println("✗ Project request creation test failed");
        }
    }
    
    /**
     * Test finding project request by ID
     */
    private void testFindById() {
        System.out.println("Testing findById()...");
        
        // First get all project requests to find our test project
        List<ProjectRequest> projects = projectRequestDAO.getAllProjectRequests();
        ProjectRequest testProject = null;
        
        for (ProjectRequest project : projects) {
            if (TEST_PROJECT_NAME.equals(project.getProjectName())) {
                testProject = project;
                break;
            }
        }
        
        if (testProject != null) {
            Optional<ProjectRequest> foundProject = projectRequestDAO.findById(testProject.getRequestId());
            
            if (foundProject.isPresent() && foundProject.get().getProjectName().equals(TEST_PROJECT_NAME)) {
                System.out.println("✓ Find by ID test passed");
            } else {
                System.out.println("✗ Find by ID test failed");
            }
        } else {
            System.out.println("✗ Find by ID test failed - no test project available");
        }
        
        // Test with non-existent ID
        Optional<ProjectRequest> nonExistentProject = projectRequestDAO.findById(99999);
        if (!nonExistentProject.isPresent()) {
            System.out.println("✓ Find by non-existent ID test passed");
        } else {
            System.out.println("✗ Find by non-existent ID test failed");
        }
    }
    
    /**
     * Test getting all project requests
     */
    private void testGetAllProjectRequests() {
        System.out.println("Testing getAllProjectRequests()...");
        
        List<ProjectRequest> allProjects = projectRequestDAO.getAllProjectRequests();
        
        if (allProjects != null && allProjects.size() > 0) {
            System.out.println("✓ Get all project requests test passed - Found " + allProjects.size() + " projects");
        } else {
            System.out.println("✗ Get all project requests test failed");
        }
    }
    
    /**
     * Test getting project requests by status
     */
    private void testGetProjectRequestsByStatus() {
        System.out.println("Testing getProjectRequestsByStatus()...");
        
        List<ProjectRequest> projectsByStatus = projectRequestDAO.getProjectRequestsByStatus(TEST_STATUS);
        
        if (projectsByStatus != null && projectsByStatus.size() > 0) {
            System.out.println("✓ Get project requests by status test passed - Found " + projectsByStatus.size() + " projects with status " + TEST_STATUS);
        } else {
            System.out.println("✗ Get project requests by status test failed");
        }
    }
    
    /**
     * Test getting project requests by ward
     */
    private void testGetProjectRequestsByWard() {
        System.out.println("Testing getProjectRequestsByWard()...");
        
        List<ProjectRequest> projectsByWard = projectRequestDAO.getProjectRequestsByWard(TEST_WARD);
        
        if (projectsByWard != null && projectsByWard.size() > 0) {
            System.out.println("✓ Get project requests by ward test passed - Found " + projectsByWard.size() + " projects in ward " + TEST_WARD);
        } else {
            System.out.println("✗ Get project requests by ward test failed");
        }
    }
    
    /**
     * Test getting project requests by priority
     */
    private void testGetProjectRequestsByPriority() {
        System.out.println("Testing getProjectRequestsByPriority()...");
        
        List<ProjectRequest> projectsByPriority = projectRequestDAO.getProjectRequestsByPriority(TEST_PRIORITY);
        
        if (projectsByPriority != null && projectsByPriority.size() > 0) {
            System.out.println("✓ Get project requests by priority test passed - Found " + projectsByPriority.size() + " projects with priority " + TEST_PRIORITY);
        } else {
            System.out.println("✗ Get project requests by priority test failed");
        }
    }
    
    /**
     * Test searching project requests by name
     */
    private void testSearchByProjectName() {
        System.out.println("Testing searchByProjectName()...");
        
        List<ProjectRequest> searchResults = projectRequestDAO.searchByProjectName("Test");
        
        if (searchResults != null && searchResults.size() > 0) {
            System.out.println("✓ Search by project name test passed - Found " + searchResults.size() + " projects");
        } else {
            System.out.println("✗ Search by project name test failed");
        }
    }
    
    /**
     * Test getting project requests by budget range
     */
    private void testGetProjectRequestsByBudgetRange() {
        System.out.println("Testing getProjectRequestsByBudgetRange()...");
        
        BigDecimal minBudget = new BigDecimal("50000.00");
        BigDecimal maxBudget = new BigDecimal("200000.00");
        
        List<ProjectRequest> projectsByBudget = projectRequestDAO.getProjectRequestsByBudgetRange(minBudget, maxBudget);
        
        if (projectsByBudget != null && projectsByBudget.size() > 0) {
            System.out.println("✓ Get project requests by budget range test passed - Found " + projectsByBudget.size() + " projects");
        } else {
            System.out.println("✗ Get project requests by budget range test failed");
        }
    }
    
    /**
     * Test updating project request information
     */
    private void testUpdateProjectRequest() {
        System.out.println("Testing updateProjectRequest()...");
        
        List<ProjectRequest> projects = projectRequestDAO.getAllProjectRequests();
        ProjectRequest testProject = null;
        
        for (ProjectRequest project : projects) {
            if (TEST_PROJECT_NAME.equals(project.getProjectName())) {
                testProject = project;
                break;
            }
        }
        
        if (testProject != null) {
            String originalDescription = testProject.getDescription();
            testProject.setDescription("Updated description for testing");
            
            boolean result = projectRequestDAO.updateProjectRequest(testProject);
            
            if (result) {
                // Verify the update
                Optional<ProjectRequest> updatedProject = projectRequestDAO.findById(testProject.getRequestId());
                if (updatedProject.isPresent() && "Updated description for testing".equals(updatedProject.get().getDescription())) {
                    System.out.println("✓ Update project request test passed");
                    
                    // Restore original description
                    testProject.setDescription(originalDescription);
                    projectRequestDAO.updateProjectRequest(testProject);
                } else {
                    System.out.println("✗ Update project request test failed - update not reflected");
                }
            } else {
                System.out.println("✗ Update project request test failed");
            }
        } else {
            System.out.println("✗ Update project request test failed - no test project available");
        }
    }
    
    /**
     * Test updating project status
     */
    private void testUpdateProjectStatus() {
        System.out.println("Testing updateProjectStatus()...");
        
        List<ProjectRequest> projects = projectRequestDAO.getAllProjectRequests();
        ProjectRequest testProject = null;
        
        for (ProjectRequest project : projects) {
            if (TEST_PROJECT_NAME.equals(project.getProjectName())) {
                testProject = project;
                break;
            }
        }
        
        if (testProject != null) {
            String originalStatus = testProject.getStatus();
            String newStatus = "Approved";
            
            boolean result = projectRequestDAO.updateProjectStatus(testProject.getRequestId(), newStatus);
            
            if (result) {
                // Verify the status update
                Optional<ProjectRequest> updatedProject = projectRequestDAO.findById(testProject.getRequestId());
                if (updatedProject.isPresent() && newStatus.equals(updatedProject.get().getStatus())) {
                    System.out.println("✓ Update project status test passed");
                    
                    // Restore original status
                    projectRequestDAO.updateProjectStatus(testProject.getRequestId(), originalStatus);
                } else {
                    System.out.println("✗ Update project status test failed - status not updated");
                }
            } else {
                System.out.println("✗ Update project status test failed");
            }
        } else {
            System.out.println("✗ Update project status test failed - no test project available");
        }
    }
    
    /**
     * Test updating project priority
     */
    private void testUpdateProjectPriority() {
        System.out.println("Testing updateProjectPriority()...");
        
        List<ProjectRequest> projects = projectRequestDAO.getAllProjectRequests();
        ProjectRequest testProject = null;
        
        for (ProjectRequest project : projects) {
            if (TEST_PROJECT_NAME.equals(project.getProjectName())) {
                testProject = project;
                break;
            }
        }
        
        if (testProject != null) {
            String originalPriority = testProject.getPriority();
            String newPriority = "High";
            
            boolean result = projectRequestDAO.updateProjectPriority(testProject.getRequestId(), newPriority);
            
            if (result) {
                // Verify the priority update
                Optional<ProjectRequest> updatedProject = projectRequestDAO.findById(testProject.getRequestId());
                if (updatedProject.isPresent() && newPriority.equals(updatedProject.get().getPriority())) {
                    System.out.println("✓ Update project priority test passed");
                    
                    // Restore original priority
                    projectRequestDAO.updateProjectPriority(testProject.getRequestId(), originalPriority);
                } else {
                    System.out.println("✗ Update project priority test failed - priority not updated");
                }
            } else {
                System.out.println("✗ Update project priority test failed");
            }
        } else {
            System.out.println("✗ Update project priority test failed - no test project available");
        }
    }
    
    /**
     * Test updating project budget
     */
    private void testUpdateProjectBudget() {
        System.out.println("Testing updateProjectBudget()...");
        
        List<ProjectRequest> projects = projectRequestDAO.getAllProjectRequests();
        ProjectRequest testProject = null;
        
        for (ProjectRequest project : projects) {
            if (TEST_PROJECT_NAME.equals(project.getProjectName())) {
                testProject = project;
                break;
            }
        }
        
        if (testProject != null) {
            BigDecimal originalBudget = testProject.getBudget();
            BigDecimal newBudget = new BigDecimal("150000.00");
            
            boolean result = projectRequestDAO.updateProjectBudget(testProject.getRequestId(), newBudget);
            
            if (result) {
                // Verify the budget update
                Optional<ProjectRequest> updatedProject = projectRequestDAO.findById(testProject.getRequestId());
                if (updatedProject.isPresent() && newBudget.compareTo(updatedProject.get().getBudget()) == 0) {
                    System.out.println("✓ Update project budget test passed");
                    
                    // Restore original budget
                    projectRequestDAO.updateProjectBudget(testProject.getRequestId(), originalBudget);
                } else {
                    System.out.println("✗ Update project budget test failed - budget not updated");
                }
            } else {
                System.out.println("✗ Update project budget test failed");
            }
        } else {
            System.out.println("✗ Update project budget test failed - no test project available");
        }
    }
    
    /**
     * Test getting project request count
     */
    private void testGetProjectRequestCount() {
        System.out.println("Testing getProjectRequestCount()...");
        
        int count = projectRequestDAO.getProjectRequestCount();
        
        if (count > 0) {
            System.out.println("✓ Get project request count test passed - Total projects: " + count);
        } else {
            System.out.println("✗ Get project request count test failed");
        }
    }
    
    /**
     * Test getting project request count by status
     */
    private void testGetProjectRequestCountByStatus() {
        System.out.println("Testing getProjectRequestCountByStatus()...");
        
        int count = projectRequestDAO.getProjectRequestCountByStatus(TEST_STATUS);
        
        if (count > 0) {
            System.out.println("✓ Get project request count by status test passed - Projects with status " + TEST_STATUS + ": " + count);
        } else {
            System.out.println("✗ Get project request count by status test failed");
        }
    }
    
    /**
     * Test getting project request count by ward
     */
    private void testGetProjectRequestCountByWard() {
        System.out.println("Testing getProjectRequestCountByWard()...");
        
        int count = projectRequestDAO.getProjectRequestCountByWard(TEST_WARD);
        
        if (count > 0) {
            System.out.println("✓ Get project request count by ward test passed - Projects in ward " + TEST_WARD + ": " + count);
        } else {
            System.out.println("✗ Get project request count by ward test failed");
        }
    }
    
    /**
     * Test getting total project budget
     */
    private void testGetTotalProjectBudget() {
        System.out.println("Testing getTotalProjectBudget()...");
        
        BigDecimal totalBudget = projectRequestDAO.getTotalProjectBudget();
        
        if (totalBudget != null && totalBudget.compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("✓ Get total project budget test passed - Total budget: " + totalBudget);
        } else {
            System.out.println("✗ Get total project budget test failed");
        }
    }
    
    /**
     * Test getting total budget by status
     */
    private void testGetTotalBudgetByStatus() {
        System.out.println("Testing getTotalBudgetByStatus()...");
        
        BigDecimal totalBudget = projectRequestDAO.getTotalBudgetByStatus(TEST_STATUS);
        
        if (totalBudget != null && totalBudget.compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("✓ Get total budget by status test passed - Total budget for " + TEST_STATUS + ": " + totalBudget);
        } else {
            System.out.println("✗ Get total budget by status test failed");
        }
    }
    
    /**
     * Test project request deletion
     */
    private void testDeleteProjectRequest() {
        System.out.println("Testing deleteProjectRequest()...");
        
        List<ProjectRequest> projects = projectRequestDAO.getAllProjectRequests();
        ProjectRequest testProject = null;
        
        for (ProjectRequest project : projects) {
            if (TEST_PROJECT_NAME.equals(project.getProjectName())) {
                testProject = project;
                break;
            }
        }
        
        if (testProject != null) {
            boolean result = projectRequestDAO.deleteProjectRequest(testProject.getRequestId());
            
            if (result) {
                // Verify deletion
                Optional<ProjectRequest> deletedProject = projectRequestDAO.findById(testProject.getRequestId());
                if (!deletedProject.isPresent()) {
                    System.out.println("✓ Delete project request test passed");
                } else {
                    System.out.println("✗ Delete project request test failed - project still exists");
                }
            } else {
                System.out.println("✗ Delete project request test failed");
            }
        } else {
            System.out.println("✗ Delete project request test failed - no test project available");
        }
    }
    
    /**
     * Clean up test data
     */
    private void cleanupTestData() {
        try {
            Connection conn = MySqlConnection.openConnection();
            if (conn != null) {
                String sql = "DELETE FROM project_requests WHERE project_name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, TEST_PROJECT_NAME);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            // Ignore cleanup errors
        }
    }
} 