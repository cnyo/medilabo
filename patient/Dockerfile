# -------------------------
# Stage 1: BUILD
# -------------------------
FROM maven:eclipse-temurin AS builder
WORKDIR /build

# Copier le pom.xml puis télécharger les dépendances
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copier le code source
COPY src ./src

# Construire l'application
RUN mvn -B clean package -DskipTests

# -------------------------
# Stage 2: RUNTIME
# -------------------------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copier uniquement le JAR du stage de build
ARG JAR_FILE=/build/target/*.jar
COPY --from=builder ${JAR_FILE} /app/app.jar

# Créer un utilisateur non-root
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

USER appuser

EXPOSE 9002

# Health check (à décommenter si Actuator est disponible)
# HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
#   CMD curl -f http://localhost:9001/actuator/health || exit 1

ENTRYPOINT ["java","-jar","app.jar"]
