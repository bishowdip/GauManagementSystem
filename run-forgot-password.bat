@echo off
echo Compiling Gau Management System - Forgot Password Module...

REM Compile the utility classes first
javac -cp "src;lib/*" src/gaumanagementsystem/util/PasswordUtil.java
javac -cp "src;lib/*" src/gaumanagementsystem/util/EmailUtil.java

REM Compile the model classes
javac -cp "src;lib/*" src/gaumanagementsystem/model/User.java

REM Compile the database classes
javac -cp "src;lib/*" src/gaumanagementsystem/database/Dbconnection.java
javac -cp "src;lib/*" src/gaumanagementsystem/database/MySqlConnection.java

REM Compile the DAO classes
javac -cp "src;lib/*" src/gaumanagementsystem/dao/UserDAO.java
javac -cp "src;lib/*" src/gaumanagementsystem/dao/impl/UserDAOImpl.java

REM Compile the controller classes
javac -cp "src;lib/*" src/gaumanagementsystem/controller/ForgotPasswordController.java

REM Compile the view classes
javac -cp "src;lib/*" src/gaumanagementsystem/view/LoginView.java
javac -cp "src;lib/*" src/gaumanagementsystem/view/ForgotPasswordView.java

echo Compilation completed!
echo.
echo Starting Forgot Password Demo...
echo.
echo Demo Mode Instructions:
echo 1. Enter any valid email address from your database
echo 2. Use verification code: 123456
echo 3. Enter a strong password (min 8 chars, uppercase, lowercase, digits, special chars)
echo.

REM Run the forgot password view
java -cp "src;lib/*" gaumanagementsystem.view.ForgotPasswordView

pause