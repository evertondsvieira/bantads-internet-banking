FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/account-query-0.0.1-SNAPSHOT.jar account-query.jar

EXPOSE 3011

ENTRYPOINT ["java", "-jar", "account-query.jar"]
