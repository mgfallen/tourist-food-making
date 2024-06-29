FROM maven:3.9-amazoncorretto-17 AS build

WORKDIR /app-back-build

COPY ./ /app-back-build


RUN mvn clean package

FROM openjdk:17
WORKDIR /app-build

COPY --from=build /app-back-build/target/*.jar app.jar

ENV DB_USERNAME=postgres
ENV DB_PASSWORD=postgres
ENV DB_NAME=postgres
ENV DB_HOST=158.160.2.48
ENV DB_PORT=5432
ENV APP_PORT=8081


ENTRYPOINT ["java","-Dspring.datasource.password=${DB_PASSWORD}", "-Dspring.datasource.username=${DB_USERNAME}", "-Dspring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}", "-Dserver.port=${APP_PORT}", "-jar", "app.jar"]