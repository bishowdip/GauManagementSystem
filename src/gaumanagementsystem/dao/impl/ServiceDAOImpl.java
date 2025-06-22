package gaumanagementsystem.dao.impl;

import gaumanagementsystem.dao.ServiceDAO;
import gaumanagementsystem.database.MySqlConnection;
import gaumanagementsystem.model.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of ServiceDAO interface
 * Handles all database operations for Service entity
 */
public class ServiceDAOImpl implements ServiceDAO {
    
    private final MySqlConnection dbConnection;
    
    public ServiceDAOImpl() {
        this.dbConnection = new MySqlConnection();
    }
    
    @Override
    public boolean createService(Service service) {
        String sql = "INSERT INTO services (service_name, name_of_citizen, ward, phone, " +
                    "description, status, submitted_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, service.getServiceName());
            stmt.setString(2, service.getNameOfCitizen());
            stmt.setInt(3, service.getWard());
            stmt.setString(4, service.getPhone());
            stmt.setString(5, service.getDescription());
            stmt.setString(6, service.getStatus());
            stmt.setTimestamp(7, service.getSubmittedAt());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    service.setServiceId(generatedKeys.getInt(1));
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error creating service: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public Optional<Service> findById(int serviceId) {
        String sql = "SELECT * FROM services WHERE service_id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, serviceId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Service service = mapResultSetToService(rs);
                return Optional.of(service);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding service by ID: " + e.getMessage());
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Service> getAllServices() {
        String sql = "SELECT * FROM services ORDER BY submitted_at DESC";
        List<Service> services = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                services.add(mapResultSetToService(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all services: " + e.getMessage());
        }
        
        return services;
    }
    
    @Override
    public List<Service> getServicesByStatus(String status) {
        String sql = "SELECT * FROM services WHERE status = ? ORDER BY submitted_at DESC";
        List<Service> services = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                services.add(mapResultSetToService(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting services by status: " + e.getMessage());
        }
        
        return services;
    }
    
    @Override
    public List<Service> getServicesByWard(int ward) {
        String sql = "SELECT * FROM services WHERE ward = ? ORDER BY submitted_at DESC";
        List<Service> services = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ward);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                services.add(mapResultSetToService(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting services by ward: " + e.getMessage());
        }
        
        return services;
    }
    
    @Override
    public List<Service> getServicesByCitizen(String citizenName) {
        String sql = "SELECT * FROM services WHERE name_of_citizen LIKE ? ORDER BY submitted_at DESC";
        List<Service> services = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + citizenName + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                services.add(mapResultSetToService(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting services by citizen: " + e.getMessage());
        }
        
        return services;
    }
    
    @Override
    public List<Service> searchByServiceName(String serviceName) {
        String sql = "SELECT * FROM services WHERE service_name LIKE ? ORDER BY submitted_at DESC";
        List<Service> services = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + serviceName + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                services.add(mapResultSetToService(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching services by name: " + e.getMessage());
        }
        
        return services;
    }
    
    @Override
    public boolean updateService(Service service) {
        String sql = "UPDATE services SET service_name = ?, name_of_citizen = ?, ward = ?, " +
                    "phone = ?, description = ?, status = ? WHERE service_id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, service.getServiceName());
            stmt.setString(2, service.getNameOfCitizen());
            stmt.setInt(3, service.getWard());
            stmt.setString(4, service.getPhone());
            stmt.setString(5, service.getDescription());
            stmt.setString(6, service.getStatus());
            stmt.setInt(7, service.getServiceId());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating service: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean updateServiceStatus(int serviceId, String newStatus) {
        String sql = "UPDATE services SET status = ? WHERE service_id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, newStatus);
            stmt.setInt(2, serviceId);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating service status: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean deleteService(int serviceId) {
        String sql = "DELETE FROM services WHERE service_id = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, serviceId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting service: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public int getServiceCount() {
        String sql = "SELECT COUNT(*) FROM services";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting service count: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public int getServiceCountByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM services WHERE status = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting service count by status: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public int getServiceCountByWard(int ward) {
        String sql = "SELECT COUNT(*) FROM services WHERE ward = ?";
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ward);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting service count by ward: " + e.getMessage());
        }
        
        return 0;
    }
    
    @Override
    public List<Service> getServicesByDateRange(java.sql.Date startDate, java.sql.Date endDate) {
        String sql = "SELECT * FROM services WHERE DATE(submitted_at) BETWEEN ? AND ? ORDER BY submitted_at DESC";
        List<Service> services = new ArrayList<>();
        
        try (Connection conn = dbConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                services.add(mapResultSetToService(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting services by date range: " + e.getMessage());
        }
        
        return services;
    }
    
    /**
     * Helper method to map ResultSet to Service object
     */
    private Service mapResultSetToService(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setServiceId(rs.getInt("service_id"));
        service.setServiceName(rs.getString("service_name"));
        service.setNameOfCitizen(rs.getString("name_of_citizen"));
        service.setWard(rs.getInt("ward"));
        service.setPhone(rs.getString("phone"));
        service.setDescription(rs.getString("description"));
        service.setStatus(rs.getString("status"));
        service.setSubmittedAt(rs.getTimestamp("submitted_at"));
        service.setEmail(rs.getString("email"));
        service.setCreatedAt(rs.getTimestamp("created_at"));
        service.setUpdatedAt(rs.getTimestamp("updated_at"));
        return service;
    }
} 
