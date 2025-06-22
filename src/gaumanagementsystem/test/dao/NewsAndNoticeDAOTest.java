package gaumanagementsystem.test.dao;

import gaumanagementsystem.dao.NewsAndNoticeDAO;
import gaumanagementsystem.dao.impl.NewsAndNoticeDAOImpl;
import gaumanagementsystem.model.NewsAndNotice;
import gaumanagementsystem.database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Unit tests for NewsAndNoticeDAO interface and NewsAndNoticeDAOImpl
 * Tests all CRUD operations and business logic methods
 * 
 * @author Test Suite
 */
public class NewsAndNoticeDAOTest {
    
    private NewsAndNoticeDAO newsAndNoticeDAO;
    private static final String TEST_SUBJECT = "Test News Subject";
    private static final String TEST_DESCRIPTION = "Test news description for testing purposes";
    private static final String TEST_TYPE = "News";
    private static final String TEST_AUDIENCE = "Public";
    
    public NewsAndNoticeDAOTest() {
        this.newsAndNoticeDAO = new NewsAndNoticeDAOImpl();
    }
    
    /**
     * Run all tests
     */
    public static void main(String[] args) {
        NewsAndNoticeDAOTest test = new NewsAndNoticeDAOTest();
        
        System.out.println("=== NewsAndNoticeDAO Test Suite ===");
        System.out.println("Starting NewsAndNoticeDAO tests...\n");
        
        try {
            // Setup test environment
            test.setupTestEnvironment();
            
            // Run all tests
            test.testCreateNewsAndNotice();
            test.testFindById();
            test.testGetAllNewsAndNotices();
            test.testGetNewsAndNoticesByType();
            test.testGetNewsAndNoticesByAudience();
            test.testSearchBySubject();
            test.testSearchByDescription();
            test.testGetActiveNewsAndNotices();
            test.testGetExpiredNewsAndNotices();
            test.testUpdateNewsAndNotice();
            test.testGetNewsAndNoticeCount();
            test.testGetNewsAndNoticeCountByType();
            test.testDeleteNewsAndNotice();
            
            // Cleanup
            test.cleanupTestData();
            
            System.out.println("\n=== All NewsAndNoticeDAO Tests Completed ===");
            
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
     * Test news and notice creation
     */
    private void testCreateNewsAndNotice() {
        System.out.println("Testing createNewsAndNotice()...");
        
        NewsAndNotice testNewsAndNotice = new NewsAndNotice();
        testNewsAndNotice.setSubject(TEST_SUBJECT);
        testNewsAndNotice.setDescription(TEST_DESCRIPTION);
        testNewsAndNotice.setType(TEST_TYPE);
        testNewsAndNotice.setAudience(TEST_AUDIENCE);
        testNewsAndNotice.setPublishDate(new Date(System.currentTimeMillis()));
        testNewsAndNotice.setExpiryDate(new Date(System.currentTimeMillis() + 86400000L * 30)); // 30 days later
        
        boolean result = newsAndNoticeDAO.createNewsAndNotice(testNewsAndNotice);
        
        if (result) {
            System.out.println("✓ News and notice creation test passed");
        } else {
            System.out.println("✗ News and notice creation test failed");
        }
    }
    
    /**
     * Test finding news and notice by ID
     */
    private void testFindById() {
        System.out.println("Testing findById()...");
        
        // First get all news and notices to find our test item
        List<NewsAndNotice> newsAndNotices = newsAndNoticeDAO.getAllNewsAndNotices();
        NewsAndNotice testNewsAndNotice = null;
        
        for (NewsAndNotice item : newsAndNotices) {
            if (TEST_SUBJECT.equals(item.getSubject())) {
                testNewsAndNotice = item;
                break;
            }
        }
        
        if (testNewsAndNotice != null) {
            Optional<NewsAndNotice> foundItem = newsAndNoticeDAO.findById(testNewsAndNotice.getId());
            
            if (foundItem.isPresent() && foundItem.get().getSubject().equals(TEST_SUBJECT)) {
                System.out.println("✓ Find by ID test passed");
            } else {
                System.out.println("✗ Find by ID test failed");
            }
        } else {
            System.out.println("✗ Find by ID test failed - no test item available");
        }
        
        // Test with non-existent ID
        Optional<NewsAndNotice> nonExistentItem = newsAndNoticeDAO.findById(99999);
        if (!nonExistentItem.isPresent()) {
            System.out.println("✓ Find by non-existent ID test passed");
        } else {
            System.out.println("✗ Find by non-existent ID test failed");
        }
    }
    
    /**
     * Test getting all news and notices
     */
    private void testGetAllNewsAndNotices() {
        System.out.println("Testing getAllNewsAndNotices()...");
        
        List<NewsAndNotice> allItems = newsAndNoticeDAO.getAllNewsAndNotices();
        
        if (allItems != null && allItems.size() > 0) {
            System.out.println("✓ Get all news and notices test passed - Found " + allItems.size() + " items");
        } else {
            System.out.println("✗ Get all news and notices test failed");
        }
    }
    
    /**
     * Test getting news and notices by type
     */
    private void testGetNewsAndNoticesByType() {
        System.out.println("Testing getNewsAndNoticesByType()...");
        
        List<NewsAndNotice> itemsByType = newsAndNoticeDAO.getNewsAndNoticesByType(TEST_TYPE);
        
        if (itemsByType != null && itemsByType.size() > 0) {
            System.out.println("✓ Get news and notices by type test passed - Found " + itemsByType.size() + " items of type " + TEST_TYPE);
        } else {
            System.out.println("✗ Get news and notices by type test failed");
        }
    }
    
    /**
     * Test getting news and notices by audience
     */
    private void testGetNewsAndNoticesByAudience() {
        System.out.println("Testing getNewsAndNoticesByAudience()...");
        
        List<NewsAndNotice> itemsByAudience = newsAndNoticeDAO.getNewsAndNoticesByAudience(TEST_AUDIENCE);
        
        if (itemsByAudience != null && itemsByAudience.size() > 0) {
            System.out.println("✓ Get news and notices by audience test passed - Found " + itemsByAudience.size() + " items for audience " + TEST_AUDIENCE);
        } else {
            System.out.println("✗ Get news and notices by audience test failed");
        }
    }
    
    /**
     * Test searching news and notices by subject
     */
    private void testSearchBySubject() {
        System.out.println("Testing searchBySubject()...");
        
        List<NewsAndNotice> searchResults = newsAndNoticeDAO.searchBySubject("Test");
        
        if (searchResults != null && searchResults.size() > 0) {
            System.out.println("✓ Search by subject test passed - Found " + searchResults.size() + " items");
        } else {
            System.out.println("✗ Search by subject test failed");
        }
    }
    
    /**
     * Test searching news and notices by description
     */
    private void testSearchByDescription() {
        System.out.println("Testing searchByDescription()...");
        
        List<NewsAndNotice> searchResults = newsAndNoticeDAO.searchByDescription("testing");
        
        if (searchResults != null && searchResults.size() > 0) {
            System.out.println("✓ Search by description test passed - Found " + searchResults.size() + " items");
        } else {
            System.out.println("✗ Search by description test failed");
        }
    }
    
    /**
     * Test getting active news and notices
     */
    private void testGetActiveNewsAndNotices() {
        System.out.println("Testing getActiveNewsAndNotices()...");
        
        List<NewsAndNotice> activeItems = newsAndNoticeDAO.getActiveNewsAndNotices();
        
        if (activeItems != null && activeItems.size() > 0) {
            System.out.println("✓ Get active news and notices test passed - Found " + activeItems.size() + " active items");
        } else {
            System.out.println("✗ Get active news and notices test failed");
        }
    }
    
    /**
     * Test getting expired news and notices
     */
    private void testGetExpiredNewsAndNotices() {
        System.out.println("Testing getExpiredNewsAndNotices()...");
        
        List<NewsAndNotice> expiredItems = newsAndNoticeDAO.getExpiredNewsAndNotices();
        
        if (expiredItems != null) {
            System.out.println("✓ Get expired news and notices test passed - Found " + expiredItems.size() + " expired items");
        } else {
            System.out.println("✗ Get expired news and notices test failed");
        }
    }
    
    /**
     * Test updating news and notice information
     */
    private void testUpdateNewsAndNotice() {
        System.out.println("Testing updateNewsAndNotice()...");
        
        List<NewsAndNotice> items = newsAndNoticeDAO.getAllNewsAndNotices();
        NewsAndNotice testItem = null;
        
        for (NewsAndNotice item : items) {
            if (TEST_SUBJECT.equals(item.getSubject())) {
                testItem = item;
                break;
            }
        }
        
        if (testItem != null) {
            String originalDescription = testItem.getDescription();
            testItem.setDescription("Updated description for testing");
            
            boolean result = newsAndNoticeDAO.updateNewsAndNotice(testItem);
            
            if (result) {
                // Verify the update
                Optional<NewsAndNotice> updatedItem = newsAndNoticeDAO.findById(testItem.getId());
                if (updatedItem.isPresent() && "Updated description for testing".equals(updatedItem.get().getDescription())) {
                    System.out.println("✓ Update news and notice test passed");
                    
                    // Restore original description
                    testItem.setDescription(originalDescription);
                    newsAndNoticeDAO.updateNewsAndNotice(testItem);
                } else {
                    System.out.println("✗ Update news and notice test failed - update not reflected");
                }
            } else {
                System.out.println("✗ Update news and notice test failed");
            }
        } else {
            System.out.println("✗ Update news and notice test failed - no test item available");
        }
    }
    
    /**
     * Test getting news and notice count
     */
    private void testGetNewsAndNoticeCount() {
        System.out.println("Testing getNewsAndNoticeCount()...");
        
        int count = newsAndNoticeDAO.getNewsAndNoticeCount();
        
        if (count > 0) {
            System.out.println("✓ Get news and notice count test passed - Total items: " + count);
        } else {
            System.out.println("✗ Get news and notice count test failed");
        }
    }
    
    /**
     * Test getting news and notice count by type
     */
    private void testGetNewsAndNoticeCountByType() {
        System.out.println("Testing getNewsAndNoticeCountByType()...");
        
        int count = newsAndNoticeDAO.getNewsAndNoticeCountByType(TEST_TYPE);
        
        if (count > 0) {
            System.out.println("✓ Get news and notice count by type test passed - Items of type " + TEST_TYPE + ": " + count);
        } else {
            System.out.println("✗ Get news and notice count by type test failed");
        }
    }
    
    /**
     * Test news and notice deletion
     */
    private void testDeleteNewsAndNotice() {
        System.out.println("Testing deleteNewsAndNotice()...");
        
        List<NewsAndNotice> items = newsAndNoticeDAO.getAllNewsAndNotices();
        NewsAndNotice testItem = null;
        
        for (NewsAndNotice item : items) {
            if (TEST_SUBJECT.equals(item.getSubject())) {
                testItem = item;
                break;
            }
        }
        
        if (testItem != null) {
            boolean result = newsAndNoticeDAO.deleteNewsAndNotice(testItem.getId());
            
            if (result) {
                // Verify deletion
                Optional<NewsAndNotice> deletedItem = newsAndNoticeDAO.findById(testItem.getId());
                if (!deletedItem.isPresent()) {
                    System.out.println("✓ Delete news and notice test passed");
                } else {
                    System.out.println("✗ Delete news and notice test failed - item still exists");
                }
            } else {
                System.out.println("✗ Delete news and notice test failed");
            }
        } else {
            System.out.println("✗ Delete news and notice test failed - no test item available");
        }
    }
    
    /**
     * Clean up test data
     */
    private void cleanupTestData() {
        try {
            Connection conn = MySqlConnection.openConnection();
            if (conn != null) {
                String sql = "DELETE FROM news_and_notices WHERE subject = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, TEST_SUBJECT);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
        } catch (SQLException e) {
            // Ignore cleanup errors
        }
    }
} 