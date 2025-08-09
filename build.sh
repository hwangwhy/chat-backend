#!/bin/bash
set -e

echo "Making Maven wrapper executable..."
chmod +x ./mvnw

echo "Building application..."
./mvnw clean package -DskipTests

echo "Build completed successfully!"
