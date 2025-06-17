/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

import gaumanagementsystem.model.User;
import gaumanagementsystem.view.DashboardUser;

/**
 *
 * @author bisho
 */
public class DashboardController {
    private DashboardUser view;
    private User user;
    public DashboardController(DashboardUser view, User user){
        this.view=view;
        this.user=user;
    }
    public void  open(){
        view.setVisible(true);
    }
    public void close(){
        view.dispose();
    }

}
