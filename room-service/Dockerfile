# Build stage
FROM gradle:8-jdk22-alpine AS builder

# Copy source files into the container
COPY --chown=gradle . /home/gradle/src

# Set working directory
WORKDIR /home/gradle/src

# Build the project without running tests
RUN gradle build -x test

# Package stage
FROM openjdk:22-jdk

# Create an application directory
RUN mkdir /app

# Copy the built JAR from the builder stage
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/room-service.jar

# Set environment variables for PostgreSQL
ENV POSTGRES_DB=room-service \
    POSTGRES_USER=admin \
    POSTGRES_PASSWORD=password \
    POSTGRES_PORT=5434

# Expose the application port
EXPOSE 8093

# Run the application
ENTRYPOINT ["java", "-jar", "/app/room-service.jar"]
