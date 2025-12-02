# -------------------------
# 1. Build maven
# -------------------------
FROM maven:eclipse-temurin AS backend-builder
WORKDIR /app

# Copier le pom.xml puis télécharger les dépendances
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src

# Build du jar Spring Boot
RUN mvn -B clean package -DskipTests

# -------------------------
# 2. Stage final
# -------------------------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copier uniquement le JAR (sans sources, sans dépendances inutiles)
COPY --from=backend-builder /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
