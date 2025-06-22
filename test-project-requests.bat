@echo off
echo Testing Project Requests Module - Table Selection Fix...
echo ====================================================
echo.
echo Testing the fix for white overlay issue when clicking table rows
echo The table selection should now show light blue background instead of white
echo.
echo Press any key to start the Project Requests module...
pause > nul

echo Compiling Project Requests module...
javac -cp "src;lib/mysql-connector-java-8.0.28.jar" src/gaumanagementsystem/view/ProjectRequests.java

if %ERRORLEVEL% EQU 0 (
    echo ✓ Compilation successful!
    echo.
    echo Starting Project Requests module...
    echo Instructions:
    echo 1. Click on any row in the table
    echo 2. The selected row should show light blue background (not white)
    echo 3. Row data should remain visible when selected
    echo.
    java -cp "src;lib/mysql-connector-java-8.0.28.jar" gaumanagementsystem.view.ProjectRequests admin
) else (
    echo ✗ Compilation failed!
    echo Please check the error messages above.
)

echo.
echo Test completed.
pause 