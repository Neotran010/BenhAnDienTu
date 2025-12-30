# Creating BenhAnDienTu.exe for Windows

This guide explains how to convert the BenhAnDienTu.jar file into a Windows executable (BenhAnDienTu.exe).

## Prerequisites

1. Java 21 or higher installed on Windows
2. BenhAnDienTu.jar file (built using build.bat)

## Method 1: Using jpackage (Recommended for Java 21+)

jpackage is included with Java 21 and creates a native Windows installer/executable.

### Steps:

1. Open Command Prompt in the project directory

2. Run the following command:
```cmd
jpackage --input . ^
  --name BenhAnDienTu ^
  --main-jar BenhAnDienTu.jar ^
  --main-class com.benhan.BenhAnDienTu ^
  --type exe ^
  --app-version 1.0 ^
  --description "Electronic Medical Records Application" ^
  --vendor "BenhAnDienTu" ^
  --icon resources/icon.ico ^
  --win-console ^
  --java-options "-Xmx512m"
```

3. The executable will be created in the current directory

Note: Remove `--win-console` if you don't want a console window to appear.

## Method 2: Using Launch4j

Launch4j is a popular tool for wrapping JAR files into Windows executables.

### Steps:

1. Download Launch4j from: http://launch4j.sourceforge.net/

2. Install and open Launch4j

3. Configure the settings:
   - **Output file**: `BenhAnDienTu.exe`
   - **Jar**: `BenhAnDienTu.jar`
   - **Icon**: (optional) Select an .ico file
   - **JRE Tab**:
     - Min JRE version: `21` or `1.21`
     - Initial heap size: `256` MB
     - Max heap size: `512` MB
   - **Main class**: `com.benhan.BenhAnDienTu`

4. Click the gear icon (Build wrapper) to create the executable

5. The BenhAnDienTu.exe file will be created

### Launch4j Configuration XML Example:

Save this as `launch4j-config.xml` and use it with Launch4j:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<launch4jConfig>
  <dontWrapJar>false</dontWrapJar>
  <headerType>gui</headerType>
  <jar>BenhAnDienTu.jar</jar>
  <outfile>BenhAnDienTu.exe</outfile>
  <errTitle>BenhAnDienTu</errTitle>
  <cmdLine></cmdLine>
  <chdir>.</chdir>
  <priority>normal</priority>
  <downloadUrl>http://java.com/download</downloadUrl>
  <supportUrl></supportUrl>
  <stayAlive>false</stayAlive>
  <restartOnCrash>false</restartOnCrash>
  <manifest></manifest>
  <icon></icon>
  <jre>
    <path></path>
    <bundledJre64Bit>false</bundledJre64Bit>
    <bundledJreAsFallback>false</bundledJreAsFallback>
    <minVersion>21</minVersion>
    <maxVersion></maxVersion>
    <jdkPreference>preferJre</jdkPreference>
    <runtimeBits>64/32</runtimeBits>
    <initialHeapSize>256</initialHeapSize>
    <maxHeapSize>512</maxHeapSize>
  </jre>
</launch4jConfig>
```

Then run:
```cmd
launch4jc.exe launch4j-config.xml
```

## Method 3: Using JSmooth

JSmooth is another wrapper tool for creating Windows executables.

1. Download JSmooth from: http://jsmooth.sourceforge.net/
2. Follow similar configuration steps as Launch4j

## Distribution

After creating the .exe file, you can distribute it along with:
- BenhAnDienTu.exe (the executable)
- BenhAnDienTu.jar (required by the exe)
- lib/ folder with all dependencies (if not embedded)

Or use jpackage to create a complete installer with bundled JRE.

## Testing the Executable

1. Double-click BenhAnDienTu.exe
2. The application should start and show the search frame
3. If it doesn't start:
   - Make sure Java 21+ is installed
   - Check if BenhAnDienTu.jar is in the same directory
   - Try running from command prompt to see error messages:
     ```cmd
     BenhAnDienTu.exe
     ```

## Troubleshooting

### "Java not found" error
- Install Java 21 or higher
- Set JAVA_HOME environment variable
- Add Java bin directory to PATH

### Application doesn't start
- Try running the JAR directly first: `java -jar BenhAnDienTu.jar`
- Check the console output for errors
- Verify all dependencies are included

### Database access errors
- Make sure the application has write permissions in its directory
- The benhan.db file will be created automatically on first run
