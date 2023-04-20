FROM openjdk:17
EXPOSE 8080
ADD target/femicidios.jar femicidios.jar
ENTRYPOINT ["java", "-jar", "/femicidios.jar"]