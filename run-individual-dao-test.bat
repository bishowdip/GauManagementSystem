@echo off
echo ========================================
echo   INDIVIDUAL DAO TEST RUNNER
echo ========================================
echo.
echo Available DAO Tests:
echo   1. UserDAO Test
echo   2. ComplaintDAO Test
echo   3. ProjectRequestDAO Test
echo   4. NewsAndNoticeDAO Test
echo   5. ServiceDAO Test
echo   6. BudgetAllocationDAO Test
echo   7. CitizenDAO Test
echo   8. Run All Tests
echo.
set /p choice="Enter your choice (1-8): "

echo.
echo Compiling test classes...
javac -cp "src;lib/mysql-connector-java-8.0.28.jar" src/gaumanagementsystem/test/dao/*.java

if %ERRORLEVEL% NEQ 0 (
    echo ✗ Compilation failed!
    pause
    exit /b 1
)

echo ✓ Compilation successful!
echo.

if "%choice%"=="1" (
    echo Running UserDAO Test...
    java -cp "src;lib/mysql-connector-java-8.0.28.jar" gaumanagementsystem.test.dao.UserDAOTest
) else if "%choice%"=="2" (
    echo Running ComplaintDAO Test...
    java -cp "src;lib/mysql-connector-java-8.0.28.jar" gaumanagementsystem.test.dao.ComplaintDAOTest
) else if "%choice%"=="3" (
    echo Running ProjectRequestDAO Test...
    java -cp "src;lib/mysql-connector-java-8.0.28.jar" gaumanagementsystem.test.dao.ProjectRequestDAOTest
) else if "%choice%"=="4" (
    echo Running NewsAndNoticeDAO Test...
    java -cp "src;lib/mysql-connector-java-8.0.28.jar" gaumanagementsystem.test.dao.NewsAndNoticeDAOTest
) else if "%choice%"=="5" (
    echo Running ServiceDAO Test...
    java -cp "src;lib/mysql-connector-java-8.0.28.jar" gaumanagementsystem.test.dao.ServiceDAOTest
) else if "%choice%"=="6" (
    echo Running BudgetAllocationDAO Test...
    java -cp "src;lib/mysql-connector-java-8.0.28.jar" gaumanagementsystem.test.dao.BudgetAllocationDAOTest
) else if "%choice%"=="7" (
    echo Running CitizenDAO Test...
    java -cp "src;lib/mysql-connector-java-8.0.28.jar" gaumanagementsystem.test.dao.CitizenDAOTest
) else if "%choice%"=="8" (
    echo Running All DAO Tests...
    java -cp "src;lib/mysql-connector-java-8.0.28.jar" gaumanagementsystem.test.dao.AllDAOTestRunner
) else (
    echo Invalid choice! Please run the script again and choose 1-8.
)

echo.
echo Test execution completed!
pause 