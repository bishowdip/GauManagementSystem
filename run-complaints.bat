@echo off
echo Starting Complaints and Feedback Management System...
echo.

REM Set the MySQL connector path
set MYSQL_CONNECTOR=D:\GauManagementSystem\mysql-connector-j-9.2.0.jar

REM Check if MySQL connector exists
if not exist "%MYSQL_CONNECTOR%" (
    echo ERROR: MySQL connector not found at %MYSQL_CONNECTOR%
    echo Please ensure mysql-connector-j-9.2.0.jar is available at the specified path.
    echo.
    echo Alternative locations to check:
    echo - Current directory: %CD%
    echo - MySQL installation directory
    echo - Project lib directory
    pause
    exit /b 1
)

echo Found MySQL connector at: %MYSQL_CONNECTOR%

REM Navigate to source directory
cd /d "%~dp0src"

REM Compile the Complaints_Tables class
echo Compiling Complaints_Tables...
javac -cp ".;%MYSQL_CONNECTOR%" gaumanagementsystem/view/Complaints_Tables.java

if %ERRORLEVEL% neq 0 (
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Run the application
echo Starting Complaints and Feedback Management System...
echo.
echo ROLE-BASED ACCESS CONTROL:
echo - Admin: Full access to all complaints and feedback
echo - User: Can see own complaints + all feedback (other users' complaints hidden)
echo - User: Can modify/delete only own complaints (feedback is read-only)
echo.
echo Note: Make sure MySQL server is running and database 'gau_management' exists
echo Database credentials: root/Akg@nepal123
echo.

java -cp ".;%MYSQL_CONNECTOR%" gaumanagementsystem.view.Complaints_Tables

if %ERRORLEVEL% neq 0 (
    echo.
    echo ERROR: Application failed to start!
    echo Please check:
    echo 1. MySQL server is running
    echo 2. Database 'gau_management' exists
    echo 3. Database credentials are correct
    echo 4. MySQL connector is in classpath
    pause
    exit /b 1
)

echo.
echo Application closed successfully.
pause 