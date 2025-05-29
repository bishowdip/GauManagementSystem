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
public class Dbconnection {
    Connection openConnection();
    void closeConnection(Connection conn);
    
}
