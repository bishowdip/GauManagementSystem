@echo off
REM News and Notice Application Startup Script
REM This script ensures the MySQL connector is included in the classpath

echo Starting News and Notice Application...
echo Checking MySQL connector...

REM Check if MySQL connector exists
if not exist "D:\GauManagementSystem\mysql-connector-j-9.2.0.jar" (
    echo ERROR: MySQL connector not found at D:\GauManagementSystem\mysql-connector-j-9.2.0.jar
    echo Please make sure the MySQL connector JAR file is in the correct location.
    pause
    exit /b 1
)

echo MySQL connector found!
echo Starting application...

REM Navigate to source directory
cd /d "%~dp0src"

REM Compile the application (if needed)
echo Compiling application...
javac -cp ".;D:\GauManagementSystem\mysql-connector-j-9.2.0.jar" gaumanagementsystem\view\NewsAndNotice.java

REM Run the News and Notice application
echo Running News and Notice application...
java -cp ".;D:\GauManagementSystem\mysql-connector-j-9.2.0.jar" gaumanagementsystem.view.NewsAndNotice

echo Application closed.
pause 