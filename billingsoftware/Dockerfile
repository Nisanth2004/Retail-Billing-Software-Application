FROM maven:3.8.5-openjdk-17 AS build
COPY . .

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/billingsoftware-0.0.1-SNAPSHOT.jar  billing.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","billing.jar"]