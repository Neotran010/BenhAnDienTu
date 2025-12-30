@echo off

REM Clean previous builds
if exist bin rmdir /s /q bin
mkdir bin

REM Compile Java files
echo Compiling Java files...
javac -d bin -cp "lib/*" -sourcepath src/main/java ^
    src/main/java/com/benhan/BenhAnDienTu.java ^
    src/main/java/com/benhan/models/*.java ^
    src/main/java/com/benhan/database/*.java ^
    src/main/java/com/benhan/ui/*.java

if %errorlevel% equ 0 (
    echo Compilation successful!
    
    REM Create JAR file
    echo Creating JAR file...
    cd bin
    jar cvfe ..\BenhAnDienTu.jar com.benhan.BenhAnDienTu com/benhan/*.class com/benhan/models/*.class com/benhan/database/*.class com/benhan/ui/*.class
    cd ..
    
    REM Extract SQLite JDBC into bin
    cd bin
    jar xf ..\lib\sqlite-jdbc-3.45.0.0.jar
    if exist META-INF rmdir /s /q META-INF
    cd ..
    
    REM Create final JAR with all dependencies
    cd bin
    jar cvfe ..\BenhAnDienTu.jar com.benhan.BenhAnDienTu .
    cd ..
    
    echo Build complete! JAR file created: BenhAnDienTu.jar
    echo Run with: java -jar BenhAnDienTu.jar
) else (
    echo Compilation failed!
    exit /b 1
)
