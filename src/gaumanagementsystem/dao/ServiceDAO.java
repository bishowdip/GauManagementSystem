package gaumanagementsystem.dao;

import gaumanagementsystem.model.Service;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author bishodip
 */
public interface ServiceDAO {
    
    /**
     * Create a new service request
     * @param service Service object to be created
     * @return true if service was created successfully, false otherwise
     */
    boolean createService(Service service);
    
    /**
     * Find service by ID
     * @param serviceId Service ID
     * @return Optional containing Service if found, empty Optional otherwise
     */
    Optional<Service> findById(int serviceId);
    
    /**
     * Get all services
     * @return List of all services
     */
    List<Service> getAllServices();
    
    /**
     * Get services by status
     * @param status Service status (Pending, In Progress, Completed)
     * @return List of services with specified status
     */
    List<Service> getServicesByStatus(String status);
    
    /**
     * Get services by ward
     * @param ward Ward number
     * @return List of services in specified ward
     */
    List<Service> getServicesByWard(int ward);
    
    /**
     * Get services by citizen name
     * @param citizenName Name of citizen
     * @return List of services requested by specified citizen
     */
    List<Service> getServicesByCitizen(String citizenName);
    
    /**
     * Search services by service name
     * @param serviceName Service name or part of name to search
     * @return List of services matching the search criteria
     */
    List<Service> searchByServiceName(String serviceName);
    
    /**
     * Update service information
     * @param service Service object with updated information
     * @return true if update successful, false otherwise
     */
    boolean updateService(Service service);
    
    /**
     * Update service status
     * @param serviceId Service ID
     * @param newStatus New status
     * @return true if status updated successfully, false otherwise
     */
    boolean updateServiceStatus(int serviceId, String newStatus);
    
    /**
     * Delete service by ID
     * @param serviceId Service ID to delete
     * @return true if deletion successful, false otherwise
     */
    boolean deleteService(int serviceId);
    
    /**
     * Get total count of services
     * @return Total number of services
     */
    int getServiceCount();
    
    /**
     * Get count of services by status
     * @param status Service status
     * @return Number of services with specified status
     */
    int getServiceCountByStatus(String status);
    
    /**
     * Get count of services by ward
     * @param ward Ward number
     * @return Number of services in specified ward
     */
    int getServiceCountByWard(int ward);
    
    /**
     * Get services submitted in date range
     * @param startDate Start date
     * @param endDate End date
     * @return List of services submitted between dates
     */
    List<Service> getServicesByDateRange(java.sql.Date startDate, java.sql.Date endDate);
} 