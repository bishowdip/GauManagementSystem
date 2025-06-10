/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
*/


package gaumanagementsystem.dao;

import gaumanagementsystem.database.MySqlConnection;
import gaumanagementsystem.model.User;
import java.sql.*;

public class UserDAO {
    private final MySqlConnection dbConnection;

<<<<<<< HEAD
    public UserDAO() {
        this.dbConnection = new MySqlConnection();
    }
=======

/**
 *
 * @author wangel
 */

public class UserDao {
    MySqlConnection mySql = new MySqlConnection();

    public boolean register(UserData user) {
        String query = "INSERT INTO users(username, email, role, fpassword) VALUES (?, ?, ?, ?)";
        Connection conn = mySql.openConnection();
>>>>>>> sambriddha

    public boolean checkUserExists(String username, String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
<<<<<<< HEAD
            conn = dbConnection.openConnection();
            String query = "SELECT * FROM users WHERE username = ? OR email = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, email);
            rs = stmt.executeQuery();
            return rs.next();
=======
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, user.getUsername());
            stmnt.setString(2, user.getEmail());
            stmnt.setString(3, user.getRole());
            stmnt.setString(4, user.getPassword());

            int result = stmnt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
>>>>>>> sambriddha
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) dbConnection.closeConnection(conn);
        }
    }

    public boolean registerUser(User user) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = dbConnection.openConnection();
            String query = "INSERT INTO users (name, username, email, gender, fpassword) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getGender());
            stmt.setString(5, user.getPassword());
            
            int result = stmt.executeUpdate();
            return result > 0;
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) dbConnection.closeConnection(conn);
        }
    }

<<<<<<< HEAD
    public User authenticateUser(String email, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dbConnection.openConnection();
            String query = "SELECT * FROM users WHERE email = ? AND fpassword = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setGender(rs.getString("gender"));
                return user;
=======
            ResultSet result = stmnt.executeQuery();

            if (result.next()) {
                String username = result.getString("username");
                String email = result.getString("email");
                String password = result.getString("fpassword");                
                String role = result.getString("role");

                return new UserData(username, email, role, password);
            } else {
                return null;
>>>>>>> sambriddha
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) dbConnection.closeConnection(conn);
        }
    }
}
