/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package gaumanagementsystem.dao;

import gaumanagementsystem.model.LoginRequest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bisho
 */
public class UserDAOTest {
    String correctEmail="testt@gmail.com";
    String correctName="Testt user";
    String password="passwordfortest";
    UserDAO dao = new UserDAO()
    @Test
    public void registerWithNewDetails(){
        UserData user = new UserData(correctName,correctEmail,password);
        boolean result = dao.register(user);
        Assert.assertTrue("Register should work with  unique details",result);
    }
    
    @Test
    public void registerWithDuplicateDetails(){
        UserData user = new UserData(correctName,correctEmail,password);
        boolean result = dao.register(user);
        Assert.assertFalse("Registration should be failed with dublicate email", result);
    }
    @Test
    public void loginWithCorrectDetails(){
        LoginRequest req= new LoginRequest(correctEmail,password);
        UserData user= dao.login(req);
        Assert.assertNotNull("User should not be null", this);
        Assert.assertEquals("Name should match", correctName, user.getName());
        Assert.assertEquals("Email should match", correctEmail, user.getEmail());
        Assert.assertEquals("password should match", password,user.getPassword());
    }
    @Test
    public void LoginWithinvalidCreds(){
        LoginRequest req=new LoginRequest("abc@test.com","iouytre");
        UserData user=dao.login(req);
        Assert.assertNull("User should be null",user);
    }
   
}
