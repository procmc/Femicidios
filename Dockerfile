FROM tomcat:latest
ADD target/femicidios.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]