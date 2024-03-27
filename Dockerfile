FROM openjdk:17-alpine

COPY target/MyDudesGeo-0.0.1-SNAPSHOT.jar /mydudes.jar

CMD ["java", "-jar", "app.jar"]