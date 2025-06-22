package gaumanagementsystem.test.dao;

import gaumanagementsystem.dao.UserDAO;
import gaumanagementsystem.dao.impl.UserDAOImpl;
import gaumanagementsystem.model.User;
import gaumanagementsystem.database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Unit tests for UserDAO interface and UserDAOImpl
 * Tests all CRUD operations and business logic methods
 * 
 * @author Test Suite
 */
public class UserDAOTest {
    
    private UserDAO userDAO;
    private static final String TEST_EMAIL = "test.user@test.com";
    private static final String TEST_PASSWORD = "TestPassword123!";
    private static final String TEST_ROLE = "User";
    
    public UserDAOTest() {
        this.userDAO = new UserDAOImpl();
    }
    
    /**
     * Run all tests
     */
    public static void main(String[] args) {
        UserDAOTest test = new UserDAOTest();
        
        System.out.println("=== UserDAO Test Suite ===");
        System.out.println("Starting UserDAO tests...\n");
        
        try {
            // Setup test environment
            test.setupTestEnvironment();
            
            // Run all tests
            test.testCreateUser();
            test.testAuthenticateUser();
            test.testFindById();
            test.testFindByEmail();
            test.testGetAllUsers();
            test.testGetUsersByRole();
            test.testUpdateUser();
            test.testUpdatePassword();
            test.testUpdateRole();
            test.testEmailExists();
            test.testGetUserCount();
            test.testGetUserCountByRole();
            test.testDeleteUser();
            
            // Cleanup
            test.cleanupTestData();
            
            System.out.println("\n=== All UserDAO Tests Completed ===");
            
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
     * Test user creation
     */
    private void testCreateUser() {
        System.out.println("Testing createUser()...");
        
        User testUser = new User();
        testUser.setEmail(TEST_EMAIL);
        testUser.setPassword(TEST_PASSWORD);
        testUser.setRole(TEST_ROLE);
        
        boolean result = userDAO.createUser(testUser);
        
        if (result) {
            System.out.println("✓ User creation test passed");
        } else {
            System.out.println("✗ User creation test failed");
        }
    }
    
    /**
     * Test user authentication
     */
    private void testAuthenticateUser() {
        System.out.println("Testing authenticateUser()...");
        
        // Test valid credentials
        User authenticatedUser = userDAO.authenticateUser(TEST_EMAIL, TEST_PASSWORD);
        
        if (authenticatedUser != null && TEST_EMAIL.equals(authenticatedUser.getEmail())) {
            System.out.println("✓ Valid authentication test passed");
        } else {
            System.out.println("✗ Valid authentication test failed");
        }
        
        // Test invalid credentials
        User invalidUser = userDAO.authenticateUser(TEST_EMAIL, "wrongpassword");
        
        if (invalidUser == null) {
            System.out.println("✓ Invalid authentication test passed");
        } else {
            System.out.println("✗ Invalid authentication test failed");
        }
    }
    
    /**
     * Test finding user by ID
     */
    private void testFindById() {
        System.out.println("Testing findById()...");
        
        // First get a user to test with
        User testUser = userDAO.authenticateUser(TEST_EMAIL, TEST_PASSWORD);
        
        if (testUser != null) {
            Optional<User> foundUser = userDAO.findById(testUser.getId());
            
            if (foundUser.isPresent() && foundUser.get().getEmail().equals(TEST_EMAIL)) {
                System.out.println("✓ Find by ID test passed");
            } else {
                System.out.println("✗ Find by ID test failed");
            }
        } else {
            System.out.println("✗ Find by ID test failed - no test user available");
        }
        
        // Test with non-existent ID
        Optional<User> nonExistentUser = userDAO.findById(99999);
        if (!nonExistentUser.isPresent()) {
            System.out.println("✓ Find by non-existent ID test passed");
        } else {
            System.out.println("✗ Find by non-existent ID test failed");
        }
    }
    
    /**
     * Test finding user by email
     */
    private void testFindByEmail() {
        System.out.println("Testing findByEmail()...");
        
        Optional<User> foundUser = userDAO.findByEmail(TEST_EMAIL);
        
        if (foundUser.isPresent() && foundUser.get().getEmail().equals(TEST_EMAIL)) {
            System.out.println("✓ Find by email test passed");
        } else {
            System.out.println("✗ Find by email test failed");
        }
        
        // Test with non-existent email
        Optional<User> nonExistentUser = userDAO.findByEmail("nonexistent@test.com");
        if (!nonExistentUser.isPresent()) {
            System.out.println("✓ Find by non-existent email test passed");
        } else {
            System.out.println("✗ Find by non-existent email test failed");
        }
    }
    
    /**
     * Test getting all users
     */
    private void testGetAllUsers() {
        System.out.println("Testing getAllUsers()...");
        
        List<User> allUsers = userDAO.getAllUsers();
        
        if (allUsers != null && allUsers.size() > 0) {
            System.out.println("✓ Get all users test passed - Found " + allUsers.size() + " users");
        } else {
            System.out.println("✗ Get all users test failed");
        }
    }
    
    /**
     * Test getting users by role
     */
    private void testGetUsersByRole() {
        System.out.println("Testing getUsersByRole()...");
        
        List<User> usersByRole = userDAO.getUsersByRole(TEST_ROLE);
        
        if (usersByRole != null && usersByRole.size() > 0) {
            System.out.println("✓ Get users by role test passed - Found " + usersByRole.size() + " users with role " + TEST_ROLE);
        } else {
            System.out.println("✗ Get users by role test failed");
        }
    }
    
    /**
     * Test updating user information
     */
    private void testUpdateUser() {
        System.out.println("Testing updateUser()...");
        
        Optional<User> userOpt = userDAO.findByEmail(TEST_EMAIL);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String originalRole = user.getRole();
            user.setRole("UpdatedRole");
            
            boolean result = userDAO.updateUser(user);
            
            if (result) {
                // Verify the update
                Optional<User> updatedUser = userDAO.findById(user.getId());
                if (updatedUser.isPresent() && "UpdatedRole".equals(updatedUser.get().getRole())) {
                    System.out.println("✓ Update user test passed");
                    
                    // Restore original role
                    user.setRole(originalRole);
                    userDAO.updateUser(user);
                } else {
                    System.out.println("✗ Update user test failed - update not reflected");
                }
            } else {
                System.out.println("✗ Update user test failed");
            }
        } else {
            System.out.println("✗ Update user test failed - no test user available");
        }
    }
    
    /**
     * Test updating user password
     */
    private void testUpdatePassword() {
        System.out.println("Testing updatePassword()...");
        
        Optional<User> userOpt = userDAO.findByEmail(TEST_EMAIL);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String newPassword = "NewPassword123!";
            
            boolean result = userDAO.updatePassword(user.getId(), newPassword);
            
            if (result) {
                // Test authentication with new password
                User authUser = userDAO.authenticateUser(TEST_EMAIL, newPassword);
                if (authUser != null) {
                    System.out.println("✓ Update password test passed");
                    
                    // Restore original password
                    userDAO.updatePassword(user.getId(), TEST_PASSWORD);
                } else {
                    System.out.println("✗ Update password test failed - authentication with new password failed");
                }
            } else {
                System.out.println("✗ Update password test failed");
            }
        } else {
            System.out.println("✗ Update password test failed - no test user available");
        }
    }
    
