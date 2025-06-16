/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
*/


package gaumanagementsystem.dao;

import gaumanagementsystem.database.MySqlConnection;
import gaumanagementsystem.model.LoginRequest;
import gaumanagementsystem.model.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author wangel
 */

public class UserDao {
    MySqlConnection mySql = new MySqlConnection();

    private boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        Connection conn = mySql.openConnection();
        
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, email);
            ResultSet rs = stmnt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public boolean register(UserData user) {
        // First check if email already exists
        if (emailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        String query = "INSERT INTO users(email, role, password) VALUES (?, ?, ?)";
        Connection conn = mySql.openConnection();

        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, user.getEmail());
            stmnt.setString(2, user.getRole());
            stmnt.setString(3, user.getPassword());

            int result = stmnt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().contains("Duplicate entry")) {
                throw new IllegalArgumentException("Email already registered");
            }
            throw new RuntimeException("Database error during registration", e);
        } finally {
            mySql.closeConnection(conn);
        }
    }

    public UserData login(LoginRequest loginReq) {
        String query = "SELECT * FROM users WHERE email = ? AND fpassword = ?";
        Connection conn = mySql.openConnection();

        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, loginReq.getEmail());
            stmnt.setString(2, loginReq.getPassword());

            ResultSet result = stmnt.executeQuery();

            if (result.next()) {
                String email = result.getString("email");
                String password = result.getString("fpassword");                
                String role = result.getString("role");

                return new UserData(email, role, password);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            mySql.closeConnection(conn);
        }
    }
}
