# Required JAR Files for Email Functionality

## Overview
To enable email functionality for the forgot password feature, you need to download and add the JavaMail API JAR files to your project.

## Required JAR Files

### 1. JavaMail API
- **File**: `javax.mail-1.6.2.jar` (or latest version)
- **Download URL**: https://mvnrepository.com/artifact/com.sun.mail/javax.mail
- **Direct Download**: https://repo1.maven.org/maven2/com/sun/mail/javax.mail/1.6.2/javax.mail-1.6.2.jar

### 2. Java Activation Framework (JAF)
- **File**: `activation-1.1.1.jar`
- **Download URL**: https://mvnrepository.com/artifact/javax.activation/activation
- **Direct Download**: https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar

## Installation Instructions

### Step 1: Create lib Directory
```bash
mkdir lib
```

### Step 2: Download JAR Files
Download the following JAR files and place them in the `lib` directory:

1. **javax.mail-1.6.2.jar**
   - Size: ~600KB
   - Contains: JavaMail API classes

2. **activation-1.1.1.jar**
   - Size: ~60KB
   - Contains: Java Activation Framework

### Step 3: Verify Installation
After downloading, your `lib` directory should contain:
```
lib/
├── javax.mail-1.6.2.jar
└── activation-1.1.1.jar
```

### Step 4: Update Classpath
Make sure your compilation and runtime commands include the JAR files:

**Compilation:**
```bash
javac -cp "src;lib/*" src/gaumanagementsystem/util/EmailUtil.java
```

**Runtime:**
```bash
java -cp "src;lib/*" gaumanagementsystem.view.ForgotPasswordView
```

## Gmail Configuration

### Step 1: Enable 2-Factor Authentication
1. Go to your Google Account settings
2. Enable 2-Factor Authentication

### Step 2: Generate App Password
1. Go to Google Account → Security → App passwords
2. Select "Mail" and "Other (Custom name)"
3. Enter "Gau Management System"
4. Copy the generated 16-character password
5. This password is already configured in the code: `mzhg bqng svnf hftk`

### Step 3: Update Email Address (Already Done)
The email address has been set to: `gaumanagement2024@gmail.com`

## Testing Without Email JAR Files

The system includes a **demo mode** that works without email JAR files:

### Demo Mode Features:
- **Verification Code**: Always use `123456`
- **Email Sending**: Simulated (shows success message)
- **Code Verification**: Accepts the demo code `123456`
- **Full Functionality**: All other features work normally

### Demo Mode Usage:
1. Enter any valid email address from your database
2. Click "Send Code" (will show success message)
3. Enter `123456` as verification code
4. Enter new password (must meet strength requirements)
5. Password will be updated in database

## Troubleshooting

### Common Issues:

#### 1. ClassNotFoundException: javax.mail
**Cause**: JavaMail JAR files not in classpath
**Solution**: Download and add the required JAR files to `lib` directory

#### 2. Authentication Failed
**Cause**: Incorrect Gmail app password or 2FA not enabled
**Solution**: 
- Enable 2-Factor Authentication
- Generate new app password
- Update `EMAIL_PASSWORD` in `EmailUtil.java`

#### 3. Connection Timeout
**Cause**: Firewall or network restrictions
**Solution**: Check firewall settings for SMTP (port 587)

#### 4. Demo Mode Not Working
**Cause**: Email configuration check failing
**Solution**: The system automatically falls back to demo mode when JAR files are missing

## Production Deployment

### For Production Use:
1. **Download the required JAR files**
2. **Configure your own Gmail account**
3. **Update email credentials in `EmailUtil.java`**
4. **Test email functionality thoroughly**

### Security Considerations:
- Never commit email passwords to version control
- Use environment variables for production credentials
- Consider using OAuth2 for enhanced security
- Implement rate limiting for email sending

## Alternative Email Providers

If you prefer not to use Gmail, you can modify `EmailUtil.java` for other providers:

### Outlook/Hotmail:
```java
private static final String SMTP_HOST = "smtp.live.com";
private static final String SMTP_PORT = "587";
```

### Yahoo:
```java
private static final String SMTP_HOST = "smtp.mail.yahoo.com";
private static final String SMTP_PORT = "587";
```

### Custom SMTP:
```java
private static final String SMTP_HOST = "your-smtp-server.com";
private static final String SMTP_PORT = "587"; // or 25, 465
```

## Current Status

✅ **Forgot Password View**: Complete with 3-step process
✅ **Email Utility**: Ready (needs JAR files for full functionality)
✅ **Password Security**: Integrated with secure hashing
✅ **Demo Mode**: Fully functional without email setup
✅ **Database Integration**: Complete password reset functionality
✅ **Login Integration**: Forgot password link added

## Next Steps

1. **Download the JAR files** from the links above
2. **Place them in the `lib` directory`**
3. **Test the complete email functionality**
4. **Deploy with proper email configuration**

The system is ready to use in demo mode immediately, and will have full email functionality once the JAR files are added. 