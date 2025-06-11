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


public class UserDao {
    MySqlConnection mySql = new MySqlConnection();

    public boolean register(UserData user) {
        String query = "INSERT INTO users(name, username, email, gender, fpassword, cpassword) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = mySql.openConnection();

        try {
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, user.getName());
            stmnt.setString(2, user.getUsername());
            stmnt.setString(3, user.getEmail());
            stmnt.setString(4, user.getGender());
            stmnt.setString(5, user.getPassword());
            stmnt.setString(6, user.getConfirmPassword());


            int result = stmnt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
                String username = result.getString("username");
                String name = result.getString("name");                
                String gender = result.getString("gender");

                String email = result.getString("email");
                String password = result.getString("fpassword");
                return new UserData(username, name, email,gender, password);
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

    public boolean forgotPassword(String email, String newPassword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
    
   