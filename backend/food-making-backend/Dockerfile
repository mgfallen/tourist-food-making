FROM maven:3.9-amazoncorretto-17 AS build

WORKDIR /app-back-build

COPY ./ /app-back-build


RUN mvn clean package

FROM openjdk:17
WORKDIR /app-build

COPY --from=build /app-back-build/target/*.jar app.jar

EXPOSE 8081


ENTRYPOINT ["java", "-jar", "app.jar"]