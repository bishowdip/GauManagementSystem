package gaumanagementsystem.test.dao;

import gaumanagementsystem.dao.ComplaintDAO;
import gaumanagementsystem.dao.impl.ComplaintDAOImpl;
import gaumanagementsystem.model.Complaint;
import gaumanagementsystem.database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Unit tests for ComplaintDAO interface and ComplaintDAOImpl
 * Tests all CRUD operations and business logic methods
 * 
 * @author Test Suite
 */
public class ComplaintDAOTest {
    
    private ComplaintDAO complaintDAO;
    private static final String TEST_CITIZEN_NAME = "Test Citizen";
    private static final String TEST_EMAIL = "test.citizen@test.com";
    private static final String TEST_SUBJECT = "Test Complaint Subject";
    private static final String TEST_DESCRIPTION = "Test complaint description for testing purposes";
    private static final String TEST_TYPE = "Complaint";
    private static final String TEST_STATUS = "Pending";
    private static final String TEST_PRIORITY = "Medium";
    private static final int TEST_WARD = 1;
    
    public ComplaintDAOTest() {
        this.complaintDAO = new ComplaintDAOImpl();
    }
    
    /**
     * Run all tests
     */
    public static void main(String[] args) {
        ComplaintDAOTest test = new ComplaintDAOTest();
        
        System.out.println("=== ComplaintDAO Test Suite ===");
        System.out.println("Starting ComplaintDAO tests...\n");
        
        try {
            // Setup test environment
            test.setupTestEnvironment();
            
            // Run all tests
            test.testCreateComplaint();
            test.testFindById();
            test.testGetAllComplaints();
            test.testGetComplaintsByType();
            test.testGetComplaintsByStatus();
            test.testGetComplaintsByPriority();
            test.testGetComplaintsByWard();
            test.testGetComplaintsByCitizen();
            test.testSearchBySubject();
            test.testSearchByDescription();
            test.testUpdateComplaint();
            test.testUpdateComplaintStatus();
            test.testUpdateComplaintPriority();
            test.testAddComplaintResponse();
            test.testGetComplaintCount();
            test.testGetComplaintCountByType();
            test.testGetComplaintCountByStatus();
            test.testGetComplaintCountByPriority();
            test.testGetComplaintCountByWard();
            test.testDeleteComplaint();
            
            // Cleanup
            test.cleanupTestData();
            
            System.out.println("\n=== All ComplaintDAO Tests Completed ===");
            
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
     * Test complaint creation
     */
    private void testCreateComplaint() {
        System.out.println("Testing createComplaint()...");
        
        Complaint testComplaint = new Complaint();
        testComplaint.setCitizenName(TEST_CITIZEN_NAME);
        testComplaint.setEmail(TEST_EMAIL);
        testComplaint.setSubject(TEST_SUBJECT);
        testComplaint.setDescription(TEST_DESCRIPTION);
        testComplaint.setType(TEST_TYPE);
        testComplaint.setStatus(TEST_STATUS);
        testComplaint.setPriority(TEST_PRIORITY);
        testComplaint.setWard(TEST_WARD);
        testComplaint.setSubmissionDate(new Date(System.currentTimeMillis()));
        
        boolean result = complaintDAO.createComplaint(testComplaint);
        
        if (result) {
            System.out.println("✓ Complaint creation test passed");
        } else {
            System.out.println("✗ Complaint creation test failed");
        }
    }
    
    /**
     * Test finding complaint by ID
     */
    private void testFindById() {
        System.out.println("Testing findById()...");
        
        // First get all complaints to find our test complaint
        List<Complaint> complaints = complaintDAO.getAllComplaints();
        Complaint testComplaint = null;
        
        for (Complaint complaint : complaints) {
            if (TEST_SUBJECT.equals(complaint.getSubject())) {
                testComplaint = complaint;
                break;
            }
        }
        
        if (testComplaint != null) {
            Optional<Complaint> foundComplaint = complaintDAO.findById(testComplaint.getId());
            
            if (foundComplaint.isPresent() && foundComplaint.get().getSubject().equals(TEST_SUBJECT)) {
                System.out.println("✓ Find by ID test passed");
            } else {
                System.out.println("✗ Find by ID test failed");
            }
        } else {
            System.out.println("✗ Find by ID test failed - no test complaint available");
        }
        
        // Test with non-existent ID
        Optional<Complaint> nonExistentComplaint = complaintDAO.findById(99999);
        if (!nonExistentComplaint.isPresent()) {
            System.out.println("✓ Find by non-existent ID test passed");
        } else {
            System.out.println("✗ Find by non-existent ID test failed");
        }
    }
    
    /**
     * Test getting all complaints
     */
    private void testGetAllComplaints() {
        System.out.println("Testing getAllComplaints()...");
        
        List<Complaint> allComplaints = complaintDAO.getAllComplaints();
        
        if (allComplaints != null && allComplaints.size() > 0) {
            System.out.println("✓ Get all complaints test passed - Found " + allComplaints.size() + " complaints");
        } else {
            System.out.println("✗ Get all complaints test failed");
        }
    }
    
    /**
     * Test getting complaints by type
     */
    private void testGetComplaintsByType() {
        System.out.println("Testing getComplaintsByType()...");
        
        List<Complaint> complaintsByType = complaintDAO.getComplaintsByType(TEST_TYPE);
        
        if (complaintsByType != null && complaintsByType.size() > 0) {
            System.out.println("✓ Get complaints by type test passed - Found " + complaintsByType.size() + " complaints of type " + TEST_TYPE);
        } else {
            System.out.println("✗ Get complaints by type test failed");
        }
    }
    
    /**
     * Test getting complaints by status
     */
    private void testGetComplaintsByStatus() {
        System.out.println("Testing getComplaintsByStatus()...");
        
        List<Complaint> complaintsByStatus = complaintDAO.getComplaintsByStatus(TEST_STATUS);
        
        if (complaintsByStatus != null && complaintsByStatus.size() > 0) {
            System.out.println("✓ Get complaints by status test passed - Found " + complaintsByStatus.size() + " complaints with status " + TEST_STATUS);
        } else {
            System.out.println("✗ Get complaints by status test failed");
        }
    }
    
    /**
     * Test getting complaints by priority
     */
    private void testGetComplaintsByPriority() {
        System.out.println("Testing getComplaintsByPriority()...");
        
        List<Complaint> complaintsByPriority = complaintDAO.getComplaintsByPriority(TEST_PRIORITY);
        
        if (complaintsByPriority != null && complaintsByPriority.size() > 0) {
            System.out.println("✓ Get complaints by priority test passed - Found " + complaintsByPriority.size() + " complaints with priority " + TEST_PRIORITY);
        } else {
            System.out.println("✗ Get complaints by priority test failed");
        }
    }
    
    /**
     * Test getting complaints by ward
     */
    private void testGetComplaintsByWard() {
        System.out.println("Testing getComplaintsByWard()...");
        
        List<Complaint> complaintsByWard = complaintDAO.getComplaintsByWard(TEST_WARD);
        
        if (complaintsByWard != null && complaintsByWard.size() > 0) {
            System.out.println("✓ Get complaints by ward test passed - Found " + complaintsByWard.size() + " complaints in ward " + TEST_WARD);
        } else {
            System.out.println("✗ Get complaints by ward test failed");
        }
    }
    
    /**
     * Test getting complaints by citizen
     */
    private void testGetComplaintsByCitizen() {
        System.out.println("Testing getComplaintsByCitizen()...");
        
        List<Complaint> complaintsByCitizen = complaintDAO.getComplaintsByCitizen(TEST_CITIZEN_NAME);
        
        if (complaintsByCitizen != null && complaintsByCitizen.size() > 0) {
            System.out.println("✓ Get complaints by citizen test passed - Found " + complaintsByCitizen.size() + " complaints by " + TEST_CITIZEN_NAME);
        } else {
            System.out.println("✗ Get complaints by citizen test failed");
        }
    }
    
    /**
     * Test searching complaints by subject
     */
    private void testSearchBySubject() {
        System.out.println("Testing searchBySubject()...");
        
        List<Complaint> searchResults = complaintDAO.searchBySubject("Test");
        
        if (searchResults != null && searchResults.size() > 0) {
            System.out.println("✓ Search by subject test passed - Found " + searchResults.size() + " complaints");
        } else {
            System.out.println("✗ Search by subject test failed");
        }
    }
    
    /**
     * Test searching complaints by description
     */
    private void testSearchByDescription() {
        System.out.println("Testing searchByDescription()...");
        
        List<Complaint> searchResults = complaintDAO.searchByDescription("testing");
        
        if (searchResults != null && searchResults.size() > 0) {
            System.out.println("✓ Search by description test passed - Found " + searchResults.size() + " complaints");
        } else {
            System.out.println("✗ Search by description test failed");
        }
    }
    
    /**
     * Test updating complaint information
     */
    private void testUpdateComplaint() {
        System.out.println("Testing updateComplaint()...");
        
        List<Complaint> complaints = complaintDAO.getAllComplaints();
        Complaint testComplaint = null;
        
        for (Complaint complaint : complaints) {
            if (TEST_SUBJECT.equals(complaint.getSubject())) {
                testComplaint = complaint;
                break;
            }
        }
        
        if (testComplaint != null) {
            String originalDescription = testComplaint.getDescription();
            testComplaint.setDescription("Updated description for testing");
            
            boolean result = complaintDAO.updateComplaint(testComplaint);
            
            if (result) {
                // Verify the update
                Optional<Complaint> updatedComplaint = complaintDAO.findById(testComplaint.getId());
                if (updatedComplaint.isPresent() && "Updated description for testing".equals(updatedComplaint.get().getDescription())) {
                    System.out.println("✓ Update complaint test passed");
                    
                    // Restore original description
                    testComplaint.setDescription(originalDescription);
                    complaintDAO.updateComplaint(testComplaint);
                } else {
                    System.out.println("✗ Update complaint test failed - update not reflected");
                }
            } else {
                System.out.println("✗ Update complaint test failed");
            }
        } else {
            System.out.println("✗ Update complaint test failed - no test complaint available");
        }
    }
    
    /**
     * Test updating complaint status
     */
    private void testUpdateComplaintStatus() {
        System.out.println("Testing updateComplaintStatus()...");
        
        List<Complaint> complaints = complaintDAO.getAllComplaints();
        Complaint testComplaint = null;
        
        for (Complaint complaint : complaints) {
            if (TEST_SUBJECT.equals(complaint.getSubject())) {
                testComplaint = complaint;
                break;
            }
        }
        
        if (testComplaint != null) {
            String originalStatus = testComplaint.getStatus();
            String newStatus = "In Progress";
            
            boolean result = complaintDAO.updateComplaintStatus(testComplaint.getId(), newStatus);
            
            if (result) {
                // Verify the status update
                Optional<Complaint> updatedComplaint = complaintDAO.findById(testComplaint.getId());
                if (updatedComplaint.isPresent() && newStatus.equals(updatedComplaint.get().getStatus())) {
                    System.out.println("✓ Update complaint status test passed");
                    
                    // Restore original status
                    complaintDAO.updateComplaintStatus(testComplaint.getId(), originalStatus);
                } else {
                    System.out.println("✗ Update complaint status test failed - status not updated");
                }
            } else {
                System.out.println("✗ Update complaint status test failed");
            }
        } else {
            System.out.println("✗ Update complaint status test failed - no test complaint available");
        }
    }
    
    /**
     * Test updating complaint priority
     */
    private void testUpdateComplaintPriority() {
        System.out.println("Testing updateComplaintPriority()...");
        
        List<Complaint> complaints = complaintDAO.getAllComplaints();
        Complaint testComplaint = null;
        
        for (Complaint complaint : complaints) {
            if (TEST_SUBJECT.equals(complaint.getSubject())) {
                testComplaint = complaint;
                break;
            }
        }
        
        if (testComplaint != null) {
            String originalPriority = testComplaint.getPriority();
            String newPriority = "High";
            
            boolean result = complaintDAO.updateComplaintPriority(testComplaint.getId(), newPriority);
            
            if (result) {
                // Verify the priority update
                Optional<Complaint> updatedComplaint = complaintDAO.findById(testComplaint.getId());
                if (updatedComplaint.isPresent() && newPriority.equals(updatedComplaint.get().getPriority())) {
                    System.out.println("✓ Update complaint priority test passed");
                    
                    // Restore original priority
                    complaintDAO.updateComplaintPriority(testComplaint.getId(), originalPriority);
                } else {
                    System.out.println("✗ Update complaint priority test failed - priority not updated");
                }
            } else {
                System.out.println("✗ Update complaint priority test failed");
            }
        } else {
            System.out.println("✗ Update complaint priority test failed - no test complaint available");
        }
    }
    
    /**
     * Test adding complaint response
     */
    private void testAddComplaintResponse() {
        System.out.println("Testing addComplaintResponse()...");
        
        List<Complaint> complaints = complaintDAO.getAllComplaints();
        Complaint testComplaint = null;
        
        for (Complaint complaint : complaints) {
            if (TEST_SUBJECT.equals(complaint.getSubject())) {
                testComplaint = complaint;
                break;
            }
        }
        
        if (testComplaint != null) {
            String response = "Test response for complaint";
            
            boolean result = complaintDAO.addComplaintResponse(testComplaint.getId(), response);
            
            if (result) {
                System.out.println("✓ Add complaint response test passed");
            } else {
                System.out.println("✗ Add complaint response test failed");
            }
        } else {
            System.out.println("✗ Add complaint response test failed - no test complaint available");
        }
    }
    
    /**
     * Test getting complaint count
     */
    private void testGetComplaintCount() {
        System.out.println("Testing getComplaintCount()...");
        
        int count = complaintDAO.getComplaintCount();
        
        if (count > 0) {
            System.out.println("✓ Get complaint count test passed - Total complaints: " + count);
        } else {
            System.out.println("✗ Get complaint count test failed");
        }
    }
    
    /**
     * Test getting complaint count by type
     */
    private void testGetComplaintCountByType() {
        System.out.println("Testing getComplaintCountByType()...");
        
        int count = complaintDAO.getComplaintCountByType(TEST_TYPE);
        
        if (count > 0) {
            System.out.println("✓ Get complaint count by type test passed - Complaints of type " + TEST_TYPE + ": " + count);
        } else {
            System.out.println("✗ Get complaint count by type test failed");
        }
    }
    
    /**
     * Test getting complaint count by status
     */
    private void testGetComplaintCountByStatus() {
        System.out.println("Testing getComplaintCountByStatus()...");
        
        int count = complaintDAO.getComplaintCountByStatus(TEST_STATUS);
        
        if (count > 0) {
            System.out.println("✓ Get complaint count by status test passed - Complaints with status " + TEST_STATUS + ": " + count);
        } else {
            System.out.println("✗ Get complaint count by status test failed");
        }
    }
    
    /**
     * Test getting complaint count by priority
     */
    private void testGetComplaintCountByPriority() {
        System.out.println("Testing getComplaintCountByPriority()...");
        
        int count = complaintDAO.getComplaintCountByPriority(TEST_PRIORITY);
        
        if (count > 0) {
            System.out.println("✓ Get complaint count by priority test passed - Complaints with priority " + TEST_PRIORITY + ": " + count);
        } else {
            System.out.println("✗ Get complaint count by priority test failed");
        }
    }
    
    /**
     * Test getting complaint count by ward
     */
    private void testGetComplaintCountByWard() {
        System.out.println("Testing getComplaintCountByWard()...");
        
        int count = complaintDAO.getComplaintCountByWard(TEST_WARD);
        
        if (count > 0) {
            System.out.println("✓ Get complaint count by ward test passed - Complaints in ward " + TEST_WARD + ": " + count);
        } else {
            System.out.println("✗ Get complaint count by ward test failed");
        }
    }
    
    /**
     * Test complaint deletion
     */
    private void testDeleteComplaint() {
        System.out.println("Testing deleteComplaint()...");
        
        List<Complaint> complaints = complaintDAO.getAllComplaints();
        Complaint testComplaint = null;
        
        for (Complaint complaint : complaints) {
            if (TEST_SUBJECT.equals(complaint.getSubject())) {
                testComplaint = complaint;
                break;
            }
        }
        
        if (testComplaint != null) {
            boolean result = complaintDAO.deleteComplaint(testComplaint.getId());
            
            if (result) {
                // Verify deletion
                Optional<Complaint> deletedComplaint = complaintDAO.findById(testComplaint.getId());
                if (!deletedComplaint.isPresent()) {
                    System.out.println("✓ Delete complaint test passed");
                } else {
                    System.out.println("✗ Delete complaint test failed - complaint still exists");
                }
            } else {
                System.out.println("✗ Delete complaint test failed");
            }
        } else {
            System.out.println("✗ Delete complaint test failed - no test complaint available");
        }
    }
    
    /**
     * Clean up test data
     */
    private void cleanupTestData() {
        try {
            Connection conn = MySqlConnection.openConnection();
            if (conn != null) {
                String sql = "DELETE FROM complaints WHERE subject = ? AND citizen_name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, TEST_SUBJECT);
                pstmt.setString(2, TEST_CITIZEN_NAME);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            // Ignore cleanup errors
        }
    }
} 