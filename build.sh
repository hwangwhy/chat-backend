#!/bin/bash
set -e

echo "=== Java Build Script ==="
echo "Setting up Java environment..."

# Set JAVA_HOME if not set
if [ -z "$JAVA_HOME" ]; then
    export JAVA_HOME=/opt/render/project/.java
    echo "JAVA_HOME set to: $JAVA_HOME"
fi

# Add Java to PATH
export PATH=$JAVA_HOME/bin:$PATH

# Verify Java installation
echo "Checking Java version..."
java -version
which java

echo "Making Maven wrapper executable..."
chmod +x ./mvnw

echo "Building application with Maven..."
./mvnw clean package -DskipTests -B

echo "Build completed successfully!"
echo "JAR file created at: target/chat-backend-0.0.1-SNAPSHOT.jar"
