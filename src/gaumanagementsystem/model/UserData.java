/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
*/


package gaumanagementsystem.model;

public class UserData {
    // private attributes
    private String username;
    private String name;
    private String email;
    private String gender;
    private String password;
    private String confirmPassword;

    // Full constructor
    public UserData(String username, String name, String email,String gender, String password, String confirmPassword) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    // Constructor without confirmPassword (optional)
    public UserData(String username, String name, String email, String gender, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.gender = gender ;
        this.password = password;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender){
        this.gender = gender;
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

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getGender(){
        return this.gender;
    }
    
    public String getPassword() {
        return this.password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }
}
