package gaumanagementsystem.dao.impl;

import gaumanagementsystem.dao.ProjectRequestDAO;
import gaumanagementsystem.database.MySqlConnection;
import gaumanagementsystem.model.ProjectRequest;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of ProjectRequestDAO interface
 * Handles all database operations for ProjectRequest entity
 */
public class ProjectRequestDAOImpl implements ProjectRequestDAO {
    
    private final MySqlConnection dbConnection;
    
    public ProjectRequestDAOImpl() {
        this.dbConnection = new MySqlConnection();
    }
    
    @Override
    public boolean createProjectRequest(ProjectRequest projectRequest) {
        String sql = "INSERT INTO project_requests (projects_name, started_date, ward, " +
                    "expected_to_end, description, status, budget, priority, requested_by, category, fiscal_year) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, projectRequest.getProjectsName());
            stmt.setDate(2, projectRequest.getStartedDate());
            stmt.setInt(3, projectRequest.getWard());
            stmt.setDate(4, projectRequest.getExpectedToEnd());
            stmt.setString(5, projectRequest.getDescription());
            stmt.setString(6, projectRequest.getStatus());
            stmt.setBigDecimal(7, projectRequest.getBudget());
            stmt.setString(8, projectRequest.getPriority());
            stmt.setString(9, projectRequest.getRequestedBy());
            stmt.setString(10, projectRequest.getCategory());
            stmt.setString(11, projectRequest.getFiscalYear());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    projectRequest.setRequestId(generatedKeys.getInt(1));
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error creating project request: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public Optional<ProjectRequest> findById(int requestId) {
        String sql = "SELECT * FROM project_requests WHERE request_id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, requestId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                ProjectRequest projectRequest = mapResultSetToProjectRequest(rs);
                return Optional.of(projectRequest);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding project request by ID: " + e.getMessage());
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<ProjectRequest> getAllProjectRequests() {
        String sql = "SELECT * FROM project_requests ORDER BY created_at DESC";
        List<ProjectRequest> projectRequests = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                projectRequests.add(mapResultSetToProjectRequest(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all project requests: " + e.getMessage());
        }
        
        return projectRequests;
    }
    
    @Override
    public List<ProjectRequest> getProjectRequestsByStatus(String status) {
        String sql = "SELECT * FROM project_requests WHERE status = ? ORDER BY created_at DESC";
        List<ProjectRequest> projectRequests = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                projectRequests.add(mapResultSetToProjectRequest(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting project requests by status: " + e.getMessage());
        }
        
        return projectRequests;
    }
    
    @Override
    public List<ProjectRequest> getProjectRequestsByWard(int ward) {
        String sql = "SELECT * FROM project_requests WHERE ward = ? ORDER BY created_at DESC";
        List<ProjectRequest> projectRequests = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ward);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                projectRequests.add(mapResultSetToProjectRequest(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting project requests by ward: " + e.getMessage());
        }
        
        return projectRequests;
    }
    
    @Override
    public List<ProjectRequest> getProjectRequestsByPriority(String priority) {
        String sql = "SELECT * FROM project_requests WHERE priority = ? ORDER BY created_at DESC";
        List<ProjectRequest> projectRequests = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, priority);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                projectRequests.add(mapResultSetToProjectRequest(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting project requests by priority: " + e.getMessage());
        }
        
        return projectRequests;
    }
    
    @Override
    public List<ProjectRequest> searchByProjectName(String projectName) {
        String sql = "SELECT * FROM project_requests WHERE projects_name LIKE ? ORDER BY created_at DESC";
        List<ProjectRequest> projectRequests = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + projectName + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                projectRequests.add(mapResultSetToProjectRequest(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching project requests by name: " + e.getMessage());
        }
        
        return projectRequests;
    }
    
    @Override
    public List<ProjectRequest> getProjectRequestsByBudgetRange(BigDecimal minBudget, BigDecimal maxBudget) {
        String sql = "SELECT * FROM project_requests WHERE budget BETWEEN ? AND ? ORDER BY budget DESC";
        List<ProjectRequest> projectRequests = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBigDecimal(1, minBudget);
            stmt.setBigDecimal(2, maxBudget);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                projectRequests.add(mapResultSetToProjectRequest(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting project requests by budget range: " + e.getMessage());
        }
        
        return projectRequests;
    }
    
    @Override
    public List<ProjectRequest> getProjectRequestsByDateRange(Date startDate, Date endDate) {
        String sql = "SELECT * FROM project_requests WHERE started_date BETWEEN ? AND ? ORDER BY started_date DESC";
        List<ProjectRequest> projectRequests = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                projectRequests.add(mapResultSetToProjectRequest(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting project requests by date range: " + e.getMessage());
        }
        
        return projectRequests;
    }
    
    @Override
    public boolean updateProjectRequest(ProjectRequest projectRequest) {
        String sql = "UPDATE project_requests SET projects_name = ?, started_date = ?, " +
                    "ward = ?, expected_to_end = ?, description = ?, status = ?, budget = ?, " +
                    "priority = ?, requested_by = ? WHERE request_id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, projectRequest.getProjectsName());
            stmt.setDate(2, projectRequest.getStartedDate());
            stmt.setInt(3, projectRequest.getWard());
            stmt.setDate(4, projectRequest.getExpectedToEnd());
            stmt.setString(5, projectRequest.getDescription());
            stmt.setString(6, projectRequest.getStatus());
            stmt.setBigDecimal(7, projectRequest.getBudget());
            stmt.setString(8, projectRequest.getPriority());
            stmt.setString(9, projectRequest.getRequestedBy());
            stmt.setInt(10, projectRequest.getRequestId());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating project request: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean updateProjectStatus(int requestId, String newStatus) {
        String sql = "UPDATE project_requests SET status = ? WHERE request_id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newStatus);
            stmt.setInt(2, requestId);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating project status: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean updateProjectPriority(int requestId, String newPriority) {
        String sql = "UPDATE project_requests SET priority = ? WHERE request_id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newPriority);
            stmt.setInt(2, requestId);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating project priority: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean updateProjectBudget(int requestId, BigDecimal newBudget) {
        String sql = "UPDATE project_requests SET budget = ? WHERE request_id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBigDecimal(1, newBudget);
            stmt.setInt(2, requestId);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating project budget: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean deleteProjectRequest(int requestId) {
        String sql = "DELETE FROM project_requests WHERE request_id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, requestId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting project request: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public int getProjectRequestCount() {
        String sql = "SELECT COUNT(*) FROM project_requests";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting project request count: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public int getProjectRequestCountByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM project_requests WHERE status = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting project request count by status: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public int getProjectRequestCountByWard(int ward) {
        String sql = "SELECT COUNT(*) FROM project_requests WHERE ward = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ward);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting project request count by ward: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public BigDecimal getTotalProjectBudget() {
        String sql = "SELECT SUM(budget) FROM project_requests";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getBigDecimal(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting total project budget: " + e.getMessage());
        }
        
        return BigDecimal.ZERO;
    }
    
    @Override
    public BigDecimal getTotalBudgetByStatus(String status) {
        String sql = "SELECT SUM(budget) FROM project_requests WHERE status = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getBigDecimal(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting total budget by status: " + e.getMessage());
        }
        
        return BigDecimal.ZERO;
    }
    
    /**
     * Helper method to map ResultSet to ProjectRequest object
     */
    private ProjectRequest mapResultSetToProjectRequest(ResultSet rs) throws SQLException {
        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setRequestId(rs.getInt("request_id"));
        projectRequest.setProjectsName(rs.getString("projects_name"));
        projectRequest.setStartedDate(rs.getDate("started_date"));
        projectRequest.setWard(rs.getInt("ward"));
        projectRequest.setExpectedToEnd(rs.getDate("expected_to_end"));
        projectRequest.setDescription(rs.getString("description"));
        projectRequest.setStatus(rs.getString("status"));
        projectRequest.setBudget(rs.getBigDecimal("budget"));
        projectRequest.setPriority(rs.getString("priority"));
        projectRequest.setRequestedBy(rs.getString("requested_by"));
        projectRequest.setCreatedAt(rs.getTimestamp("created_at"));
        projectRequest.setUpdatedAt(rs.getTimestamp("updated_at"));
        projectRequest.setCategory(rs.getString("category"));
        projectRequest.setFiscalYear(rs.getString("fiscal_year"));
        return projectRequest;
    }
} 
