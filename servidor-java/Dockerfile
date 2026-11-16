# Etapa 1: compila e gera o WAR
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B package -DskipTests

# Etapa 2: roda no Jetty
FROM jetty:10-jdk17
COPY --from=build /build/target/app.war /var/lib/jetty/webapps/ROOT.war
EXPOSE 8080
