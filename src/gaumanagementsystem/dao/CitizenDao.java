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
import java.sql.SQLException;

public class CitizenDao {
    private Connection conn;

    public CitizenDao(Connection conn) {
        this.conn = conn;
    }

    public boolean updateCitizen(CitizenData citizen) {
        String sql = "UPDATE citizens SET name = ?, email = ?, date_of_birth = ?, address = ?, gender = ?, phone = ?, father_name = ?, mother_name = ?, image_path = ? WHERE citizen_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, citizen.getName());
            stmt.setString(2, citizen.getEmail());
            stmt.setString(3, citizen.getDateOfBirth());
            stmt.setString(4, citizen.getAddress());
            stmt.setString(5, citizen.getGender());
            stmt.setString(6, citizen.getPhone());
            stmt.setString(7, citizen.getFatherName());
            stmt.setString(8, citizen.getMotherName());
            stmt.setString(9, citizen.getImagePath());
            stmt.setString(10, citizen.getCitizenId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
