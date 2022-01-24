FROM adoptopenjdk:11-jre-hotspot
COPY rest/build/libs/*.jar  /opt/movie-service.jar
ENTRYPOINT ["java", "-jar", "/opt/movie-service.jar"]