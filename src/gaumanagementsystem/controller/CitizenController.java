/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

/**
 *
 * @author wangel
 */

import gaumanagementsystem.model.CitizenData;
import java.sql.*;

public class CitizenController {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String DB_USER = "your_db_user";
    private static final String DB_PASS = "your_db_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static CitizenData getCitizenById(String citizenId) {
        String query = "SELECT * FROM citizens WHERE citizen_id = ?";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, citizenId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CitizenData(
                        citizenId,
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("date_of_birth"),
                        rs.getString("address"),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("father_name"),
                        rs.getString("mother_name"),
                        rs.getString("image_path")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
