FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy Maven wrapper and pom
COPY mvnw* ./
COPY .mvn .mvn/
COPY pom.xml ./

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw -B clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/chat-backend-0.0.1-SNAPSHOT.jar"]

