FROM tomcat:8.5-jdk17-openjdk-slim
RUN rm -rf /usr/local/tomcat/webapps/*
COPY ./target/femicidios.war /usr/local/tomcat/webapps/femicidios.war
CMD ["catalina.sh","run"]