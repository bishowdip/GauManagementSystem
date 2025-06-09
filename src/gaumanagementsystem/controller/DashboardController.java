/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gaumanagementsystem.controller;

import gaumanagementsystem.model.UserData;
import gaumanagementsystem.view.DashboardView;

/**
 *
 * @author bisho
 */
public class DashboardController {
    private DashboardView view;
    private UserData user;
    public DashboardController(DashboardView view, UserData user){
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
