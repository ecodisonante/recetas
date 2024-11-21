# ==========================
# Fase 1: Build Stage
# ==========================
FROM eclipse-temurin:21-jdk AS buildstage

# Instalar Maven para la compilación
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copiar el archivo de configuración de Maven (pom.xml) primero para aprovechar el caché
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente
COPY src /app/src

# Ejecutar la compilación
RUN mvn clean package -DskipTests

# ==========================
# Fase 2: Runtime Stage
# ==========================
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copiar el archivo JAR generado
COPY --from=buildstage /app/target/frontend-0.0.3-SNAPSHOT.jar /app/frontend.jar

# Exponer el puerto
EXPOSE 8082

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/frontend.jar"]
