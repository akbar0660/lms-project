# Eureka Server Dockerfile with Corretto 21
FROM amazoncorretto:21-alpine

WORKDIR /app

# Copy the built JAR file
COPY target/eureka-server-*.jar app.jar

# Expose Eureka port
EXPOSE 8761

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]