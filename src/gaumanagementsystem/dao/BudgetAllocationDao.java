/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.dao;

import gaumanagementsystem.database.MySqlConnection;
import gaumanagementsystem.model.BudgetAllocation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Budget Allocation operations
 * Handles only database operations - no business logic
 * @author bisho
 */
public class BudgetAllocationDao {
    
    private MySqlConnection dbConnection;
    
    public BudgetAllocationDao() {
        dbConnection = new MySqlConnection();
    }
    
    /**
     * Get budget allocations grouped by category from project data
     * @return List of BudgetAllocation objects
     * @throws SQLException if database operation fails
     */
    public List<BudgetAllocation> getBudgetAllocationsByCategory() throws SQLException {
        List<BudgetAllocation> allocations = new ArrayList<>();
        String query = "SELECT category, SUM(amount) as total_amount, COUNT(*) as project_count " +
                      "FROM projects " +
                      "WHERE amount IS NOT NULL AND amount > 0 " +
                      "GROUP BY category " +
                      "ORDER BY total_amount DESC";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                throw new SQLException("Unable to establish database connection");
            }
            
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String category = rs.getString("category");
                Double totalAmount = rs.getDouble("total_amount");
                Integer projectCount = rs.getInt("project_count");
                
                allocations.add(new BudgetAllocation(category, totalAmount, projectCount));
            }
            
        } finally {
            // Clean up resources
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { /* ignore */ }
            if (conn != null) dbConnection.closeConnection(conn);
        }
        
        return allocations;
    }
    
    /**
     * Get total budget amount across all categories
     * @return Total budget amount
     * @throws SQLException if database operation fails
     */
    public Double getTotalBudgetAmount() throws SQLException {
        String query = "SELECT SUM(amount) as total_budget FROM projects WHERE amount IS NOT NULL AND amount > 0";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                throw new SQLException("Unable to establish database connection");
            }
            
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total_budget");
            }
            
            return 0.0;
            
        } finally {
            // Clean up resources
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { /* ignore */ }
            if (conn != null) dbConnection.closeConnection(conn);
        }
    }
    
    /**
     * Get budget allocation for a specific category
     * @param category Category name
     * @return BudgetAllocation object or null if not found
     * @throws SQLException if database operation fails
     */
    public BudgetAllocation getBudgetAllocationByCategory(String category) throws SQLException {
        String query = "SELECT category, SUM(amount) as total_amount, COUNT(*) as project_count " +
                      "FROM projects " +
                      "WHERE category = ? AND amount IS NOT NULL AND amount > 0 " +
                      "GROUP BY category";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                throw new SQLException("Unable to establish database connection");
            }
            
            stmt = conn.prepareStatement(query);
            stmt.setString(1, category);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                String cat = rs.getString("category");
                Double totalAmount = rs.getDouble("total_amount");
                Integer projectCount = rs.getInt("project_count");
                
                return new BudgetAllocation(cat, totalAmount, projectCount);
            }
            
            return null;
            
        } finally {
            // Clean up resources
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { /* ignore */ }
            if (conn != null) dbConnection.closeConnection(conn);
        }
    }
    
    /**
     * Get budget allocations grouped by ward
     * @return List of BudgetAllocation objects grouped by ward
     * @throws SQLException if database operation fails
     */
    public List<BudgetAllocation> getBudgetAllocationsByWard() throws SQLException {
        List<BudgetAllocation> allocations = new ArrayList<>();
        String query = "SELECT ward as category, SUM(amount) as total_amount, COUNT(*) as project_count " +
                      "FROM projects " +
                      "WHERE amount IS NOT NULL AND amount > 0 " +
                      "GROUP BY ward " +
                      "ORDER BY total_amount DESC";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                throw new SQLException("Unable to establish database connection");
            }
            
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String ward = rs.getString("category");
                Double totalAmount = rs.getDouble("total_amount");
                Integer projectCount = rs.getInt("project_count");
                
                allocations.add(new BudgetAllocation("Ward " + ward, totalAmount, projectCount));
            }
            
        } finally {
            // Clean up resources
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { /* ignore */ }
            if (conn != null) dbConnection.closeConnection(conn);
        }
        
        return allocations;
    }
    
    /**
     * Get budget allocations by status
     * @return List of BudgetAllocation objects grouped by status
     * @throws SQLException if database operation fails
     */
    public List<BudgetAllocation> getBudgetAllocationsByStatus() throws SQLException {
        List<BudgetAllocation> allocations = new ArrayList<>();
        String query = "SELECT status as category, SUM(amount) as total_amount, COUNT(*) as project_count " +
                      "FROM projects " +
                      "WHERE amount IS NOT NULL AND amount > 0 " +
                      "GROUP BY status " +
                      "ORDER BY total_amount DESC";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                throw new SQLException("Unable to establish database connection");
            }
            
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String status = rs.getString("category");
                Double totalAmount = rs.getDouble("total_amount");
                Integer projectCount = rs.getInt("project_count");
                
                allocations.add(new BudgetAllocation(status + " Projects", totalAmount, projectCount));
            }
            
        } finally {
            // Clean up resources
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { /* ignore */ }
            if (conn != null) dbConnection.closeConnection(conn);
        }
        
        return allocations;
    }
    
    /**
     * Check if projects table exists and has required columns
     * @return true if table structure is valid
     */
    public boolean validateProjectsTable() {
        String query = "SHOW COLUMNS FROM projects";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                return false;
            }
            
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            boolean hasCategory = false;
            boolean hasAmount = false;
            
            while (rs.next()) {
                String columnName = rs.getString("Field");
                if ("category".equalsIgnoreCase(columnName)) {
                    hasCategory = true;
                }
                if ("amount".equalsIgnoreCase(columnName)) {
                    hasAmount = true;
                }
            }
            
            return hasCategory && hasAmount;
            
        } catch (SQLException e) {
            System.err.println("Error validating projects table: " + e.getMessage());
            return false;
        } finally {
            // Clean up resources
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { /* ignore */ }
            if (conn != null) dbConnection.closeConnection(conn);
        }
    }
    
    /**
     * Check if database connection is available
     * @return true if connection can be established
     */
    public boolean isConnectionAvailable() {
        Connection conn = null;
        try {
            conn = dbConnection.openConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        } finally {
            if (conn != null) {
                dbConnection.closeConnection(conn);
            }
        }
    }
    
    /**
     * Get project count by category
     * @return List of category names and their project counts
     * @throws SQLException if database operation fails
     */
    public List<Object[]> getProjectCountByCategory() throws SQLException {
        List<Object[]> counts = new ArrayList<>();
        String query = "SELECT category, COUNT(*) as project_count " +
                      "FROM projects " +
                      "GROUP BY category " +
                      "ORDER BY project_count DESC";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                throw new SQLException("Unable to establish database connection");
            }
            
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                String category = rs.getString("category");
                Integer projectCount = rs.getInt("project_count");
                counts.add(new Object[]{category, projectCount});
            }
            
        } finally {
            // Clean up resources
            if (rs != null) try { rs.close(); } catch (SQLException e) { /* ignore */ }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { /* ignore */ }
            if (conn != null) dbConnection.closeConnection(conn);
        }
        
        return counts;
    }
} 