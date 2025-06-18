/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

import gaumanagementsystem.model.UserData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author bisho
 */
public class DashboardController {
    private final DashboardUser view;
    public DashboardController(DashboardUser view, UserData user){
        this.view=view;
    }
    public void  open(){
        view.setVisible(true);
     
    public void close(){
        view.dispose();
    }
    class DashboardUser implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
        
        }
        
        
        }

    }

        


