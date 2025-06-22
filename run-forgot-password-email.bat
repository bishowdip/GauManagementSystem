@echo off
echo ================================================================
echo    Gau Management System - Forgot Password with REAL EMAIL
echo ================================================================
echo.

REM Check if all required JAR files exist
if not exist "lib\javax.mail-1.6.2.jar" (
    echo ❌ ERROR: javax.mail-1.6.2.jar not found
    goto :missing_jars
)

if not exist "lib\activation-1.1.1.jar" (
    echo ❌ ERROR: activation-1.1.1.jar not found
    goto :missing_jars
)

if not exist "lib\mysql-connector-java-8.0.28.jar" (
    echo ❌ ERROR: mysql-connector-java-8.0.28.jar not found
    goto :missing_jars
)

echo ✅ All required JAR files found!
echo   📧 Email: javax.mail-1.6.2.jar (644KB)
echo   🔧 Activation: activation-1.1.1.jar (68KB)
echo   🗄️ MySQL: mysql-connector-java-8.0.28.jar (2.4MB)
echo.

echo Compiling with REAL EMAIL support...
echo.

REM Compile utility classes
javac -cp "src;lib/*" src/gaumanagementsystem/util/PasswordUtil.java
if %errorlevel% neq 0 goto :compile_error

javac -cp "src;lib/*" src/gaumanagementsystem/util/EmailUtil.java
if %errorlevel% neq 0 goto :compile_error

REM Compile model classes
javac -cp "src;lib/*" src/gaumanagementsystem/model/User.java
if %errorlevel% neq 0 goto :compile_error

REM Compile database classes
javac -cp "src;lib/*" src/gaumanagementsystem/database/Dbconnection.java
if %errorlevel% neq 0 goto :compile_error

javac -cp "src;lib/*" src/gaumanagementsystem/database/MySqlConnection.java
if %errorlevel% neq 0 goto :compile_error

REM Compile DAO classes
javac -cp "src;lib/*" src/gaumanagementsystem/dao/UserDAO.java
if %errorlevel% neq 0 goto :compile_error

javac -cp "src;lib/*" src/gaumanagementsystem/dao/impl/UserDAOImpl.java
if %errorlevel% neq 0 goto :compile_error

REM Compile controller classes
javac -cp "src;lib/*" src/gaumanagementsystem/controller/ForgotPasswordController.java
if %errorlevel% neq 0 goto :compile_error

REM Compile view classes
javac -cp "src;lib/*" src/gaumanagementsystem/view/LoginView.java
if %errorlevel% neq 0 goto :compile_error

javac -cp "src;lib/*" src/gaumanagementsystem/view/ForgotPasswordView.java
if %errorlevel% neq 0 goto :compile_error

echo ✅ Compilation successful!
echo.
echo ================================================================
echo                    REAL EMAIL FUNCTIONALITY
echo ================================================================
echo.
echo 📧 Email Configuration:
echo   Email: bishodip123@gmail.com
echo   App Password: mzhg bqng svnf hftk
echo   SMTP: smtp.gmail.com:587 (TLS)
echo.
echo 🗄️ Database Configuration:
echo   Database: gau_management
echo   Username: root
echo   Password: Akg@nepal123
echo.
echo 🚀 Instructions:
echo   1. Enter your registered email address
echo   2. Real verification code will be sent to that email
echo   3. Check your email inbox (and spam folder)
echo   4. Enter the 6-digit code from the email
echo   5. Set a strong new password
echo.
echo ⏰ Note: Verification codes expire in 10 minutes
echo.

java -cp "src;lib/*" gaumanagementsystem.view.ForgotPasswordView
goto :end

:missing_jars
echo.
echo ================================================================
echo                    MISSING JAR FILES
echo ================================================================
echo.
echo Please download the following files to the 'lib' directory:
echo.
echo 1. javax.mail-1.6.2.jar
echo    https://repo1.maven.org/maven2/com/sun/mail/javax.mail/1.6.2/javax.mail-1.6.2.jar
echo.
echo 2. activation-1.1.1.jar
echo    https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar
echo.
echo 3. mysql-connector-java-8.0.28.jar
echo    https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.28/mysql-connector-java-8.0.28.jar
echo.
goto :end

:compile_error
echo.
echo ❌ Compilation failed! Check the error messages above.
echo.
goto :end

:end
pause 