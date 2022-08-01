FROM openjdk:11-jre-slim

ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME

COPY build/libs/server-0.0.1-SNAPSHOT.jar application.jar

EXPOSE 8443
EXPOSE 8080

CMD ["java", "-jar", "application.jar"]
