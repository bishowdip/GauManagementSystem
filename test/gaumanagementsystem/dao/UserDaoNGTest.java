/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package gaumanagementsystem.dao;

import gaumanagementsystem.model.LoginRequest;
import gaumanagementsystem.model.UserData;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author ASUS
 */
public class UserDaoNGTest {
    
    public UserDaoNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of register method, of class UserDao.
     */
    @Test
    public void testRegister() {
        System.out.println("register");
        UserData user = null;
        UserDao instance = new UserDao();
        boolean expResult = false;
        boolean result = instance.register(user);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of login method, of class UserDao.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        LoginRequest loginReq = null;
        UserDao instance = new UserDao();
        UserData expResult = null;
        UserData result = instance.login(loginReq);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of forgotPassword method, of class UserDao.
     */
    @Test
    public void testForgotPassword() {
        System.out.println("forgotPassword");
        String email = "";
        String newPassword = "";
        UserDao instance = new UserDao();
        boolean expResult = false;
        boolean result = instance.forgotPassword(email, newPassword);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    
    
    
    
    public class UserDaoTest {
        String correctEmail="testt@gmail.com";
        String correctName="Testt user";
        String password="passwordfortestt";
    }
        @Test
        public void registerWithNewDetails(){
            UserData user;
        user = new UserData(correctName,correctEmail,password);
            boolean result= dao.register(user);
            Assert.assertTrue("Register should work with unique details",result);
    }
        @Test
        public void registerWithDuplicateDetails(){
            UserData user= new UserData(correctName,correctEmail,password);
            boolean result=dao.register(user);
            Assert.assertFalse("Register should fail with duplicate credentials",result);
        }
        @Test
        public void loginWithCorrectCreds(){
            LoginRequest req= new LoginRequest(correctEmail, password);
            UserData user= dao.login(req);
            Assert.assertNotNull("User should not be null",user);
            Assert.assertEquals("Name should match",corectName,user.getName());
            Assert.assertEquals("Email should match",correctEmail,user.getEmail());
            Assert.assertEquals("Password should match",password,user.getPassword());
        }
}
