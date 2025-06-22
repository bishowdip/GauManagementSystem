package gaumanagementsystem.dao;

import gaumanagementsystem.model.Citizen;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author bishodip
 */
public interface CitizenDAOInterface {
    
    /**
     * Create a new citizen profile
     * @param citizen Citizen object to be created
     * @return true if citizen was created successfully, false otherwise
     */
    boolean createCitizen(Citizen citizen);
    
    /**
     * Find citizen by ID
     * @param citizenId Citizen ID
     * @return Optional containing Citizen if found, empty Optional otherwise
     */
    Optional<Citizen> findById(int citizenId);
    
    /**
     * Find citizen by user ID
     * @param userId User ID
     * @return Optional containing Citizen if found, empty Optional otherwise
     */
    Optional<Citizen> findByUserId(int userId);
    
    /**
     * Find citizen by email
     * @param email Citizen's email
     * @return Optional containing Citizen if found, empty Optional otherwise
     */
    Optional<Citizen> findByEmail(String email);
    
    /**
     * Get all citizens
     * @return List of all citizens
     */
    List<Citizen> getAllCitizens();
    
    /**
     * Get citizens by ward
     * @param ward Ward number
     * @return List of citizens in specified ward
     */
    List<Citizen> getCitizensByWard(int ward);
    
    /**
     * Get citizens by gender
     * @param gender Gender (Male/Female)
     * @return List of citizens with specified gender
     */
    List<Citizen> getCitizensByGender(String gender);
    
    /**
     * Search citizens by name
     * @param name Name or part of name to search
     * @return List of citizens matching the search criteria
     */
    List<Citizen> searchByName(String name);
    
    /**
     * Update citizen information
     * @param citizen Citizen object with updated information
     * @return true if update successful, false otherwise
     */
    boolean updateCitizen(Citizen citizen);
    
    /**
     * Delete citizen by ID
     * @param citizenId Citizen ID to delete
     * @return true if deletion successful, false otherwise
     */
    boolean deleteCitizen(int citizenId);
    
    /**
     * Check if email already exists
     * @param email Email to check
     * @return true if email exists, false otherwise
     */
    boolean emailExists(String email);
    
    /**
     * Check if phone number already exists
     * @param phone Phone number to check
     * @return true if phone exists, false otherwise
     */
    boolean phoneExists(String phone);
    
    /**
     * Get total count of citizens
     * @return Total number of citizens
     */
    int getCitizenCount();
    
    /**
     * Get count of citizens by ward
     * @param ward Ward number
     * @return Number of citizens in specified ward
     */
    int getCitizenCountByWard(int ward);
    
    /**
     * Get count of citizens by gender
     * @param gender Gender
     * @return Number of citizens with specified gender
     */
    int getCitizenCountByGender(String gender);
} 