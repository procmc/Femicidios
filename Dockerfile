FROM tomcat:9.0.74-jdk17-temurin-focal
LABEL maintainer="pro.cmc@gmail.com"

ADD target/femicidios.war /usr/local/tomcat/webapps/


#copy the user configuration file into the container.
#COPY tomcat-users.xml /usr/local/tomcat/conf/

CMD ["catalina.sh", "run"]

EXPOSE 8080