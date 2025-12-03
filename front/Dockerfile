ARG BUILD_STAGE=prod

# -------------------------
# Stage 1: BUILD CSS
# -------------------------
FROM node:25-alpine3.21 AS frontend-base
WORKDIR /build

# Copier fichiers de configuration
COPY package.json package-lock.json ./
RUN npm ci

# Copier configuration PostCSS
COPY postcss.config.mjs ./
COPY src/css ./src/css
COPY src/main/resources/templates ./src/main/resources/templates

# Générer les CSS optimisés
# RUN npm run build

# -------------------------
# Stage PROD
# -------------------------
FROM frontend-base AS frontend-prod
RUN npm run build

# -------------------------
# Stage DEV
# -------------------------
FROM frontend-base AS frontend-dev
RUN npm run watch

# -------------------------
# Stage FRONT
# -------------------------
ARG BUILD_STAGE=prod
FROM frontend-${BUILD_STAGE} AS frontend

# -------------------------
# Stage 2: BUILD JAVA
# -------------------------
FROM maven:eclipse-temurin AS java-builder
WORKDIR /build

# Copier uniquement les fichiers du frontend
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copier le code source
COPY src ./src

# Copier les CSS générés du stage précédent
COPY --from=frontend /build/src/main/resources/static/css /build/src/main/resources/static/css

# Build Maven
RUN mvn -B clean package -DskipTests

# -------------------------
#  Stage 3: RUNTIME
# -------------------------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copier uniquement le JAR du stage de build
ARG JAR_FILE=/build/target/*.jar
COPY --from=java-builder ${JAR_FILE} /app/app.jar

RUN addgroup --system appgroup && \
    adduser --system --ingroup appgroup appuser

USER appuser

EXPOSE 9003

#HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
#  CMD curl -f http://localhost:9003/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]