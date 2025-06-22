@echo off
echo ================================================================
echo                Git Cleanup - Ignore Class and MD Files
echo ================================================================
echo.
echo Step 1: Remove all tracked .class files from Git...
git rm --cached -r **/*.class 2>nul
echo.
echo Step 2: Remove tracked .md files from Git (if you want to ignore them)...
git rm --cached *.md 2>nul
echo.
echo Step 3: Add the updated .gitignore file...
git add .gitignore
echo.
echo Step 4: Check current status...
git status
echo.
echo ================================================================
echo Commands completed! 
echo.
echo Next steps:
echo 1. Review the changes above
echo 2. Commit the changes: git commit -m "Update .gitignore to ignore .class and .md files"
echo 3. Push to remote: git push origin bishowdip
echo ================================================================
pause 