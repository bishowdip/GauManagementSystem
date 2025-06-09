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

    public UserDAO() {
        this.dbConnection = new MySqlConnection();
    }

    public boolean checkUserExists(String username, String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = dbConnection.openConnection();
            String query = "SELECT * FROM users WHERE username = ? OR email = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, email);
            rs = stmt.executeQuery();
            return rs.next();
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
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) dbConnection.closeConnection(conn);
        }
    }
}
