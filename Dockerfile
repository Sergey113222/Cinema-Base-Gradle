FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /opt/movie-service.jar
ENTRYPOINT ["java", "-jar", "/opt/movie-service.jar"]