FROM eclipse-temurin:17.0.6_10-jre-focal
RUN mkdir /opt/app
COPY target/femicidios.jar /opt/femicidios
CMD ["java", "-jar", "/opt/app/femicidios.jar"]
