package gaumanagementsystem.test.dao;

import gaumanagementsystem.dao.CitizenDao;
import gaumanagementsystem.model.Citizen;
import gaumanagementsystem.database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Unit tests for CitizenDao
 * Tests all CRUD operations and business logic methods
 * 
 * @author Test Suite
 */
public class CitizenDAOTest {
    
    private CitizenDao citizenDAO;
    private static final String TEST_CITIZEN_ID = "TEST123456789";
    private static final String TEST_NAME = "Test Citizen";
    private static final String TEST_EMAIL = "test.citizen@test.com";
    private static final String TEST_PHONE = "9876543210";
    private static final String TEST_ADDRESS = "Test Address, Test City";
    private static final String TEST_GENDER = "Male";
    private static final int TEST_WARD = 1;
    private static final String TEST_FATHER_NAME = "Test Father";
    private static final String TEST_MOTHER_NAME = "Test Mother";
    
    public CitizenDAOTest() {
        this.citizenDAO = new CitizenDao();
    }
    
    /**
     * Run all tests
     */
    public static void main(String[] args) {
        CitizenDAOTest test = new CitizenDAOTest();
        
        System.out.println("=== CitizenDAO Test Suite ===");
        System.out.println("Starting CitizenDAO tests...\n");
        
        try {
            // Setup test environment
            test.setupTestEnvironment();
            
            // Run all tests
            test.testCreateCitizen();
            test.testFindById();
            test.testFindByCitizenId();
            test.testFindByEmail();
            test.testGetAllCitizens();
            test.testGetCitizensByWard();
            test.testGetCitizensByGender();
            test.testSearchCitizensByName();
            test.testUpdateCitizen();
            test.testUpdateCitizenContact();
            test.testGetCitizenCount();
            test.testGetCitizenCountByWard();
            test.testGetCitizenCountByGender();
            test.testCitizenIdExists();
            test.testEmailExists();
            test.testDeleteCitizen();
            
            // Cleanup
            test.cleanupTestData();
            
            System.out.println("\n=== All CitizenDAO Tests Completed ===");
            
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
     * Test citizen creation
     */
    private void testCreateCitizen() {
        System.out.println("Testing createCitizen()...");
        
        Citizen testCitizen = new Citizen();
        testCitizen.setCitizenId(TEST_CITIZEN_ID);
        testCitizen.setName(TEST_NAME);
        testCitizen.setEmail(TEST_EMAIL);
        testCitizen.setPhone(TEST_PHONE);
        testCitizen.setAddress(TEST_ADDRESS);
        testCitizen.setGender(TEST_GENDER);
        testCitizen.setWard(TEST_WARD);
        testCitizen.setFatherName(TEST_FATHER_NAME);
        testCitizen.setMotherName(TEST_MOTHER_NAME);
        testCitizen.setDateOfBirth(new Date(System.currentTimeMillis() - 86400000L * 365 * 25)); // 25 years ago
        testCitizen.setRegistrationDate(new Date(System.currentTimeMillis()));
        
        boolean result = citizenDAO.createCitizen(testCitizen);
        
        if (result) {
            System.out.println("✓ Citizen creation test passed");
        } else {
            System.out.println("✗ Citizen creation test failed");
        }
    }
    
    /**
     * Test finding citizen by ID
     */
    private void testFindById() {
        System.out.println("Testing findById()...");
        
        // First get all citizens to find our test citizen
        List<Citizen> citizens = citizenDAO.getAllCitizens();
        Citizen testCitizen = null;
        
        for (Citizen citizen : citizens) {
            if (TEST_CITIZEN_ID.equals(citizen.getCitizenId())) {
                testCitizen = citizen;
                break;
            }
        }
        
        if (testCitizen != null) {
            Optional<Citizen> foundCitizen = citizenDAO.findById(testCitizen.getId());
            
            if (foundCitizen.isPresent() && foundCitizen.get().getCitizenId().equals(TEST_CITIZEN_ID)) {
                System.out.println("✓ Find by ID test passed");
            } else {
                System.out.println("✗ Find by ID test failed");
            }
        } else {
            System.out.println("✗ Find by ID test failed - no test citizen available");
        }
        
        // Test with non-existent ID
        Optional<Citizen> nonExistentCitizen = citizenDAO.findById(99999);
        if (!nonExistentCitizen.isPresent()) {
            System.out.println("✓ Find by non-existent ID test passed");
        } else {
            System.out.println("✗ Find by non-existent ID test failed");
        }
    }
    
    /**
     * Test finding citizen by citizen ID
     */
    private void testFindByCitizenId() {
        System.out.println("Testing findByCitizenId()...");
        
        Optional<Citizen> foundCitizen = citizenDAO.findByCitizenId(TEST_CITIZEN_ID);
        
        if (foundCitizen.isPresent() && foundCitizen.get().getName().equals(TEST_NAME)) {
            System.out.println("✓ Find by citizen ID test passed");
        } else {
            System.out.println("✗ Find by citizen ID test failed");
        }
        
        // Test with non-existent citizen ID
        Optional<Citizen> nonExistentCitizen = citizenDAO.findByCitizenId("NONEXISTENT123");
        if (!nonExistentCitizen.isPresent()) {
            System.out.println("✓ Find by non-existent citizen ID test passed");
        } else {
            System.out.println("✗ Find by non-existent citizen ID test failed");
        }
    }
    
    /**
     * Test finding citizen by email
     */
    private void testFindByEmail() {
        System.out.println("Testing findByEmail()...");
        
        Optional<Citizen> foundCitizen = citizenDAO.findByEmail(TEST_EMAIL);
        
        if (foundCitizen.isPresent() && foundCitizen.get().getCitizenId().equals(TEST_CITIZEN_ID)) {
            System.out.println("✓ Find by email test passed");
        } else {
            System.out.println("✗ Find by email test failed");
        }
        
        // Test with non-existent email
        Optional<Citizen> nonExistentCitizen = citizenDAO.findByEmail("nonexistent@test.com");
        if (!nonExistentCitizen.isPresent()) {
            System.out.println("✓ Find by non-existent email test passed");
        } else {
            System.out.println("✗ Find by non-existent email test failed");
        }
    }
    
    /**
     * Test getting all citizens
     */
    private void testGetAllCitizens() {
        System.out.println("Testing getAllCitizens()...");
        
        List<Citizen> allCitizens = citizenDAO.getAllCitizens();
        
        if (allCitizens != null && allCitizens.size() > 0) {
            System.out.println("✓ Get all citizens test passed - Found " + allCitizens.size() + " citizens");
        } else {
            System.out.println("✗ Get all citizens test failed");
        }
    }
    
    /**
     * Test getting citizens by ward
     */
    private void testGetCitizensByWard() {
        System.out.println("Testing getCitizensByWard()...");
        
        List<Citizen> citizensByWard = citizenDAO.getCitizensByWard(TEST_WARD);
        
        if (citizensByWard != null && citizensByWard.size() > 0) {
            System.out.println("✓ Get citizens by ward test passed - Found " + citizensByWard.size() + " citizens in ward " + TEST_WARD);
        } else {
            System.out.println("✗ Get citizens by ward test failed");
        }
    }
    
    /**
     * Test getting citizens by gender
     */
    private void testGetCitizensByGender() {
        System.out.println("Testing getCitizensByGender()...");
        
        List<Citizen> citizensByGender = citizenDAO.getCitizensByGender(TEST_GENDER);
        
        if (citizensByGender != null && citizensByGender.size() > 0) {
            System.out.println("✓ Get citizens by gender test passed - Found " + citizensByGender.size() + " " + TEST_GENDER + " citizens");
        } else {
            System.out.println("✗ Get citizens by gender test failed");
        }
    }
    
    /**
     * Test searching citizens by name
     */
    private void testSearchCitizensByName() {
        System.out.println("Testing searchCitizensByName()...");
        
        List<Citizen> searchResults = citizenDAO.searchCitizensByName("Test");
        
        if (searchResults != null && searchResults.size() > 0) {
            System.out.println("✓ Search citizens by name test passed - Found " + searchResults.size() + " citizens");
        } else {
            System.out.println("✗ Search citizens by name test failed");
        }
    }
    
    /**
     * Test updating citizen information
     */
    private void testUpdateCitizen() {
        System.out.println("Testing updateCitizen()...");
        
        Optional<Citizen> citizenOpt = citizenDAO.findByCitizenId(TEST_CITIZEN_ID);
        
        if (citizenOpt.isPresent()) {
            Citizen citizen = citizenOpt.get();
            String originalAddress = citizen.getAddress();
            citizen.setAddress("Updated Address for Testing");
            
            boolean result = citizenDAO.updateCitizen(citizen);
            
            if (result) {
                // Verify the update
                Optional<Citizen> updatedCitizen = citizenDAO.findById(citizen.getId());
                if (updatedCitizen.isPresent() && "Updated Address for Testing".equals(updatedCitizen.get().getAddress())) {
                    System.out.println("✓ Update citizen test passed");
                    
                    // Restore original address
                    citizen.setAddress(originalAddress);
                    citizenDAO.updateCitizen(citizen);
                } else {
                    System.out.println("✗ Update citizen test failed - update not reflected");
                }
            } else {
                System.out.println("✗ Update citizen test failed");
            }
        } else {
            System.out.println("✗ Update citizen test failed - no test citizen available");
        }
    }
    
    /**
     * Test updating citizen contact information
     */
    private void testUpdateCitizenContact() {
        System.out.println("Testing updateCitizenContact()...");
        
        Optional<Citizen> citizenOpt = citizenDAO.findByCitizenId(TEST_CITIZEN_ID);
        
        if (citizenOpt.isPresent()) {
            Citizen citizen = citizenOpt.get();
            String originalPhone = citizen.getPhone();
            String originalEmail = citizen.getEmail();
            String newPhone = "9999999999";
            String newEmail = "updated.test@test.com";
            
            boolean result = citizenDAO.updateCitizenContact(citizen.getId(), newPhone, newEmail);
            
            if (result) {
                System.out.println("✓ Update citizen contact test passed");
                
                // Restore original contact info
                citizenDAO.updateCitizenContact(citizen.getId(), originalPhone, originalEmail);
            } else {
                System.out.println("✗ Update citizen contact test failed");
            }
        } else {
            System.out.println("✗ Update citizen contact test failed - no test citizen available");
        }
    }
    
    /**
     * Test getting citizen count
     */
    private void testGetCitizenCount() {
        System.out.println("Testing getCitizenCount()...");
        
        int count = citizenDAO.getCitizenCount();
        
        if (count > 0) {
            System.out.println("✓ Get citizen count test passed - Total citizens: " + count);
        } else {
            System.out.println("✗ Get citizen count test failed");
        }
    }
    
    /**
     * Test getting citizen count by ward
     */
    private void testGetCitizenCountByWard() {
        System.out.println("Testing getCitizenCountByWard()...");
        
        int count = citizenDAO.getCitizenCountByWard(TEST_WARD);
        
        if (count > 0) {
            System.out.println("✓ Get citizen count by ward test passed - Citizens in ward " + TEST_WARD + ": " + count);
        } else {
            System.out.println("✗ Get citizen count by ward test failed");
        }
    }
    
    /**
     * Test getting citizen count by gender
     */
    private void testGetCitizenCountByGender() {
        System.out.println("Testing getCitizenCountByGender()...");
        
        int count = citizenDAO.getCitizenCountByGender(TEST_GENDER);
        
        if (count > 0) {
            System.out.println("✓ Get citizen count by gender test passed - " + TEST_GENDER + " citizens: " + count);
        } else {
            System.out.println("✗ Get citizen count by gender test failed");
        }
    }
    
    /**
     * Test citizen ID existence check
     */
    private void testCitizenIdExists() {
        System.out.println("Testing citizenIdExists()...");
        
        boolean exists = citizenDAO.citizenIdExists(TEST_CITIZEN_ID);
        
        if (exists) {
            System.out.println("✓ Citizen ID exists test passed");
        } else {
            System.out.println("✗ Citizen ID exists test failed");
        }
        
        // Test with non-existent citizen ID
        boolean notExists = citizenDAO.citizenIdExists("NONEXISTENT123");
        if (!notExists) {
            System.out.println("✓ Citizen ID not exists test passed");
        } else {
            System.out.println("✗ Citizen ID not exists test failed");
        }
    }
    
    /**
     * Test email existence check
     */
    private void testEmailExists() {
        System.out.println("Testing emailExists()...");
        
        boolean exists = citizenDAO.emailExists(TEST_EMAIL);
        
        if (exists) {
            System.out.println("✓ Email exists test passed");
        } else {
            System.out.println("✗ Email exists test failed");
        }
        
        // Test with non-existent email
        boolean notExists = citizenDAO.emailExists("nonexistent@test.com");
        if (!notExists) {
            System.out.println("✓ Email not exists test passed");
        } else {
            System.out.println("✗ Email not exists test failed");
        }
    }
    
    /**
     * Test citizen deletion
     */
    private void testDeleteCitizen() {
        System.out.println("Testing deleteCitizen()...");
        
        Optional<Citizen> citizenOpt = citizenDAO.findByCitizenId(TEST_CITIZEN_ID);
        
        if (citizenOpt.isPresent()) {
            Citizen citizen = citizenOpt.get();
            boolean result = citizenDAO.deleteCitizen(citizen.getId());
            
            if (result) {
                // Verify deletion
                Optional<Citizen> deletedCitizen = citizenDAO.findById(citizen.getId());
                if (!deletedCitizen.isPresent()) {
                    System.out.println("✓ Delete citizen test passed");
                } else {
                    System.out.println("✗ Delete citizen test failed - citizen still exists");
                }
            } else {
                System.out.println("✗ Delete citizen test failed");
            }
        } else {
            System.out.println("✗ Delete citizen test failed - no test citizen available");
        }
    }
    
    /**
     * Clean up test data
     */
    private void cleanupTestData() {
        try {
            Connection conn = MySqlConnection.openConnection();
            if (conn != null) {
                String sql = "DELETE FROM citizens WHERE citizen_id = ? OR email = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, TEST_CITIZEN_ID);
                pstmt.setString(2, TEST_EMAIL);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            // Ignore cleanup errors
        }
    }
} 