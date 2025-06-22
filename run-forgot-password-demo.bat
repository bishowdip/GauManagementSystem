@echo off
echo Compiling Gau Management System - Forgot Password Demo Mode...

REM Compile the utility classes (skip EmailUtil due to missing JAR files)
javac -cp "src;lib/*" src/gaumanagementsystem/util/PasswordUtil.java
javac -cp "src;lib/*" src/gaumanagementsystem/util/EmailUtilDemo.java

REM Compile the model classes
javac -cp "src;lib/*" src/gaumanagementsystem/model/User.java

REM Compile the database classes
javac -cp "src;lib/*" src/gaumanagementsystem/database/Dbconnection.java
javac -cp "src;lib/*" src/gaumanagementsystem/database/MySqlConnection.java

REM Compile the DAO classes
javac -cp "src;lib/*" src/gaumanagementsystem/dao/UserDAO.java
javac -cp "src;lib/*" src/gaumanagementsystem/dao/impl/UserDAOImpl.java

REM Compile the controller classes
javac -cp "src;lib/*" src/gaumanagementsystem/controller/ForgotPasswordControllerDemo.java

REM Compile the view classes
javac -cp "src;lib/*" src/gaumanagementsystem/view/LoginView.java
javac -cp "src;lib/*" src/gaumanagementsystem/view/ForgotPasswordView.java

echo Compilation completed!
echo.
echo Starting Login with Forgot Password Demo...
echo.
echo DEMO MODE INSTRUCTIONS:
echo 1. Click on "Forgot Password?" link in the login screen
echo 2. The system will show the forgot password form
echo 3. Enter any valid email from your database
echo 4. Use verification code: 123456
echo 5. Enter a strong password meeting these requirements:
echo    - Minimum 8 characters
echo    - At least 1 uppercase letter (A-Z)
echo    - At least 1 lowercase letter (a-z)  
echo    - At least 1 digit (0-9)
echo    - At least 1 special character
echo.
echo Example strong passwords: MyPassword123!, SecurePass@2024, Admin#123456
echo.

REM Run the login view which now has forgot password functionality
java -cp "src;lib/*" gaumanagementsystem.view.LoginView

pause 