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
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Akg@nepal123";
    private static final String DATABASE = "gau_management";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE;
    
    public Connection openConnection() {
        try {
            System.out.println("Attempting to connect to database: " + DATABASE);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connection successful!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found: " + e.getMessage());
            e.printStackTrace();
            return null;    
        } catch (SQLException e) {
            System.err.println("Database Connection Error: " + e.getMessage());
            System.err.println("URL: " + URL);
            System.err.println("Username: " + USERNAME);
            System.err.println("Make sure MySQL server is running and database '" + DATABASE + "' exists");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error during database connection: " + e.getMessage());
            e.printStackTrace();
            return null;    
        }
    }    
    
    public void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }  
}
