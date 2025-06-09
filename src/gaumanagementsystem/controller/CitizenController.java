/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

/**
 *
 * @author wangel
 */
import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class CitizenController {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String DB_USER = "your_db_user";
    private static final String DB_PASS = "your_db_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // 1. Get All Citizens
    public static DefaultTableModel getAllCitizens() {
        String[] columns = {"Citizen-number", "Name", "Phone", "Address", "Gender", "Email", "Father_name", "Mother_name"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String query = "SELECT * FROM citizens";

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = {
                    rs.getString("citizen_number"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("gender"),
                    rs.getString("email"),
                    rs.getString("father_name"),
                    rs.getString("mother_name")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }

    // 2. Get Citizen by ID
    public static Map<String, String> getCitizenById(String citizenNumber) {
        String query = "SELECT * FROM citizens WHERE citizen_number = ?";
        Map<String, String> citizen = new HashMap<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, citizenNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                citizen.put("name", rs.getString("name"));
                citizen.put("phone", rs.getString("phone"));
                citizen.put("address", rs.getString("address"));
                citizen.put("gender", rs.getString("gender"));
                citizen.put("email", rs.getString("email"));
                citizen.put("father_name", rs.getString("father_name"));
                citizen.put("mother_name", rs.getString("mother_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return citizen;
    }

    // 3. Update Citizen
    public static boolean updateCitizen(String citizenNumber, String name, String phone,
                                        String address, String gender, String email) {
        String query = "UPDATE citizens SET name=?, phone=?, address=?, gender=?, email=? WHERE citizen_number=?";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, address);
            stmt.setString(4, gender);
            stmt.setString(5, email);
            stmt.setString(6, citizenNumber);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
