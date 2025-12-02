## Build stage: compile and package the Spring Boot app with Maven
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

# Copy Maven descriptor and source code
COPY pom.xml .
COPY src ./src

# Build the jar (skip tests to speed up docker builds in dev)
RUN mvn -q -B package -DskipTests

## Run stage: use a slim JRE image to run the app
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port that Spring Boot uses
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
