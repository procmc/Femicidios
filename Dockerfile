FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/*.war femicidios.war

ENTRYPOINT ["java","-war","/app/femicidios.war"]