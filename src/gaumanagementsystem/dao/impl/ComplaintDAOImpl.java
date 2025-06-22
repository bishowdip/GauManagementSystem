package gaumanagementsystem.dao.impl;

import gaumanagementsystem.dao.ComplaintDAO;
import gaumanagementsystem.database.MySqlConnection;
import gaumanagementsystem.model.Complaint;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of ComplaintDAO interface
 * Handles all database operations for Complaint entity
 */
public class ComplaintDAOImpl implements ComplaintDAO {
    
    private final MySqlConnection dbConnection;
    
    public ComplaintDAOImpl() {
        this.dbConnection = new MySqlConnection();
    }
    
    @Override
    public boolean createComplaint(Complaint complaint) {
        String sql = "INSERT INTO complaints (name, date, email, description, category, " +
                    "status, feedback, ward, phone) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                System.err.println("Failed to establish database connection for creating complaint");
                return false;
            }
            
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, complaint.getName());
            stmt.setDate(2, complaint.getDate());
            stmt.setString(3, complaint.getEmail());
            stmt.setString(4, complaint.getDescription());
            stmt.setString(5, complaint.getCategory() != null ? complaint.getCategory() : "Complaint");
            stmt.setString(6, complaint.getStatus() != null ? complaint.getStatus() : "Pending");
            stmt.setString(7, complaint.getFeedback());
            
            // Handle ward - use null if 0 or negative
            if (complaint.getWard() > 0) {
                stmt.setInt(8, complaint.getWard());
            } else {
                stmt.setNull(8, java.sql.Types.INTEGER);
            }
            
            stmt.setString(9, complaint.getPhone());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    complaint.setId(generatedKeys.getInt(1));
                }
                System.out.println("Complaint created successfully with ID: " + complaint.getId());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error creating complaint: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (stmt != null) stmt.close();
                if (conn != null) dbConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return false;
    }
    
    @Override
    public Optional<Complaint> findById(int complaintId) {
        String sql = "SELECT * FROM complaints WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                System.err.println("Failed to establish database connection for finding complaint by ID");
                return Optional.empty();
            }
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, complaintId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Complaint complaint = mapResultSetToComplaint(rs);
                return Optional.of(complaint);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding complaint by ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) dbConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Complaint> getAllComplaints() {
        String sql = "SELECT * FROM complaints ORDER BY date DESC";
        List<Complaint> complaints = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                System.err.println("Failed to establish database connection for getting all complaints");
                return complaints;
            }
            
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }
            
            System.out.println("Successfully retrieved " + complaints.size() + " complaints from database");
            
        } catch (SQLException e) {
            System.err.println("Error getting all complaints: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) dbConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return complaints;
    }
    
    @Override
    public List<Complaint> getComplaintsByType(String type) {
        String sql = "SELECT * FROM complaints WHERE category = ? ORDER BY date DESC";
        List<Complaint> complaints = new ArrayList<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                System.err.println("Failed to establish database connection for getting complaints by type");
                return complaints;
            }
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, type);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting complaints by type: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) dbConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return complaints;
    }
    
    @Override
    public List<Complaint> getComplaintsByStatus(String status) {
        String sql = "SELECT * FROM complaints WHERE status = ? ORDER BY date DESC";
        List<Complaint> complaints = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting complaints by status: " + e.getMessage());
        }
        
        return complaints;
    }
    
    @Override
    public List<Complaint> getComplaintsByPriority(String priority) {
        // Since priority column doesn't exist in our schema, return empty list
        // or we could use status as a proxy for priority
        String sql = "SELECT * FROM complaints WHERE status = ? ORDER BY date DESC";
        List<Complaint> complaints = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, priority);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting complaints by priority: " + e.getMessage());
        }
        
        return complaints;
    }
    
    @Override
    public List<Complaint> getComplaintsByWard(int ward) {
        String sql = "SELECT * FROM complaints WHERE ward = ? ORDER BY date DESC";
        List<Complaint> complaints = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ward);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting complaints by ward: " + e.getMessage());
        }
        
        return complaints;
    }
    
    @Override
    public List<Complaint> getComplaintsByCitizen(String citizenName) {
        String sql = "SELECT * FROM complaints WHERE name LIKE ? ORDER BY date DESC";
        List<Complaint> complaints = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + citizenName + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting complaints by citizen: " + e.getMessage());
        }
        
        return complaints;
    }
    
    @Override
    public List<Complaint> searchBySubject(String subject) {
        String sql = "SELECT * FROM complaints WHERE description LIKE ? ORDER BY date DESC";
        List<Complaint> complaints = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + subject + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching complaints by subject: " + e.getMessage());
        }
        
        return complaints;
    }
    
    @Override
    public List<Complaint> searchByDescription(String description) {
        String sql = "SELECT * FROM complaints WHERE description LIKE ? ORDER BY date DESC";
        List<Complaint> complaints = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + description + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching complaints by description: " + e.getMessage());
        }
        
        return complaints;
    }
    
    @Override
    public List<Complaint> getComplaintsByDateRange(Date startDate, Date endDate) {
        String sql = "SELECT * FROM complaints WHERE date BETWEEN ? AND ? ORDER BY date DESC";
        List<Complaint> complaints = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting complaints by date range: " + e.getMessage());
        }
        
        return complaints;
    }
    
    @Override
    public boolean updateComplaint(Complaint complaint) {
        String sql = "UPDATE complaints SET name = ?, date = ?, email = ?, description = ?, " +
                    "category = ?, status = ?, feedback = ?, ward = ?, phone = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, complaint.getName());
            stmt.setDate(2, complaint.getDate());
            stmt.setString(3, complaint.getEmail());
            stmt.setString(4, complaint.getDescription());
            stmt.setString(5, complaint.getCategory());
            stmt.setString(6, complaint.getStatus());
            stmt.setString(7, complaint.getFeedback());
            
            // Handle ward - use null if 0 or negative
            if (complaint.getWard() > 0) {
                stmt.setInt(8, complaint.getWard());
            } else {
                stmt.setNull(8, java.sql.Types.INTEGER);
            }
            
            stmt.setString(9, complaint.getPhone());
            stmt.setInt(10, complaint.getId());
            
            boolean success = stmt.executeUpdate() > 0;
            if (success) {
                System.out.println("Complaint updated successfully with ID: " + complaint.getId());
            }
            return success;
            
        } catch (SQLException e) {
            System.err.println("Error updating complaint: " + e.getMessage());
            e.printStackTrace(); // Add stack trace for debugging
        }
        
        return false;
    }
    
    @Override
    public boolean updateComplaintStatus(int complaintId, String newStatus) {
        String sql = "UPDATE complaints SET status = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newStatus);
            stmt.setInt(2, complaintId);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating complaint status: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean updateComplaintPriority(int complaintId, String newPriority) {
        // Since priority column doesn't exist, we'll use status as proxy
        String sql = "UPDATE complaints SET status = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newPriority);
            stmt.setInt(2, complaintId);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating complaint priority: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean addComplaintResponse(int complaintId, String response) {
        // Since response column doesn't exist, we'll use feedback column
        String sql = "UPDATE complaints SET feedback = ?, status = 'Resolved' WHERE id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, response);
            stmt.setInt(2, complaintId);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding complaint response: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean deleteComplaint(int complaintId) {
        String sql = "DELETE FROM complaints WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = dbConnection.openConnection();
            if (conn == null) {
                System.err.println("Failed to establish database connection for deleting complaint");
                return false;
            }
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, complaintId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting complaint: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) dbConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
        
        return false;
    }
    
    @Override
    public int getComplaintCount() {
        String sql = "SELECT COUNT(*) FROM complaints";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting complaint count: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public int getComplaintCountByType(String type) {
        String sql = "SELECT COUNT(*) FROM complaints WHERE category = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting complaint count by type: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public int getComplaintCountByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM complaints WHERE status = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting complaint count by status: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public int getComplaintCountByPriority(String priority) {
        // Since priority column doesn't exist, use status as proxy
        String sql = "SELECT COUNT(*) FROM complaints WHERE status = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, priority);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting complaint count by priority: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public int getComplaintCountByWard(int ward) {
        String sql = "SELECT COUNT(*) FROM complaints WHERE ward = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ward);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting complaint count by ward: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public double getAverageResolutionTime() {
        String sql = "SELECT AVG(DATEDIFF(NOW(), date)) FROM complaints WHERE status = 'Resolved'";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting average resolution time: " + e.getMessage());
        }
        
        return 0.0;
    }
    
    @Override
    public List<Complaint> getComplaintsPendingForDays(int days) {
        String sql = "SELECT * FROM complaints WHERE status = 'Pending' AND " +
                    "DATEDIFF(NOW(), date) > ? ORDER BY date ASC";
        List<Complaint> complaints = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, days);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting complaints pending for days: " + e.getMessage());
        }
        
        return complaints;
    }
    
    /**
     * Helper method to map ResultSet to Complaint object
     */
    private Complaint mapResultSetToComplaint(ResultSet rs) throws SQLException {
        Complaint complaint = new Complaint();
        
        // Required fields
        complaint.setId(rs.getInt("id"));
        complaint.setName(rs.getString("name"));
        complaint.setDate(rs.getDate("date"));
        complaint.setEmail(rs.getString("email"));
        complaint.setDescription(rs.getString("description"));
        complaint.setCategory(rs.getString("category"));
        complaint.setStatus(rs.getString("status"));
        
        // Optional fields - handle null values properly
        complaint.setFeedback(rs.getString("feedback"));
        
        // Ward can be null in the database
        int ward = rs.getInt("ward");
        if (rs.wasNull()) {
            complaint.setWard(0); // Default to 0 if null
        } else {
            complaint.setWard(ward);
        }
        
        // Phone can be null
        complaint.setPhone(rs.getString("phone"));
        
        // Timestamp fields
        try {
            complaint.setCreatedAt(rs.getTimestamp("created_at"));
        } catch (SQLException e) {
            complaint.setCreatedAt(null);
        }
        
        try {
            complaint.setUpdatedAt(rs.getTimestamp("updated_at"));
        } catch (SQLException e) {
            complaint.setUpdatedAt(null);
        }
        
        return complaint;
    }
} 
