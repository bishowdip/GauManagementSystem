package gaumanagementsystem.test.dao;

import gaumanagementsystem.dao.ServiceDAO;
import gaumanagementsystem.dao.impl.ServiceDAOImpl;
import gaumanagementsystem.model.Service;
import gaumanagementsystem.model.ServiceRequest;
import gaumanagementsystem.database.MySqlConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Unit tests for ServiceDAO interface and ServiceDAOImpl
 * Tests all CRUD operations and business logic methods
 * 
 * @author Test Suite
 */
public class ServiceDAOTest {
    
    private ServiceDAO serviceDAO;
    private static final String TEST_SERVICE_NAME = "Test Service";
    private static final String TEST_SERVICE_DESCRIPTION = "Test service description for testing purposes";
    private static final String TEST_SERVICE_CATEGORY = "Public Service";
    private static final String TEST_SERVICE_STATUS = "Active";
    private static final String TEST_CITIZEN_NAME = "Test Citizen";
    private static final String TEST_CITIZEN_EMAIL = "test.citizen@test.com";
    
    public ServiceDAOTest() {
        this.serviceDAO = new ServiceDAOImpl();
    }
    
    /**
     * Run all tests
     */
    public static void main(String[] args) {
        ServiceDAOTest test = new ServiceDAOTest();
        
        System.out.println("=== ServiceDAO Test Suite ===");
        System.out.println("Starting ServiceDAO tests...\n");
        
        try {
            // Setup test environment
            test.setupTestEnvironment();
            
            // Run all tests
            test.testCreateService();
            test.testFindServiceById();
            test.testGetAllServices();
            test.testGetServicesByCategory();
            test.testGetServicesByStatus();
            test.testSearchServicesByName();
            test.testUpdateService();
            test.testCreateServiceRequest();
            test.testGetAllServiceRequests();
            test.testGetServiceRequestsByStatus();
            test.testGetServiceRequestsByCitizen();
            test.testUpdateServiceRequestStatus();
            test.testGetServiceCount();
            test.testGetServiceRequestCount();
            test.testDeleteServiceRequest();
            test.testDeleteService();
            
            // Cleanup
            test.cleanupTestData();
            
            System.out.println("\n=== All ServiceDAO Tests Completed ===");
            
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
     * Test service creation
     */
    private void testCreateService() {
        System.out.println("Testing createService()...");
        
        Service testService = new Service();
        testService.setServiceName(TEST_SERVICE_NAME);
        testService.setDescription(TEST_SERVICE_DESCRIPTION);
        testService.setCategory(TEST_SERVICE_CATEGORY);
        testService.setStatus(TEST_SERVICE_STATUS);
        testService.setCreatedDate(new Date(System.currentTimeMillis()));
        
        boolean result = serviceDAO.createService(testService);
        
        if (result) {
            System.out.println("✓ Service creation test passed");
        } else {
            System.out.println("✗ Service creation test failed");
        }
    }
    
    /**
     * Test finding service by ID
     */
    private void testFindServiceById() {
        System.out.println("Testing findServiceById()...");
        
        // First get all services to find our test service
        List<Service> services = serviceDAO.getAllServices();
        Service testService = null;
        
        for (Service service : services) {
            if (TEST_SERVICE_NAME.equals(service.getServiceName())) {
                testService = service;
                break;
            }
        }
        
        if (testService != null) {
            Optional<Service> foundService = serviceDAO.findServiceById(testService.getServiceId());
            
            if (foundService.isPresent() && foundService.get().getServiceName().equals(TEST_SERVICE_NAME)) {
                System.out.println("✓ Find service by ID test passed");
            } else {
                System.out.println("✗ Find service by ID test failed");
            }
        } else {
            System.out.println("✗ Find service by ID test failed - no test service available");
        }
        
        // Test with non-existent ID
        Optional<Service> nonExistentService = serviceDAO.findServiceById(99999);
        if (!nonExistentService.isPresent()) {
            System.out.println("✓ Find service by non-existent ID test passed");
        } else {
            System.out.println("✗ Find service by non-existent ID test failed");
        }
    }
    
    /**
     * Test getting all services
     */
    private void testGetAllServices() {
        System.out.println("Testing getAllServices()...");
        
        List<Service> allServices = serviceDAO.getAllServices();
        
        if (allServices != null && allServices.size() > 0) {
            System.out.println("✓ Get all services test passed - Found " + allServices.size() + " services");
        } else {
            System.out.println("✗ Get all services test failed");
        }
    }
    
    /**
     * Test getting services by category
     */
    private void testGetServicesByCategory() {
        System.out.println("Testing getServicesByCategory()...");
        
        List<Service> servicesByCategory = serviceDAO.getServicesByCategory(TEST_SERVICE_CATEGORY);
        
        if (servicesByCategory != null && servicesByCategory.size() > 0) {
            System.out.println("✓ Get services by category test passed - Found " + servicesByCategory.size() + " services in category " + TEST_SERVICE_CATEGORY);
        } else {
            System.out.println("✗ Get services by category test failed");
        }
    }
    
    /**
     * Test getting services by status
     */
    private void testGetServicesByStatus() {
        System.out.println("Testing getServicesByStatus()...");
        
        List<Service> servicesByStatus = serviceDAO.getServicesByStatus(TEST_SERVICE_STATUS);
        
        if (servicesByStatus != null && servicesByStatus.size() > 0) {
            System.out.println("✓ Get services by status test passed - Found " + servicesByStatus.size() + " services with status " + TEST_SERVICE_STATUS);
        } else {
            System.out.println("✗ Get services by status test failed");
        }
    }
    
    /**
     * Test searching services by name
     */
    private void testSearchServicesByName() {
        System.out.println("Testing searchServicesByName()...");
        
        List<Service> searchResults = serviceDAO.searchServicesByName("Test");
        
        if (searchResults != null && searchResults.size() > 0) {
            System.out.println("✓ Search services by name test passed - Found " + searchResults.size() + " services");
        } else {
            System.out.println("✗ Search services by name test failed");
        }
    }
    
    /**
     * Test updating service information
     */
    private void testUpdateService() {
        System.out.println("Testing updateService()...");
        
        List<Service> services = serviceDAO.getAllServices();
        Service testService = null;
        
        for (Service service : services) {
            if (TEST_SERVICE_NAME.equals(service.getServiceName())) {
                testService = service;
                break;
            }
        }
        
        if (testService != null) {
            String originalDescription = testService.getDescription();
            testService.setDescription("Updated description for testing");
            
            boolean result = serviceDAO.updateService(testService);
            
            if (result) {
                // Verify the update
                Optional<Service> updatedService = serviceDAO.findServiceById(testService.getServiceId());
                if (updatedService.isPresent() && "Updated description for testing".equals(updatedService.get().getDescription())) {
                    System.out.println("✓ Update service test passed");
                    
                    // Restore original description
                    testService.setDescription(originalDescription);
                    serviceDAO.updateService(testService);
                } else {
                    System.out.println("✗ Update service test failed - update not reflected");
                }
            } else {
                System.out.println("✗ Update service test failed");
            }
        } else {
            System.out.println("✗ Update service test failed - no test service available");
        }
    }
    
    /**
     * Test service request creation
     */
    private void testCreateServiceRequest() {
        System.out.println("Testing createServiceRequest()...");
        
        // First get a service to request
        List<Service> services = serviceDAO.getAllServices();
        Service testService = null;
        
        for (Service service : services) {
            if (TEST_SERVICE_NAME.equals(service.getServiceName())) {
                testService = service;
                break;
            }
        }
        
        if (testService != null) {
            ServiceRequest testRequest = new ServiceRequest();
            testRequest.setServiceId(testService.getServiceId());
            testRequest.setCitizenName(TEST_CITIZEN_NAME);
            testRequest.setCitizenEmail(TEST_CITIZEN_EMAIL);
            testRequest.setRequestDate(new Date(System.currentTimeMillis()));
            testRequest.setStatus("Pending");
            testRequest.setDescription("Test service request");
            
            boolean result = serviceDAO.createServiceRequest(testRequest);
            
            if (result) {
                System.out.println("✓ Service request creation test passed");
            } else {
                System.out.println("✗ Service request creation test failed");
            }
        } else {
            System.out.println("✗ Service request creation test failed - no test service available");
        }
    }
    
    /**
     * Test getting all service requests
     */
    private void testGetAllServiceRequests() {
        System.out.println("Testing getAllServiceRequests()...");
        
        List<ServiceRequest> allRequests = serviceDAO.getAllServiceRequests();
        
        if (allRequests != null && allRequests.size() > 0) {
            System.out.println("✓ Get all service requests test passed - Found " + allRequests.size() + " requests");
        } else {
            System.out.println("✗ Get all service requests test failed");
        }
    }
    
    /**
     * Test getting service requests by status
     */
    private void testGetServiceRequestsByStatus() {
        System.out.println("Testing getServiceRequestsByStatus()...");
        
        List<ServiceRequest> requestsByStatus = serviceDAO.getServiceRequestsByStatus("Pending");
        
        if (requestsByStatus != null && requestsByStatus.size() > 0) {
            System.out.println("✓ Get service requests by status test passed - Found " + requestsByStatus.size() + " pending requests");
        } else {
            System.out.println("✗ Get service requests by status test failed");
        }
    }
    
    /**
     * Test getting service requests by citizen
     */
    private void testGetServiceRequestsByCitizen() {
        System.out.println("Testing getServiceRequestsByCitizen()...");
        
        List<ServiceRequest> requestsByCitizen = serviceDAO.getServiceRequestsByCitizen(TEST_CITIZEN_NAME);
        
        if (requestsByCitizen != null && requestsByCitizen.size() > 0) {
            System.out.println("✓ Get service requests by citizen test passed - Found " + requestsByCitizen.size() + " requests by " + TEST_CITIZEN_NAME);
        } else {
            System.out.println("✗ Get service requests by citizen test failed");
        }
    }
    
    /**
     * Test updating service request status
     */
    private void testUpdateServiceRequestStatus() {
        System.out.println("Testing updateServiceRequestStatus()...");
        
        List<ServiceRequest> requests = serviceDAO.getAllServiceRequests();
        ServiceRequest testRequest = null;
        
        for (ServiceRequest request : requests) {
            if (TEST_CITIZEN_NAME.equals(request.getCitizenName())) {
                testRequest = request;
                break;
            }
        }
        
        if (testRequest != null) {
            String originalStatus = testRequest.getStatus();
            String newStatus = "Approved";
            
            boolean result = serviceDAO.updateServiceRequestStatus(testRequest.getRequestId(), newStatus);
            
            if (result) {
                System.out.println("✓ Update service request status test passed");
                
                // Restore original status
                serviceDAO.updateServiceRequestStatus(testRequest.getRequestId(), originalStatus);
            } else {
                System.out.println("✗ Update service request status test failed");
            }
        } else {
            System.out.println("✗ Update service request status test failed - no test request available");
        }
    }
    
    /**
     * Test getting service count
     */
    private void testGetServiceCount() {
        System.out.println("Testing getServiceCount()...");
        
        int count = serviceDAO.getServiceCount();
        
        if (count > 0) {
            System.out.println("✓ Get service count test passed - Total services: " + count);
        } else {
            System.out.println("✗ Get service count test failed");
        }
    }
    
    /**
     * Test getting service request count
     */
    private void testGetServiceRequestCount() {
        System.out.println("Testing getServiceRequestCount()...");
        
        int count = serviceDAO.getServiceRequestCount();
        
        if (count > 0) {
            System.out.println("✓ Get service request count test passed - Total requests: " + count);
        } else {
            System.out.println("✗ Get service request count test failed");
        }
    }
    
    /**
     * Test service request deletion
     */
    private void testDeleteServiceRequest() {
        System.out.println("Testing deleteServiceRequest()...");
        
        List<ServiceRequest> requests = serviceDAO.getAllServiceRequests();
        ServiceRequest testRequest = null;
        
        for (ServiceRequest request : requests) {
            if (TEST_CITIZEN_NAME.equals(request.getCitizenName())) {
                testRequest = request;
                break;
            }
        }
        
        if (testRequest != null) {
            boolean result = serviceDAO.deleteServiceRequest(testRequest.getRequestId());
            
            if (result) {
                System.out.println("✓ Delete service request test passed");
            } else {
                System.out.println("✗ Delete service request test failed");
            }
        } else {
            System.out.println("✗ Delete service request test failed - no test request available");
        }
    }
    
    /**
     * Test service deletion
     */
    private void testDeleteService() {
        System.out.println("Testing deleteService()...");
        
        List<Service> services = serviceDAO.getAllServices();
        Service testService = null;
        
        for (Service service : services) {
            if (TEST_SERVICE_NAME.equals(service.getServiceName())) {
                testService = service;
                break;
            }
        }
        
        if (testService != null) {
            boolean result = serviceDAO.deleteService(testService.getServiceId());
            
            if (result) {
                // Verify deletion
                Optional<Service> deletedService = serviceDAO.findServiceById(testService.getServiceId());
                if (!deletedService.isPresent()) {
                    System.out.println("✓ Delete service test passed");
                } else {
                    System.out.println("✗ Delete service test failed - service still exists");
                }
            } else {
                System.out.println("✗ Delete service test failed");
            }
        } else {
            System.out.println("✗ Delete service test failed - no test service available");
        }
    }
    
    /**
     * Clean up test data
     */
    private void cleanupTestData() {
        try {
            Connection conn = MySqlConnection.openConnection();
            if (conn != null) {
                // Clean service requests first due to foreign key constraints
                String sql1 = "DELETE FROM service_requests WHERE citizen_name = ?";
                PreparedStatement pstmt1 = conn.prepareStatement(sql1);
                pstmt1.setString(1, TEST_CITIZEN_NAME);
                pstmt1.executeUpdate();
                pstmt1.close();
                
                // Clean services
                String sql2 = "DELETE FROM services WHERE service_name = ?";
                PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                pstmt2.setString(1, TEST_SERVICE_NAME);
                pstmt2.executeUpdate();
                pstmt2.close();
                
                conn.close();
            }
        } catch (SQLException e) {
            // Ignore cleanup errors
        }
    }
} 