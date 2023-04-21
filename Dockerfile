FROM eclipse-temurin:17.0.6_10-jre-focal
RUN mkdir /opt/app
COPY /home/runner/work/Femicidios/Femicidios/target/femicidios-0.0.1-SNAPSHOT.jar /opt/app/femicidios.jar
CMD ["java", "-jar", "/opt/app/femicidios.jar"]
