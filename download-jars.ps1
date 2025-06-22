# PowerShell script to download required JAR files for Gau Management System
Write-Host "================================================================" -ForegroundColor Green
Write-Host "    Downloading JAR Files for Email Functionality" -ForegroundColor Green
Write-Host "================================================================" -ForegroundColor Green
Write-Host ""

# Create lib directory if it doesn't exist
if (!(Test-Path -Path "lib")) {
    New-Item -ItemType Directory -Path "lib"
    Write-Host "✓ Created lib directory" -ForegroundColor Green
} else {
    Write-Host "✓ lib directory already exists" -ForegroundColor Green
}

Write-Host ""

# Download javax.mail JAR
$javaMailUrl = "https://repo1.maven.org/maven2/com/sun/mail/javax.mail/1.6.2/javax.mail-1.6.2.jar"
$javaMailPath = "lib\javax.mail-1.6.2.jar"

Write-Host "Downloading javax.mail-1.6.2.jar..." -ForegroundColor Yellow
try {
    Invoke-WebRequest -Uri $javaMailUrl -OutFile $javaMailPath
    Write-Host "✓ Downloaded javax.mail-1.6.2.jar ($(((Get-Item $javaMailPath).length/1KB).ToString('F0')) KB)" -ForegroundColor Green
} catch {
    Write-Host "❌ Failed to download javax.mail-1.6.2.jar" -ForegroundColor Red
    Write-Host "Please download manually from: $javaMailUrl" -ForegroundColor Yellow
}

# Download activation JAR
$activationUrl = "https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar"
$activationPath = "lib\activation-1.1.1.jar"

Write-Host "Downloading activation-1.1.1.jar..." -ForegroundColor Yellow
try {
    Invoke-WebRequest -Uri $activationUrl -OutFile $activationPath
    Write-Host "✓ Downloaded activation-1.1.1.jar ($(((Get-Item $activationPath).length/1KB).ToString('F0')) KB)" -ForegroundColor Green
} catch {
    Write-Host "❌ Failed to download activation-1.1.1.jar" -ForegroundColor Red
    Write-Host "Please download manually from: $activationUrl" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "================================================================" -ForegroundColor Green
Write-Host "                    DOWNLOAD SUMMARY" -ForegroundColor Green
Write-Host "================================================================" -ForegroundColor Green

# Check if files were downloaded successfully
$javaMailExists = Test-Path $javaMailPath
$activationExists = Test-Path $activationPath

if ($javaMailExists) {
    Write-Host "✓ javax.mail-1.6.2.jar - Ready" -ForegroundColor Green
} else {
    Write-Host "❌ javax.mail-1.6.2.jar - Missing" -ForegroundColor Red
}

if ($activationExists) {
    Write-Host "✓ activation-1.1.1.jar - Ready" -ForegroundColor Green
} else {
    Write-Host "❌ activation-1.1.1.jar - Missing" -ForegroundColor Red
}

Write-Host ""

if ($javaMailExists -and $activationExists) {
    Write-Host "🎉 All JAR files downloaded successfully!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Next steps:" -ForegroundColor Cyan
    Write-Host "1. Run: .\test-email-setup.bat" -ForegroundColor White
    Write-Host "2. Test real email functionality with your Gmail account" -ForegroundColor White
    Write-Host ""
    Write-Host "Your email configuration:" -ForegroundColor Cyan
    Write-Host "📧 Email: bishodip123@gmail.com" -ForegroundColor White
    Write-Host "🔑 App Password: mzhg bqng svnf hftk" -ForegroundColor White
} else {
    Write-Host "⚠️  Some files are missing. Please download them manually:" -ForegroundColor Yellow
    Write-Host ""
    if (!$javaMailExists) {
        Write-Host "Download: $javaMailUrl" -ForegroundColor White
        Write-Host "Save as: $javaMailPath" -ForegroundColor White
        Write-Host ""
    }
    if (!$activationExists) {
        Write-Host "Download: $activationUrl" -ForegroundColor White
        Write-Host "Save as: $activationPath" -ForegroundColor White
    }
}

Write-Host ""
Write-Host "================================================================" -ForegroundColor Green
Read-Host "Press Enter to continue" 