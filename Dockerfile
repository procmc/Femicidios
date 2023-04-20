FROM openjdk:17
EXPOSE 8080
ADD target/femicidios.war femicidios.war
ENTRYPOINT ["java", "-war", "/femicidios.war"]