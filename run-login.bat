@echo off
echo Starting Login Module...
echo.

REM Check if MySQL connector exists
if not exist "lib\mysql-connector-java-8.0.33.jar" (
    echo Warning: MySQL connector not found at lib\mysql-connector-java-8.0.33.jar
    echo Please ensure the MySQL JDBC driver is in the lib directory
    echo.
)

REM Compile the LoginView and dependencies if needed
echo Compiling LoginView and security utilities...
javac -cp "src;lib/*" src/gaumanagementsystem/util/PasswordUtil.java
javac -cp "src;lib/*" src/gaumanagementsystem/controller/UserController.java
javac -cp "src;lib/*" src/gaumanagementsystem/view/LoginView.java
if %ERRORLEVEL% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Run the LoginView
echo Running Login Module...
java -cp "src;lib/*" gaumanagementsystem.view.LoginView

echo.
echo Login Module closed.
pause 