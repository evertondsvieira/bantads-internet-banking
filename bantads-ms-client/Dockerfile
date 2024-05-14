FROM openjdk:17-jdk-alpine
RUN mkdir /ms-client
WORKDIR /ms-client
COPY target/*.jar /ms-client/ms-client.jar
CMD ["java", "-jar", "/ms-client/ms-client.jar"]