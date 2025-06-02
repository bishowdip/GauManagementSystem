/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
*/


package gaumanagementsystem.model;

public class UserData {
    // private attributes
    private String username;
    private String email;
    private String role;
    private String password;
    private String confirmPassword;

    // Full constructor
    public UserData(String username, String name, String email,String role, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Constructor without confirmPassword (optional)
    public UserData(String username, String name, String email, String role, String password) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role){
        this.role = role;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    // Getters
    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getRole(){
        return this.role;
    }
    
    public String getPassword() {
        return this.password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }
}
