FROM openjdk:17-jdk-alpine
RUN mkdir /ms-authentication
WORKDIR /ms-authentication
COPY target/*.jar /ms-authentication/ms-authentication.jar
CMD ["java", "-jar", "/ms-authentication/ms-authentication.jar"]