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
public interface Dbconnection {
    Connection openConnection();
    void closeConnection(Connection conn);
}

class DatabaseConnection implements Dbconnection {
    private static final String URL = "jdbc:mysql://localhost:3306/gau_management";
    private static final String USER = "root";
    private static final String PASSWORD = "Akg@nepal123";
   
    @Override
    public Connection openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

    
    
    

