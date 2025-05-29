/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.dao;

import gaumanagementsystem.database.MySqlConnection;
import gaumanagementsystem.model.LoginRequest;
import gaumanagementsystem.model.UserData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;

/**
 *
 * @author bisho
 */
public class UserDao {
    MySqlConnection mySql = new MySqlConnection();
    public boolean register(UserData user){
        String query="INSERT INTO users(fname,email,fpassword) VALUES(?,?,?)";
        Connection conn = mySql.openConnection();
        try{
            PreparedStatement stmnt = conn.prepareStatement(query);
            stmnt.setString(1, user.getName());
            stmnt.setString(2, user.getName());
            stmnt.setString(3, user.getPassword());
            int result = stmnt.executeUpdate();
            return result>0;
                    
        
        
    }catch(Exception e){
        return false;
        
    }finally{
        mySql.closeConnection(conn);    
            }

        }
    public UserData login(LoginRequest loginReq){
        //step1:write query
        String query = "SELECT * FROM users WHERE email=? and fpassword=?";
        //step2 open connection
        Connection conn= mySql.openConnection();
        try{
            // 3 start prepared statement
            PreparedStatement stmnt = conn.prepareStatement(query);
            // 3 clear query if needed
            stmnt.setString(1,loginReq.getEmail());
            stmnt.setString(1, loginReq.getPassword());
            //execute query
            // always use executeQuery for select query
            //it returns rows so, use ResultSet
            ResultSet result= stmnt.executeQuery();
            if (result.next()){
                String id = result.getString("id");
                String name = result.getString("fname");
                String email = result.getString("email");
                String password= result.getString("password");
                UserData user = new UserData(id,name,email,password);
                return user;
            }
            else{
                return null;
            }
            } catch(Exception e){
                return null;
            } finally {
            mySql.closeConnection(conn);
        }
        
    }
}
