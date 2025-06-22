/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.dao;

/**
 *
 * @author wangel
 */

import gaumanagementsystem.model.CitizenData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitizenDao {
    private Connection conn;

    public CitizenDao(Connection conn) {
        this.conn = conn;
    }

    // CREATE - Add new citizen
    public boolean createCitizen(CitizenData citizen) {
        String sql = "INSERT INTO citizens (citizen_id, name, email, date_of_birth, address, gender, phone, father_name, mother_name, ward, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, citizen.getCitizenId());
            stmt.setString(2, citizen.getName());
            stmt.setString(3, citizen.getEmail());
            stmt.setString(4, citizen.getDateOfBirth());
            stmt.setString(5, citizen.getAddress());
            stmt.setString(6, citizen.getGender());
            stmt.setString(7, citizen.getPhone());
            stmt.setString(8, citizen.getFatherName());
            stmt.setString(9, citizen.getMotherName());
            stmt.setInt(10, citizen.getWard());
            stmt.setString(11, citizen.getImagePath());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // READ - Get citizen by ID
    public CitizenData getCitizenById(String citizenId) {
        String sql = "SELECT * FROM citizens WHERE citizen_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, citizenId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToCitizen(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // READ - Get citizen by email (for user profile lookup)
    public CitizenData getCitizenByEmail(String email) {
        String sql = "SELECT * FROM citizens WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToCitizen(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // READ - Get all citizens
    public List<CitizenData> getAllCitizens() {
        List<CitizenData> citizens = new ArrayList<>();
        String sql = "SELECT * FROM citizens ORDER BY name";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                citizens.add(mapResultSetToCitizen(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return citizens;
    }

    // READ - Search citizens by name
    public List<CitizenData> searchCitizensByName(String name) {
        List<CitizenData> citizens = new ArrayList<>();
        String sql = "SELECT * FROM citizens WHERE name LIKE ? ORDER BY name";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                citizens.add(mapResultSetToCitizen(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return citizens;
    }

    // READ - Search citizens by citizenship number
    public List<CitizenData> searchCitizensByCitizenshipNumber(String citizenshipNumber) {
        List<CitizenData> citizens = new ArrayList<>();
        String sql = "SELECT * FROM citizens WHERE citizen_id LIKE ? ORDER BY name";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + citizenshipNumber + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                citizens.add(mapResultSetToCitizen(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return citizens;
    }

    // READ - Search citizens by name OR citizenship number
    public List<CitizenData> searchCitizensByNameOrCitizenship(String searchTerm) {
        List<CitizenData> citizens = new ArrayList<>();
        String sql = "SELECT * FROM citizens WHERE name LIKE ? OR citizen_id LIKE ? ORDER BY name";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                citizens.add(mapResultSetToCitizen(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return citizens;
    }

    // READ - Get citizens by address
    public List<CitizenData> getCitizensByAddress(String address) {
        List<CitizenData> citizens = new ArrayList<>();
        String sql = "SELECT * FROM citizens WHERE address LIKE ? ORDER BY name";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + address + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                citizens.add(mapResultSetToCitizen(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return citizens;
    }

    // UPDATE - Update existing citizen
    public boolean updateCitizen(CitizenData citizen) {
        String sql = "UPDATE citizens SET name = ?, email = ?, date_of_birth = ?, address = ?, gender = ?, phone = ?, father_name = ?, mother_name = ?, ward = ?, image_path = ? WHERE citizen_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, citizen.getName());
            stmt.setString(2, citizen.getEmail());
            stmt.setString(3, citizen.getDateOfBirth());
            stmt.setString(4, citizen.getAddress());
            stmt.setString(5, citizen.getGender());
            stmt.setString(6, citizen.getPhone());
            stmt.setString(7, citizen.getFatherName());
            stmt.setString(8, citizen.getMotherName());
            stmt.setInt(9, citizen.getWard());
            stmt.setString(10, citizen.getImagePath());
            stmt.setString(11, citizen.getCitizenId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // DELETE - Delete citizen by ID
    public boolean deleteCitizen(String citizenId) {
        String sql = "DELETE FROM citizens WHERE citizen_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, citizenId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // DELETE - Delete multiple citizens by IDs
    public boolean deleteMultipleCitizens(List<String> citizenIds) {
        if (citizenIds == null || citizenIds.isEmpty()) {
            return false;
        }
        
        String placeholders = String.join(",", java.util.Collections.nCopies(citizenIds.size(), "?"));
        String sql = "DELETE FROM citizens WHERE citizen_id IN (" + placeholders + ")";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < citizenIds.size(); i++) {
                stmt.setString(i + 1, citizenIds.get(i));
            }
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Check if citizen exists
    public boolean citizenExists(String citizenId) {
        String sql = "SELECT COUNT(*) FROM citizens WHERE citizen_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, citizenId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // Get total count of citizens
    public int getCitizenCount() {
        String sql = "SELECT COUNT(*) FROM citizens";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    // Helper method to map ResultSet to CitizenData object
    private CitizenData mapResultSetToCitizen(ResultSet rs) throws SQLException {
        CitizenData citizen = new CitizenData();
        citizen.setCitizenId(rs.getString("citizen_id"));
        citizen.setName(rs.getString("name"));
        citizen.setEmail(rs.getString("email"));
        citizen.setDateOfBirth(rs.getString("date_of_birth"));
        citizen.setAddress(rs.getString("address"));
        citizen.setGender(rs.getString("gender"));
        citizen.setPhone(rs.getString("phone"));
        citizen.setFatherName(rs.getString("father_name"));
        citizen.setMotherName(rs.getString("mother_name"));
        citizen.setWard(rs.getInt("ward"));
        citizen.setImagePath(rs.getString("image_path"));
        return citizen;
    }
}
