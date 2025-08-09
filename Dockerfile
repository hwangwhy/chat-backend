FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy Maven wrapper and pom
COPY mvnw* ./
COPY .mvn .mvn/
COPY pom.xml ./

# Copy source code
COPY src ./src

# Make Maven wrapper executable and build the application
RUN chmod +x ./mvnw && ./mvnw -B clean package -DskipTests

# Create uploads directory
RUN mkdir -p /tmp/uploads

# Expose port (Render will provide PORT via environment variable)
EXPOSE $PORT

# Run the application with dynamic port
CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar target/chat-backend-0.0.1-SNAPSHOT.jar"]

