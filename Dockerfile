FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /src

COPY pom.xml .
COPY src/main/resources/application.yml src/main/resources/application.yml
COPY src/main/resources/application-docker.yml src/main/resources/application-docker.yml

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine

RUN addgroup -g 1001 -S go-con-coche && \
    adduser -S -D -H -u 1001 -h /app -s /sbin/nologin -G go-con-coche go-con-coche

WORKDIR /app

RUN apk add --no-cache curl

COPY --from=build /src/target/*.jar app.jar

RUN chown go-con-coche:go-con-coche app.jar

USER go-con-coche

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dspring.profiles.active=docker", \
    "-Xmx512m", \
    "-Xms256m", \
    "-jar", \
    "app.jar"]