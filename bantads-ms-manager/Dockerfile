FROM openjdk:17-jdk-alpine
RUN mkdir /ms-account
WORKDIR /ms-account
COPY target/*.jar /ms-account/ms-account.jar
CMD ["java", "-jar", "/ms-account/ms-account.jar"]