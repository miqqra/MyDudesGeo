FROM openjdk:21-jdk

COPY .mvn .mvn
COPY mvnw .
COPY lombok.config lombok.config
COPY pom.xml .
COPY src src

RUN ./mvnw clean package -DskipTests=true

COPY target/MyDudesGeo-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "MyDudesGeo-0.0.1-SNAPSHOT.jar"]