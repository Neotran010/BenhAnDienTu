#!/bin/bash

# Clean previous builds
rm -rf bin
mkdir -p bin

# Compile Java files
echo "Compiling Java files..."
javac -d bin -cp "lib/*" -sourcepath src/main/java \
    src/main/java/com/benhan/BenhAnDienTu.java \
    src/main/java/com/benhan/models/*.java \
    src/main/java/com/benhan/database/*.java \
    src/main/java/com/benhan/ui/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    
    # Create JAR file
    echo "Creating JAR file..."
    cd bin
    jar cvfe ../BenhAnDienTu.jar com.benhan.BenhAnDienTu com/benhan/*.class com/benhan/models/*.class com/benhan/database/*.class com/benhan/ui/*.class
    cd ..
    
    # Extract SQLite JDBC into bin
    cd bin
    jar xf ../lib/sqlite-jdbc-3.45.0.0.jar
    rm -rf META-INF
    cd ..
    
    # Create final JAR with all dependencies
    cd bin
    jar cvfe ../BenhAnDienTu.jar com.benhan.BenhAnDienTu .
    cd ..
    
    echo "Build complete! JAR file created: BenhAnDienTu.jar"
    echo "Run with: java -jar BenhAnDienTu.jar"
else
    echo "Compilation failed!"
    exit 1
fi
