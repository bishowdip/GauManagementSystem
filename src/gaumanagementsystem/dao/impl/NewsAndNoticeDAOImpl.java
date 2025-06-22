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
    }
    
    @Override
    public boolean createNewsAndNotice(NewsAndNotice newsAndNotice) {
        String sql = "INSERT INTO news_and_notice (date, audience, subject, description, expiry_date, type) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
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
        String sql = "SELECT * FROM news_and_notices WHERE id = ?";
        
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
        String sql = "SELECT * FROM news_and_notices ORDER BY publication_date DESC";
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
        String sql = "SELECT * FROM news_and_notices WHERE type = ? ORDER BY publication_date DESC";
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
    public List<NewsAndNotice> getByStatus(String status) {
        String sql = "SELECT * FROM news_and_notices WHERE status = ? ORDER BY publication_date DESC";
        List<NewsAndNotice> newsAndNotices = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                newsAndNotices.add(mapResultSetToNewsAndNotice(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting news/notices by status: " + e.getMessage());
        }
        
        return newsAndNotices;
    }
    
    @Override
    public List<NewsAndNotice> getByPriority(String priority) {
        String sql = "SELECT * FROM news_and_notices WHERE priority = ? ORDER BY publication_date DESC";
        List<NewsAndNotice> newsAndNotices = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, priority);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                newsAndNotices.add(mapResultSetToNewsAndNotice(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting news/notices by priority: " + e.getMessage());
        }
        
        return newsAndNotices;
    }
    
    @Override
    public List<NewsAndNotice> getActiveNewsAndNotices() {
        String sql = "SELECT * FROM news_and_notices WHERE status = 'Active' AND " +
                    "(expiry_date IS NULL OR expiry_date >= CURDATE()) ORDER BY publication_date DESC";
        List<NewsAndNotice> newsAndNotices = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                newsAndNotices.add(mapResultSetToNewsAndNotice(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting active news and notices: " + e.getMessage());
        }
        
        return newsAndNotices;
    }
    
    @Override
    public List<NewsAndNotice> getExpiredNewsAndNotices() {
        String sql = "SELECT * FROM news_and_notices WHERE expiry_date < CURDATE() ORDER BY expiry_date DESC";
        List<NewsAndNotice> newsAndNotices = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                newsAndNotices.add(mapResultSetToNewsAndNotice(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting expired news and notices: " + e.getMessage());
        }
        
        return newsAndNotices;
    }
    
    @Override
    public List<NewsAndNotice> searchByTitle(String title) {
        String sql = "SELECT * FROM news_and_notices WHERE title LIKE ? ORDER BY publication_date DESC";
        List<NewsAndNotice> newsAndNotices = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + title + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                newsAndNotices.add(mapResultSetToNewsAndNotice(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching news/notices by title: " + e.getMessage());
        }
        
        return newsAndNotices;
    }
    
    @Override
    public List<NewsAndNotice> searchByContent(String content) {
        String sql = "SELECT * FROM news_and_notices WHERE content LIKE ? ORDER BY publication_date DESC";
        List<NewsAndNotice> newsAndNotices = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + content + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                newsAndNotices.add(mapResultSetToNewsAndNotice(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching news/notices by content: " + e.getMessage());
        }
        
        return newsAndNotices;
    }
    
    @Override
    public List<NewsAndNotice> getByPublicationDateRange(Date startDate, Date endDate) {
        String sql = "SELECT * FROM news_and_notices WHERE publication_date BETWEEN ? AND ? ORDER BY publication_date DESC";
        List<NewsAndNotice> newsAndNotices = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                newsAndNotices.add(mapResultSetToNewsAndNotice(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting news/notices by publication date range: " + e.getMessage());
        }
        
        return newsAndNotices;
    }
    
    @Override
    public List<NewsAndNotice> getByExpiryDateRange(Date startDate, Date endDate) {
        String sql = "SELECT * FROM news_and_notices WHERE expiry_date BETWEEN ? AND ? ORDER BY expiry_date ASC";
        List<NewsAndNotice> newsAndNotices = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                newsAndNotices.add(mapResultSetToNewsAndNotice(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting news/notices by expiry date range: " + e.getMessage());
        }
        
        return newsAndNotices;
    }
    
    @Override
    public List<NewsAndNotice> getExpiringSoon(int days) {
        String sql = "SELECT * FROM news_and_notices WHERE expiry_date BETWEEN CURDATE() AND " +
                    "DATE_ADD(CURDATE(), INTERVAL ? DAY) ORDER BY expiry_date ASC";
        List<NewsAndNotice> newsAndNotices = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, days);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                newsAndNotices.add(mapResultSetToNewsAndNotice(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting news/notices expiring soon: " + e.getMessage());
        }
        
        return newsAndNotices;
    }
    
    @Override
    public List<NewsAndNotice> getLatestNewsAndNotices(int limit) {
        String sql = "SELECT * FROM news_and_notices WHERE status = 'Active' " +
                    "ORDER BY publication_date DESC LIMIT ?";
        List<NewsAndNotice> newsAndNotices = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                newsAndNotices.add(mapResultSetToNewsAndNotice(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting latest news and notices: " + e.getMessage());
        }
        
        return newsAndNotices;
    }
    
    @Override
    public boolean updateNewsAndNotice(NewsAndNotice newsAndNotice) {
        String sql = "UPDATE news_and_notice SET date = ?, audience = ?, subject = ?, " +
                    "description = ?, expiry_date = ?, type = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newsAndNotice.getDate());
            stmt.setString(2, newsAndNotice.getAudience());
            stmt.setString(3, newsAndNotice.getSubject());
            stmt.setString(4, newsAndNotice.getDescription());
            stmt.setString(5, newsAndNotice.getExpiryDate());
            stmt.setString(6, newsAndNotice.getType());
            // Note: NewsAndNotice model doesn't have ID field, this will need to be added
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating news/notice: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean updateStatus(int id, String newStatus) {
        String sql = "UPDATE news_and_notices SET status = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newStatus);
            stmt.setInt(2, id);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating news/notice status: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean updatePriority(int id, String newPriority) {
        String sql = "UPDATE news_and_notices SET priority = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newPriority);
            stmt.setInt(2, id);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating news/notice priority: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean extendExpiryDate(int id, Date newExpiryDate) {
        String sql = "UPDATE news_and_notices SET expiry_date = ? WHERE id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, newExpiryDate);
            stmt.setInt(2, id);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error extending expiry date: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean deleteNewsAndNotice(int id) {
        String sql = "DELETE FROM news_and_notices WHERE id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting news/notice: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public int getNewsAndNoticeCount() {
        String sql = "SELECT COUNT(*) FROM news_and_notices";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting news/notice count: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public int getCountByType(String type) {
        String sql = "SELECT COUNT(*) FROM news_and_notices WHERE type = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting count by type: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public int getCountByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM news_and_notices WHERE status = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting count by status: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public int getActiveCount() {
        String sql = "SELECT COUNT(*) FROM news_and_notices WHERE status = 'Active' AND " +
                    "(expiry_date IS NULL OR expiry_date >= CURDATE())";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting active count: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public int getExpiredCount() {
        String sql = "SELECT COUNT(*) FROM news_and_notices WHERE expiry_date < CURDATE()";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting expired count: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public int archiveOldNewsAndNotices(Date cutoffDate) {
        String sql = "UPDATE news_and_notices SET status = 'Archived' WHERE publication_date < ? AND status != 'Archived'";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, cutoffDate);
            return stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error archiving old news/notices: " + e.getMessage());
        }
        
        return 0;
    }
    
    /**
     * Helper method to map ResultSet to NewsAndNotice object
     */
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
} 
