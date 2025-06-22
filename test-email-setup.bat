@echo off
echo Testing Email Setup for Gau Management System...
echo.

REM Check if JAR files exist
if not exist "lib\javax.mail-1.6.2.jar" (
    echo ❌ ERROR: javax.mail-1.6.2.jar not found in lib directory
    echo Please download it from: https://repo1.maven.org/maven2/com/sun/mail/javax.mail/1.6.2/javax.mail-1.6.2.jar
    goto :error
)

if not exist "lib\activation-1.1.1.jar" (
    echo ❌ ERROR: activation-1.1.1.jar not found in lib directory  
    echo Please download it from: https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar
    goto :error
)

echo ✅ JAR files found!
echo.

echo Compiling with email support...
javac -cp "src;lib/*" src/gaumanagementsystem/util/PasswordUtil.java
javac -cp "src;lib/*" src/gaumanagementsystem/util/EmailUtil.java
javac -cp "src;lib/*" src/gaumanagementsystem/model/User.java
javac -cp "src;lib/*" src/gaumanagementsystem/database/Dbconnection.java
javac -cp "src;lib/*" src/gaumanagementsystem/database/MySqlConnection.java
javac -cp "src;lib/*" src/gaumanagementsystem/dao/UserDAO.java
javac -cp "src;lib/*" src/gaumanagementsystem/dao/impl/UserDAOImpl.java
javac -cp "src;lib/*" src/gaumanagementsystem/controller/ForgotPasswordController.java
javac -cp "src;lib/*" src/gaumanagementsystem/view/LoginView.java
javac -cp "src;lib/*" src/gaumanagementsystem/view/ForgotPasswordView.java

if %errorlevel% neq 0 (
    echo ❌ Compilation failed! Check the error messages above.
    goto :error
)

echo ✅ Compilation successful!
echo.
echo 🚀 Starting Forgot Password with REAL EMAIL functionality...
echo.
echo Email Configuration:
echo   📧 Email: bishodip123@gmail.com
echo   🔑 App Password: mzhg bqng svnf hftk
echo.
echo Instructions:
echo   1. Enter your registered email address
echo   2. Check your email inbox for the verification code
echo   3. Enter the received 6-digit code
echo   4. Set a strong new password
echo.

java -cp "src;lib/*" gaumanagementsystem.view.ForgotPasswordView
goto :end

:error
echo.
echo ================================================================
echo                         SETUP REQUIRED
echo ================================================================
echo.
echo To get email functionality working:
echo.
echo 1. Download javax.mail-1.6.2.jar from:
echo    https://repo1.maven.org/maven2/com/sun/mail/javax.mail/1.6.2/javax.mail-1.6.2.jar
echo.
echo 2. Download activation-1.1.1.jar from:
echo    https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar
echo.
echo 3. Place both files in the 'lib' directory
echo.
echo 4. Run this script again
echo.
echo Alternative: Run 'run-forgot-password-demo.bat' for demo mode
echo.

:end
pause 