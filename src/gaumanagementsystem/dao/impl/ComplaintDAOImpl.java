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
        String sql = "INSERT INTO complaints (name, ward, phone, category, " +
                    "description, status, date, feedback) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, complaint.getName());
            stmt.setInt(2, complaint.getWard());
            stmt.setString(3, complaint.getPhone());
            stmt.setString(4, complaint.getCategory());
            stmt.setString(5, complaint.getDescription());
            stmt.setString(6, complaint.getStatus());
            stmt.setDate(7, complaint.getDate());
            stmt.setString(8, complaint.getFeedback());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    complaint.setId(generatedKeys.getInt(1));
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error creating complaint: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public Optional<Complaint> findById(int complaintId) {
        String sql = "SELECT * FROM complaints WHERE complaint_id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, complaintId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Complaint complaint = mapResultSetToComplaint(rs);
                return Optional.of(complaint);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding complaint by ID: " + e.getMessage());
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Complaint> getAllComplaints() {
        String sql = "SELECT * FROM complaints ORDER BY submitted_date DESC";
        List<Complaint> complaints = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all complaints: " + e.getMessage());
        }
        
        return complaints;
    }
    
    @Override
    public List<Complaint> getComplaintsByType(String type) {
        String sql = "SELECT * FROM complaints WHERE complaint_type = ? ORDER BY submitted_date DESC";
        List<Complaint> complaints = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting complaints by type: " + e.getMessage());
        }
        
        return complaints;
    }
    
    @Override
    public List<Complaint> getComplaintsByStatus(String status) {
        String sql = "SELECT * FROM complaints WHERE status = ? ORDER BY submitted_date DESC";
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
        String sql = "SELECT * FROM complaints WHERE priority = ? ORDER BY submitted_date DESC";
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
        String sql = "SELECT * FROM complaints WHERE ward = ? ORDER BY submitted_date DESC";
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
        String sql = "SELECT * FROM complaints WHERE citizen_name LIKE ? ORDER BY submitted_date DESC";
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
        String sql = "SELECT * FROM complaints WHERE subject LIKE ? ORDER BY submitted_date DESC";
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
        String sql = "SELECT * FROM complaints WHERE description LIKE ? ORDER BY submitted_date DESC";
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
        String sql = "SELECT * FROM complaints WHERE submitted_date BETWEEN ? AND ? ORDER BY submitted_date DESC";
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
        String sql = "UPDATE complaints SET name = ?, ward = ?, phone = ?, " +
                    "category = ?, description = ?, status = ?, " +
                    "feedback = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, complaint.getName());
            stmt.setInt(2, complaint.getWard());
            stmt.setString(3, complaint.getPhone());
            stmt.setString(4, complaint.getCategory());
            stmt.setString(5, complaint.getDescription());
            stmt.setString(6, complaint.getStatus());
            stmt.setString(7, complaint.getFeedback());
            stmt.setInt(8, complaint.getId());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating complaint: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean updateComplaintStatus(int complaintId, String newStatus) {
        String sql = "UPDATE complaints SET status = ? WHERE complaint_id = ?";
        
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
        String sql = "UPDATE complaints SET priority = ? WHERE complaint_id = ?";
        
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
        String sql = "UPDATE complaints SET response = ?, status = 'Resolved' WHERE complaint_id = ?";
        
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
        String sql = "DELETE FROM complaints WHERE complaint_id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, complaintId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting complaint: " + e.getMessage());
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
        String sql = "SELECT COUNT(*) FROM complaints WHERE complaint_type = ?";
        
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
        String sql = "SELECT COUNT(*) FROM complaints WHERE priority = ?";
        
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
        String sql = "SELECT AVG(DATEDIFF(NOW(), submitted_date)) FROM complaints WHERE status = 'Resolved'";
        
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
                    "DATEDIFF(NOW(), submitted_date) > ? ORDER BY submitted_date ASC";
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
        complaint.setId(rs.getInt("id"));
        complaint.setName(rs.getString("name"));
        complaint.setWard(rs.getInt("ward"));
        complaint.setPhone(rs.getString("phone"));
        complaint.setCategory(rs.getString("category"));
        complaint.setDescription(rs.getString("description"));
        complaint.setStatus(rs.getString("status"));
        complaint.setDate(rs.getDate("date"));
        complaint.setFeedback(rs.getString("feedback"));
        complaint.setEmail(rs.getString("email"));
        complaint.setCreatedAt(rs.getTimestamp("created_at"));
        complaint.setUpdatedAt(rs.getTimestamp("updated_at"));
        return complaint;
    }
} 