    /**
     * Test updating user role
     */
    private void testUpdateRole() {
        System.out.println("Testing updateRole()...");
        
        Optional<User> userOpt = userDAO.findByEmail(TEST_EMAIL);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String originalRole = user.getRole();
            String newRole = "Admin";
            
            boolean result = userDAO.updateRole(user.getId(), newRole);
            
            if (result) {
                // Verify the role update
                Optional<User> updatedUser = userDAO.findById(user.getId());
                if (updatedUser.isPresent() && newRole.equals(updatedUser.get().getRole())) {
                    System.out.println("✓ Update role test passed");
                    
                    // Restore original role
                    userDAO.updateRole(user.getId(), originalRole);
                } else {
                    System.out.println("✗ Update role test failed - role not updated");
                }
            } else {
                System.out.println("✗ Update role test failed");
            }
        } else {
            System.out.println("✗ Update role test failed - no test user available");
        }
    }
    
    /**
     * Test email existence check
     */
    private void testEmailExists() {
        System.out.println("Testing emailExists()...");
        
        boolean exists = userDAO.emailExists(TEST_EMAIL);
        
        if (exists) {
            System.out.println("✓ Email exists test passed");
        } else {
            System.out.println("✗ Email exists test failed");
        }
        
        // Test with non-existent email
        boolean notExists = userDAO.emailExists("nonexistent@test.com");
        if (!notExists) {
            System.out.println("✓ Email not exists test passed");
        } else {
            System.out.println("✗ Email not exists test failed");
        }
    }
    
    /**
     * Test getting user count
     */
    private void testGetUserCount() {
        System.out.println("Testing getUserCount()...");
        
        int count = userDAO.getUserCount();
        
        if (count > 0) {
            System.out.println("✓ Get user count test passed - Total users: " + count);
        } else {
            System.out.println("✗ Get user count test failed");
        }
    }
    
    /**
     * Test getting user count by role
     */
    private void testGetUserCountByRole() {
        System.out.println("Testing getUserCountByRole()...");
        
        int count = userDAO.getUserCountByRole(TEST_ROLE);
        
        if (count > 0) {
            System.out.println("✓ Get user count by role test passed - Users with role " + TEST_ROLE + ": " + count);
        } else {
            System.out.println("✗ Get user count by role test failed");
        }
    }
    
    /**
     * Test user deletion
     */
    private void testDeleteUser() {
        System.out.println("Testing deleteUser()...");
        
        Optional<User> userOpt = userDAO.findByEmail(TEST_EMAIL);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            boolean result = userDAO.deleteUser(user.getId());
            
            if (result) {
                // Verify deletion
                Optional<User> deletedUser = userDAO.findById(user.getId());
                if (!deletedUser.isPresent()) {
                    System.out.println("✓ Delete user test passed");
                } else {
                    System.out.println("✗ Delete user test failed - user still exists");
                }
            } else {
                System.out.println("✗ Delete user test failed");
            }
        } else {
            System.out.println("✗ Delete user test failed - no test user available");
        }
    }
    
    /**
     * Clean up test data
     */
    private void cleanupTestData() {
        try {
            Connection conn = MySqlConnection.openConnection();
            if (conn != null) {
                String sql = "DELETE FROM users WHERE email = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, TEST_EMAIL);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            // Ignore cleanup errors
        }
    }
} 