@echo off
echo ================================================================
echo         Gau Management System - JAR Files Download Guide
echo ================================================================
echo.
echo You need to download the following JAR files for email functionality:
echo.
echo 1. JavaMail API (javax.mail-1.6.2.jar)
echo    Download from: https://repo1.maven.org/maven2/com/sun/mail/javax.mail/1.6.2/javax.mail-1.6.2.jar
echo    Size: ~600KB
echo.
echo 2. Java Activation Framework (activation-1.1.1.jar)
echo    Download from: https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar
echo    Size: ~60KB
echo.
echo 3. NetBeans AbsoluteLayout (AbsoluteLayout.jar)
echo    This is needed for the UI components to work properly
echo    Download from NetBeans IDE installation or Maven repository
echo.
echo ================================================================
echo                        INSTRUCTIONS
echo ================================================================
echo.
echo Step 1: Create lib directory (if not exists)
if not exist "lib" mkdir lib
echo ✓ lib directory created/verified
echo.
echo Step 2: Download the JAR files
echo   - Open the URLs above in your browser
echo   - Download each JAR file
echo   - Place them in the 'lib' directory of this project
echo.
echo Step 3: Verify downloads
echo   After downloading, your lib directory should contain:
echo   ├── javax.mail-1.6.2.jar
echo   ├── activation-1.1.1.jar
echo   └── AbsoluteLayout.jar (or similar NetBeans layout JAR)
echo.
echo Step 4: Test email functionality
echo   Run: run-forgot-password.bat
echo.
echo ================================================================
echo                     ALTERNATIVE SOLUTION
echo ================================================================
echo.
echo If you want to test immediately without downloading JARs:
echo   Run: run-forgot-password-demo.bat
echo   This uses demo mode with verification code: 123456
echo.
echo ================================================================
echo                    EMAIL CONFIGURATION
echo ================================================================
echo.
echo Your email is configured as:
echo   Email: bishodip123@gmail.com
echo   App Password: mzhg bqng svnf hftk
echo.
echo Make sure:
echo   1. 2-Factor Authentication is enabled on your Gmail account
echo   2. App Password is correctly generated for "Mail" application
echo   3. The app password above is correct and active
echo.
echo ================================================================

pause 