FROM eclipse-temurin:21-jdk AS buildstage 
RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src

RUN mvn clean package

FROM eclipse-temurin:21-jdk 

COPY --from=buildstage /app/target/frontend-0.0.2-SNAPSHOT.jar /app/frontend.jar
EXPOSE 8080

ENTRYPOINT [ "java", "-jar","/app/frontend.jar" ]



