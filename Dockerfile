FROM tomcat:8.5-jdk17-openjdk-slim
ADD target/femicidios.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]