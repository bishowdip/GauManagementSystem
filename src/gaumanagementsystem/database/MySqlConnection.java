/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.database;
import java.sql.*;
/**
 *
 * @author bisho
 */
public class MySqlConnection {
     public Connection openConnection() {
    try{
    String username="root";
    String password= "Akg@nepal123";
    String database="gau_management";
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conn;
    conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database,username,password);
    return conn;
    }catch(Exception e){
        e.printStackTrace();
        return null;    
    }

    }    
    
    

//    @Override
    public void closeConnection(Connection conn) {
        try{
            if (conn!=null &&! conn.isClosed()){
                conn.close();
            }
        
        } catch(Exception e){
            e.printStackTrace();
        }
    
    }  
}
