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
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author wangel
 */

public class UserDao {
    MySqlConnection mySql = new MySqlConnection();

    // CREATE - Register new user
    public boolean register(UserData user) {
        // First check if email already exists
        if (emailExists(user.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        String query = "INSERT INTO users(email, role, fpassword) VALUES (?, ?, ?)";
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

    // READ - Login user
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

    // READ - Get user by email
    public UserData getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        Connection conn = mySql.openConnection();

        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, email);

            ResultSet result = stmnt.executeQuery();

            if (result.next()) {
                String userEmail = result.getString("email");
                String password = result.getString("fpassword");                
                String role = result.getString("role");

                return new UserData(userEmail, role, password);
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

    // READ - Get all users
    public List<UserData> getAllUsers() {
        List<UserData> users = new ArrayList<>();
        String query = "SELECT * FROM users ORDER BY email";
        Connection conn = mySql.openConnection();

        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            ResultSet result = stmnt.executeQuery();

            while (result.next()) {
                String email = result.getString("email");
                String password = result.getString("fpassword");                
                String role = result.getString("role");

                users.add(new UserData(email, role, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return users;
    }

    // READ - Get users by role
    public List<UserData> getUsersByRole(String role) {
        List<UserData> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = ? ORDER BY email";
        Connection conn = mySql.openConnection();

        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, role);
            ResultSet result = stmnt.executeQuery();

            while (result.next()) {
                String email = result.getString("email");
                String password = result.getString("fpassword");                
                String userRole = result.getString("role");

                users.add(new UserData(email, userRole, password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }
        return users;
    }

    // UPDATE - Update user password
    public boolean updateUserPassword(String email, String newPassword) {
        String query = "UPDATE users SET fpassword = ? WHERE email = ?";
        Connection conn = mySql.openConnection();

        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, newPassword);
            stmnt.setString(2, email);

            int result = stmnt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    // UPDATE - Update user role
    public boolean updateUserRole(String email, String newRole) {
        String query = "UPDATE users SET role = ? WHERE email = ?";
        Connection conn = mySql.openConnection();

        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, newRole);
            stmnt.setString(2, email);

            int result = stmnt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    // UPDATE - Update user completely
    public boolean updateUser(UserData user) {
        String query = "UPDATE users SET role = ?, fpassword = ? WHERE email = ?";
        Connection conn = mySql.openConnection();

        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, user.getRole());
            stmnt.setString(2, user.getPassword());
            stmnt.setString(3, user.getEmail());

            int result = stmnt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    // DELETE - Delete user by email
    public boolean deleteUser(String email) {
        String query = "DELETE FROM users WHERE email = ?";
        Connection conn = mySql.openConnection();

        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, email);

            int result = stmnt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    // DELETE - Delete multiple users
    public boolean deleteMultipleUsers(List<String> emails) {
        if (emails == null || emails.isEmpty()) {
            return false;
        }
        
        String placeholders = String.join(",", java.util.Collections.nCopies(emails.size(), "?"));
        String query = "DELETE FROM users WHERE email IN (" + placeholders + ")";
        Connection conn = mySql.openConnection();

        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            for (int i = 0; i < emails.size(); i++) {
                stmnt.setString(i + 1, emails.get(i));
            }

            int result = stmnt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    // Utility methods
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

    // Check if user exists
    public boolean userExists(String email) {
        return emailExists(email);
    }

    // Get total user count
    public int getUserCount() {
        String query = "SELECT COUNT(*) FROM users";
        Connection conn = mySql.openConnection();
        
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            ResultSet rs = stmnt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            mySql.closeConnection(conn);
        }
    }

    // Get user count by role
    public int getUserCountByRole(String role) {
        String query = "SELECT COUNT(*) FROM users WHERE role = ?";
        Connection conn = mySql.openConnection();
        
        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, role);
            ResultSet rs = stmnt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            mySql.closeConnection(conn);
        }
    }
}
