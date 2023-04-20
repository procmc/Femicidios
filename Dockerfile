FROM tomcat:9.0.8-jre8-alpine
LABEL maintainer="pro.cmc@gmail.com"

ADD target/femicidios.war /usr/local/tomcat/webapps/

EXPOSE 8080
CMD ["catalina.sh", "run"]