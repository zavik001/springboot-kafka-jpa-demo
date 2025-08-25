FROM gradle:9.0-jdk24 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:24-jre
COPY --from=build /home/gradle/src/build/libs/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]