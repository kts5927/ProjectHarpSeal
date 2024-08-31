FROM openjdk:21-jdk-slim

COPY /var/jenkins_home/workspace/HarpSeal_Hook/build/libs/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE 8080
