FROM openjdk:21-jdk-slim

COPY build/libs/ProjectHarpSeal.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE 8080
