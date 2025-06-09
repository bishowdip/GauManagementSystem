/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.dao;

import gaumanagementsystem.database.MySqlConnection;
import gaumanagementsystem.model.CitizenData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author wangel
 */

public class CitizenDao {
    private MySqlConnection mySql = new MySqlConnection();

    // Fetch citizen by ID
    public CitizenData getCitizenById(String citizenId) {
        String query = "SELECT * FROM citizens WHERE citizen_id = ?";
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, citizenId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CitizenData(
                    rs.getString("citizen_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("gender"),
                    rs.getString("phone"),
                    rs.getString("father_name"),
                    rs.getString("mother_name")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update citizen record
    public boolean updateCitizen(CitizenData citizen) {
        String query = "UPDATE citizens SET name = ?, email = ?, address = ?, gender = ?, phone = ?, father_name = ?, mother_name = ? WHERE citizen_id = ?";
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, citizen.getName());
            stmt.setString(2, citizen.getEmail());
            stmt.setString(3, citizen.getAddress());
            stmt.setString(4, citizen.getGender());
            stmt.setString(5, citizen.getPhone());
            stmt.setString(6, citizen.getFathername());
            stmt.setString(7, citizen.getMothername());
            stmt.setString(8, citizen.getCitizen_id());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
