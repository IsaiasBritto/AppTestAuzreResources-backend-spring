# ====== Build ======
FROM maven:3.9.6-eclipse-temurin-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -B -DskipTests package

# ====== Runtime ======
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copie o JAR gerado
COPY --from=build /app/target/*.jar /app/app.jar

# Porta padrão do Spring
EXPOSE 8080

# JVM + Spring Boot otimizações leves
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Dserver.port=8080"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
