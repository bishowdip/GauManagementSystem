@echo off
echo ====================================================
echo   Project Requests Table Selection Fix Test
echo ====================================================
echo.
echo This test will demonstrate the fix for the white overlay issue
echo when clicking on table rows in the Project Requests module.
echo.
echo BEFORE FIX: Clicking rows showed white background that hid the data
echo AFTER FIX:  Clicking rows shows light blue background with visible data
echo.
pause

echo Compiling test class...
javac -cp "src" src/gaumanagementsystem/view/ProjectRequestsSimple.java

if %ERRORLEVEL% EQU 0 (
    echo ✓ Compilation successful!
    echo.
    echo Starting table selection test...
    echo.
    echo TEST INSTRUCTIONS:
    echo 1. Click on different rows in the table
    echo 2. Selected rows should show LIGHT BLUE background
    echo 3. Text should remain BLACK and VISIBLE
    echo 4. No white overlay should hide the row data
    echo.
    java -cp "src" gaumanagementsystem.view.ProjectRequestsSimple
) else (
    echo ✗ Compilation failed!
)

echo.
echo Test completed.
pause 