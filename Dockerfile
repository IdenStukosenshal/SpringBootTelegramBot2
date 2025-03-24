FROM amazoncorretto:21-alpine
WORKDIR /app
COPY target/java_tg_bot_2-0.0.1-SNAPSHOT.jar /app/jbot.jar
ENTRYPOINT ["java", "-jar", "jbot.jar"]
