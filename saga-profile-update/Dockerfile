FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/saga-profile-update-0.0.1-SNAPSHOT.jar saga-profile-update.jar

EXPOSE 3005

ENTRYPOINT ["java", "-jar", "saga-profile-update.jar"]