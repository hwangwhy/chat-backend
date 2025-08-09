# Render Deployment Guide

## Issue Resolution: JAVA_HOME Error

The "JAVA_HOME is not defined correctly" error occurs because Render is auto-detecting the wrong runtime due to multiple project types in the repository.

## Solution Implemented

1. **Explicit Runtime Configuration**: Added `runtime: java` in render.yaml
2. **JAVA_HOME Environment Variable**: Explicitly set JAVA_HOME to Render's Java installation path
3. **Build Script Enhancement**: Added Java environment setup in build commands
4. **Buildpacks Configuration**: Created buildpacks.yml to force Java buildpack usage

## Deployment Options

### Option 1: Use Root render.yaml (Recommended)
- The render.yaml in the root directory specifies `rootDir: chat-backend`
- This tells Render to focus on the chat-backend subdirectory

### Option 2: Deploy from chat-backend directory
- Connect Render directly to the chat-backend folder
- Use the render.yaml inside chat-backend directory

## Manual Deployment Steps

If automatic deployment still fails:

1. **Set Service Type**: Ensure service is set to "Web Service"
2. **Set Runtime**: Explicitly set runtime to "Java" in Render dashboard
3. **Set Build Command**:
   ```bash
   cd chat-backend && chmod +x ./mvnw && ./mvnw clean package -DskipTests -B
   ```
4. **Set Start Command**:
   ```bash
   cd chat-backend && java -Dserver.port=$PORT -jar target/chat-backend-0.0.1-SNAPSHOT.jar
   ```
5. **Environment Variables**:
   - `JAVA_HOME=/opt/render/project/.java`
   - `JAVA_TOOL_OPTIONS=-XX:MaxRAMPercentage=90.0`

## Troubleshooting

If you still get the JAVA_HOME error:
1. Check Render logs for runtime detection
2. Ensure Java 17 is being used
3. Verify build commands are running in correct directory
4. Consider creating a separate repository for just the chat-backend
