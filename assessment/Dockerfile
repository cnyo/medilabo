# -------------------------
# 1. Build Stage
# -------------------------
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Pre-download dependencies
COPY pom.xml mvnw ./
COPY mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:go-offline -B

# Copy source code and build the application
COPY src ./src
RUN ./mvnw package -DskipTests


# -------------------------
# 2. Run Stage
# -------------------------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Use --no-install-recommends to reduce image size
RUN apt-get update && apt-get install -y --no-install-recommends dumb-init \
    # Clean up apt cache to reduce image size
    && rm -rf /var/lib/apt/lists/*

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Use dumb-init to handle PID 1 issues
ENTRYPOINT ["/usr/bin/dumb-init", "--"]
CMD ["java", "-jar", "app.jar"]
