@echo off
echo Starting Hamro Smart Gaun Dashboard...
echo.

REM Set the MySQL connector path
set MYSQL_CONNECTOR=D:\GauManagementSystem\mysql-connector-j-9.2.0.jar

REM Check if MySQL connector exists
if not exist "%MYSQL_CONNECTOR%" (
    echo ERROR: MySQL connector not found at %MYSQL_CONNECTOR%
    echo Please ensure mysql-connector-j-9.2.0.jar is available at the specified path.
    pause
    exit /b 1
)

echo Found MySQL connector at: %MYSQL_CONNECTOR%

REM Navigate to source directory
cd /d "%~dp0src"

REM Compile the Dashboard classes
echo Compiling Dashboard components...
javac -cp ".;%MYSQL_CONNECTOR%" gaumanagementsystem/view/DashboardView.java
javac -cp ".;%MYSQL_CONNECTOR%" gaumanagementsystem/view/LoginView.java

if %ERRORLEVEL% neq 0 (
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Run the application
echo Starting Dashboard...
echo.
echo FEATURES:
echo - Multi-user role support (Admin/User)
echo - Complete module navigation
echo - Secure logout functionality
echo.
echo LOGOUT FUNCTIONALITY:
echo - Click 'Logout' button to sign out
echo - Confirmation dialog will appear
echo - Dashboard closes and returns to Login screen
echo.

java -cp ".;%MYSQL_CONNECTOR%" gaumanagementsystem.view.DashboardView

if %ERRORLEVEL% neq 0 (
    echo.
    echo ERROR: Application failed to start!
    echo Please check:
    echo 1. Java is properly installed
    echo 2. All required dependencies are available
    pause
    exit /b 1
)

echo.
echo Application closed successfully.
pause 