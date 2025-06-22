/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
*/


package gaumanagementsystem.dao;

import gaumanagementsystem.database.MySqlConnection;
import gaumanagementsystem.model.User;
import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author bishodip
 */
public interface UserDAO {
    
    /**
     * Create a new user in the database
     * @param user User object to be created
     * @return true if user was created successfully, false otherwise
     */
    boolean createUser(User user);
    
    /**
     * Authenticate user with email and password
     * @param email User's email
     * @param password User's password
     * @return User object if authentication successful, null otherwise
     */
    User authenticateUser(String email, String password);
    
    /**
     * Find user by ID
     * @param id User ID
     * @return Optional containing User if found, empty Optional otherwise
     */
    Optional<User> findById(int id);
    
    /**
     * Find user by email
     * @param email User's email
     * @return Optional containing User if found, empty Optional otherwise
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Get all users from database
     * @return List of all users
     */
    List<User> getAllUsers();
    
    /**
     * Get users by role
     * @param role User role (admin/user)
     * @return List of users with specified role
     */
    List<User> getUsersByRole(String role);
    
    /**
     * Update user information
     * @param user User object with updated information
     * @return true if update successful, false otherwise
     */
    boolean updateUser(User user);
    
    /**
     * Update user password
     * @param userId User ID
     * @param newPassword New password
     * @return true if password updated successfully, false otherwise
     */
    boolean updatePassword(int userId, String newPassword);
    
    /**
     * Update user role
     * @param userId User ID
     * @param newRole New role
     * @return true if role updated successfully, false otherwise
     */
    boolean updateRole(int userId, String newRole);
    
    /**
     * Delete user by ID
     * @param id User ID to delete
     * @return true if deletion successful, false otherwise
     */
    boolean deleteUser(int id);
    
    /**
     * Check if email already exists
     * @param email Email to check
     * @return true if email exists, false otherwise
     */
    boolean emailExists(String email);
    
    /**
     * Get total count of users
     * @return Total number of users
     */
    int getUserCount();
    
    /**
     * Get count of users by role
     * @param role User role
     * @return Number of users with specified role
     */
    int getUserCountByRole(String role);
}
    
   


