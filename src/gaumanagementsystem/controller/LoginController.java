/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

import gaumanagementsystem.dao.UserDao;
import gaumanagementsystem.model.LoginRequest;
import gaumanagementsystem.model.UserData;
import gaumanagementsystem.view.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author bisho
 */
public class LoginController {
    LoginView view;
    public LoginController(LoginView view){
        this.view=view;
        LoginUser login = new LoginUser();
        this.view.loginUser(login);
    }
    public void open(){
        view.setVisible(true);
    }
    public void close(){
        view.dispose();
    }
    class LoginUser implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmailTextField().getText();
            String password = String.valueOf(view.getPasswordField().getPassword());
            if (email.isEmpty()||password.isEmpty()){
                JOptionPane.showMessageDialog(view, "Fill in all the fields");
            }else{
                LoginRequest loginData = new LoginRequest(email,password);
                UserDao userdao = new UserDao();
                UserData user = userdao.login(loginData);
                if (user==null){
                    JOptionPane.showMessageDialog(view, "Invalid credentials");
                }else{
                    
                }
            }
        
        }
        
    }
}
