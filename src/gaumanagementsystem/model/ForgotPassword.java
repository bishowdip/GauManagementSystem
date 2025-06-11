/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.model;

/**
 *
 * @author ASUS
 */
public class ForgotPassword {
    

   private String username;
    private String favoriteAnimal;
    private String newPassword;
    private String repeatNewPassword;
    private boolean showPassword;
    private boolean showRepeatPassword;
    
    // Parameterized constructor
    public ForgotPassword (String username, String favoriteAnimal, 
                              String newPassword, String repeatNewPassword) {
        this.username = username;
        this.favoriteAnimal = favoriteAnimal;
        this.newPassword = newPassword;
        this.repeatNewPassword = repeatNewPassword;
        this.showPassword = false;
        this.showRepeatPassword = false;
    }
    
    // Getter and Setter for Username
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    // Getter and Setter for Favorite Animal (Security Question)
    public String getFavoriteAnimal() {
        return favoriteAnimal;
    }
    
    public void setFavoriteAnimal(String favoriteAnimal) {
        this.favoriteAnimal = favoriteAnimal;
    }
    
    // Getter and Setter for New Password
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    // Getter and Setter for Repeat New Password
    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }
    
    public void setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
    }
    
    // Getter and Setter for Show Password flag
    public boolean isShowPassword() {
        return showPassword;
    }
    
    public void setShowPassword(boolean showPassword) {
        this.showPassword = showPassword;
    }
    
    // Getter and Setter for Show Repeat Password flag
    public boolean isShowRepeatPassword() {
        return showRepeatPassword;
    }
    
    public void setShowRepeatPassword(boolean showRepeatPassword) {
        this.showRepeatPassword = showRepeatPassword;
    }
}


       
    
 
    

