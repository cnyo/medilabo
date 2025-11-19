# -------------------------
# 1. Stage Node
# -------------------------
FROM node:25-alpine3.21 AS frontend-builder
WORKDIR /app

# Copier uniquement les fichiers du frontend
COPY package.json .
RUN npm install

# A revoir
COPY src/css/tailwind.css ./src/css/tailwind.css

# Générer les assets Tailwind pour la production
RUN npm run build

# -------------------------
# 2. Build maven
# -------------------------
FROM maven:eclipse-temurin AS backend-builder
WORKDIR /app

# Copier le pom.xml puis télécharger les dépendances
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src

# Copier les assets générés par le frontend
COPY --from=frontend-builder /app/src/main/resources/static/css/styles.css ./src/main/resources/static/css/styles.css


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


## Use --no-install-recommends to reduce image size
#RUN apt-get update && apt-get install -y --no-install-recommends dumb-init \
#    # Clean up apt cache to reduce image size
#    && rm -rf /var/lib/apt/lists/*
#
## Copy the built jar from the build stage
#COPY --from=build /app/target/*.jar app.jar
#
## Use dumb-init to handle PID 1 issues
#ENTRYPOINT ["/usr/bin/dumb-init", "--"]
#CMD ["java", "-jar", "app.jar"]
#
## -------------------------
## 2. Build Stage
## -------------------------
#FROM eclipse-temurin:17-jdk AS build
#WORKDIR /app
#
## Pre-download dependencies
#COPY pom.xml mvnw ./
#COPY .mvn .mvn
#COPY mvnw ./
#RUN ./mvnw dependency:go-offline -B
#
## Copy source code and build the application
#COPY src ./src
#RUN ./mvnw package -DskipTests
#
#
## -------------------------
## 2. Run Stage
## -------------------------
#FROM eclipse-temurin:17-jre-alpine
#WORKDIR /app
#
## Copier uniquement le JAR (sans sources, sans dépendances inutiles)
#COPY --from=build /app/target/*.jar app.jar
#
#ENTRYPOINT ["java", "-jar","app.jar"]

# Use --no-install-recommends to reduce image size
#RUN apt-get update && apt-get install -y --no-install-recommends dumb-init \
#    # Clean up apt cache to reduce image size
#    && rm -rf /var/lib/apt/lists/*

# Copy the built jar from the build stage
#COPY --from=build /app/target/*.jar app.jar

# Use dumb-init to handle PID 1 issues
#ENTRYPOINT ["/usr/bin/dumb-init", "--"]
#CMD ["java", "-jar", "app.jar"]
