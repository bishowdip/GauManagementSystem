/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

import gaumanagementsystem.dao.UserDao;
import gaumanagementsystem.model.UserData;
import gaumanagementsystem.view.RegistrationView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author bisho
 */
public class RegisterController {

    private RegistrationView view = new RegistrationView();
    public RegisterController(RegistrationView view){
        this.view =view;

        this.view.registerUser(new RegisterUser());
        
    }
    public void open(){
        this.view.setVisible(true);
    }
    public void close(){
        this.view.dispose();
    }
    class RegisterUser implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getNameTextField().getText();
            String email = view.getEmailTextField().getText();
            String password = String.valueOf(view.getPasswordField().getPassword());
            String confirmPassword = String.valueOf(view.getConfirmPasswordField().getPassword());
            if(name.isEmpty()||email.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()){
                JOptionPane.showMessageDialog(view,"Fill in all the fields");
            }else if (!password.equals(confirmPassword)){
                JOptionPane.showMessageDialog(view, "Password do not match");
            }else {
                // if validated
            UserData user = new UserData(name,email,password);
            UserDao userDao = new UserDao();
            boolean result = userDao.register(user);
            
            if(result){
                JOptionPane.showMessageDialog(view, "Registered Successfully");
            }else{
                JOptionPane.showConfirmDialog(view, "Registration failed");
            }

            }
            
                        
        }
        }
 
}
