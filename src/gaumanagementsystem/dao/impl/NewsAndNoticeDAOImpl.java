package gaumanagementsystem.dao.impl;

import gaumanagementsystem.dao.NewsAndNoticeDAO;
import gaumanagementsystem.database.MySqlConnection;
import gaumanagementsystem.model.NewsAndNotice;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of NewsAndNoticeDAO interface
 * Handles all database operations for NewsAndNotice entity
 */
public class NewsAndNoticeDAOImpl implements NewsAndNoticeDAO {
    
    private final MySqlConnection dbConnection;
    
    public NewsAndNoticeDAOImpl() {
        this.dbConnection = new MySqlConnection();
        createTableIfNotExists();
    }
    
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS news_and_notice (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "date VARCHAR(20) NOT NULL, " +
                    "audience VARCHAR(255) NOT NULL, " +
                    "subject VARCHAR(255) NOT NULL, " +
                    "description TEXT NOT NULL, " +
                    "expiry_date VARCHAR(20) NOT NULL, " +
                    "type VARCHAR(50) NOT NULL, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
        
        try (Connection conn = dbConnection.openConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(sql);
            System.out.println("News and Notice table ready.");
            
        } catch (SQLException e) {
            System.err.println("Error creating news_and_notice table: " + e.getMessage());
        }
    }
    
    @Override
    public boolean createNewsAndNotice(NewsAndNotice newsAndNotice) {
        String sql = "INSERT INTO news_and_notice (date, audience, subject, description, expiry_date, type) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newsAndNotice.getDate());
            stmt.setString(2, newsAndNotice.getAudience());
            stmt.setString(3, newsAndNotice.getSubject());
            stmt.setString(4, newsAndNotice.getDescription());
            stmt.setString(5, newsAndNotice.getExpiryDate());
            stmt.setString(6, newsAndNotice.getType());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error creating news/notice: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public Optional<NewsAndNotice> findById(int id) {
        String sql = "SELECT * FROM news_and_notice WHERE id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                NewsAndNotice newsAndNotice = mapResultSetToNewsAndNotice(rs);
                return Optional.of(newsAndNotice);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding news/notice by ID: " + e.getMessage());
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<NewsAndNotice> getAllNewsAndNotices() {
        String sql = "SELECT * FROM news_and_notice ORDER BY created_at DESC";
        List<NewsAndNotice> newsAndNotices = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                newsAndNotices.add(mapResultSetToNewsAndNotice(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all news and notices: " + e.getMessage());
        }
        
        return newsAndNotices;
    }
    
    @Override
    public List<NewsAndNotice> getByType(String type) {
        String sql = "SELECT * FROM news_and_notice WHERE type = ? ORDER BY created_at DESC";
        List<NewsAndNotice> newsAndNotices = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                newsAndNotices.add(mapResultSetToNewsAndNotice(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting news/notices by type: " + e.getMessage());
        }
        
        return newsAndNotices;
    }
    
    @Override
    public boolean updateNewsAndNotice(NewsAndNotice newsAndNotice) {
        // For simplicity, we'll identify by subject since we don't have ID in model
        String sql = "UPDATE news_and_notice SET date = ?, audience = ?, description = ?, expiry_date = ?, type = ? WHERE subject = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newsAndNotice.getDate());
            stmt.setString(2, newsAndNotice.getAudience());
            stmt.setString(3, newsAndNotice.getDescription());
            stmt.setString(4, newsAndNotice.getExpiryDate());
            stmt.setString(5, newsAndNotice.getType());
            stmt.setString(6, newsAndNotice.getSubject());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating news/notice: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean deleteNewsAndNotice(int id) {
        String sql = "DELETE FROM news_and_notice WHERE id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting news/notice: " + e.getMessage());
        }
        
        return false;
    }
    
    // Helper method to get ID by subject for deletion
    public int getIdBySubject(String subject) {
        String sql = "SELECT id FROM news_and_notice WHERE subject = ? LIMIT 1";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, subject);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting ID by subject: " + e.getMessage());
        }
        
        return -1;
    }
    
    private NewsAndNotice mapResultSetToNewsAndNotice(ResultSet rs) throws SQLException {
        return new NewsAndNotice(
            rs.getString("date"),
            rs.getString("audience"),
            rs.getString("subject"),
            rs.getString("description"),
            rs.getString("expiry_date"),
            rs.getString("type")
        );
    }
    
    // Implement remaining interface methods with simple implementations
    @Override
    public List<NewsAndNotice> getByStatus(String status) {
        return new ArrayList<>(); // Not implemented for simplicity
    }
    
    @Override
    public List<NewsAndNotice> getByPriority(String priority) {
        return new ArrayList<>(); // Not implemented for simplicity
    }
    
    @Override
    public List<NewsAndNotice> getActiveNewsAndNotices() {
        return getAllNewsAndNotices(); // Return all for simplicity
    }
    
    @Override
    public List<NewsAndNotice> getExpiredNewsAndNotices() {
        return new ArrayList<>(); // Not implemented for simplicity
    }
    
    @Override
    public List<NewsAndNotice> searchByTitle(String title) {
        return new ArrayList<>(); // Not implemented for simplicity
    }
    
    @Override
    public List<NewsAndNotice> searchByContent(String content) {
        return new ArrayList<>(); // Not implemented for simplicity
    }
    
    @Override
    public List<NewsAndNotice> getByPublicationDateRange(java.sql.Date startDate, java.sql.Date endDate) {
        return new ArrayList<>(); // Not implemented for simplicity
    }
    
    @Override
    public List<NewsAndNotice> getByExpiryDateRange(java.sql.Date startDate, java.sql.Date endDate) {
        return new ArrayList<>(); // Not implemented for simplicity
    }
    
    @Override
    public List<NewsAndNotice> getExpiringSoon(int days) {
        return new ArrayList<>(); // Not implemented for simplicity
    }
    
    @Override
    public List<NewsAndNotice> getLatestNewsAndNotices(int limit) {
        return getAllNewsAndNotices(); // Return all for simplicity
    }
    
    @Override
    public boolean updateStatus(int id, String newStatus) {
        return false; // Not implemented for simplicity
    }
    
    @Override
    public boolean updatePriority(int id, String newPriority) {
        return false; // Not implemented for simplicity
    }
    
    @Override
    public boolean extendExpiryDate(int id, java.sql.Date newExpiryDate) {
        return false; // Not implemented for simplicity
    }
    
    @Override
    public int getNewsAndNoticeCount() {
        return getAllNewsAndNotices().size();
    }
    
    @Override
    public int getCountByType(String type) {
        return getByType(type).size();
    }
    
    @Override
    public int getCountByStatus(String status) {
        return 0; // Not implemented for simplicity
    }
    
    @Override
    public int getActiveCount() {
        return getAllNewsAndNotices().size();
    }
    
    @Override
    public int getExpiredCount() {
        return 0; // Not implemented for simplicity
    }
    
    @Override
    public int archiveOldNewsAndNotices(java.sql.Date cutoffDate) {
        return 0; // Not implemented for simplicity
    }
} 
