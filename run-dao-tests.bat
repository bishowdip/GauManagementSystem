@echo off
echo ========================================
echo    GAU MANAGEMENT SYSTEM - DAO TESTS
echo ========================================
echo.
echo This script will run comprehensive tests for all DAO classes
echo Make sure your MySQL database is running and accessible
echo.
echo Database Configuration:
echo   Database: gau_management
echo   Username: root
echo   Password: Akg@nepal123
echo.
pause

echo Compiling DAO test classes...
javac -cp "src;lib/mysql-connector-java-8.0.28.jar" src/gaumanagementsystem/test/dao/*.java

if %ERRORLEVEL% EQU 0 (
    echo ✓ Compilation successful!
    echo.
    echo Running all DAO tests...
    echo.
    java -cp "src;lib/mysql-connector-java-8.0.28.jar" gaumanagementsystem.test.dao.AllDAOTestRunner
) else (
    echo ✗ Compilation failed!
    echo Please check for compilation errors and try again.
)

echo.
echo ========================================
echo           TESTS COMPLETED
echo ========================================
pause 