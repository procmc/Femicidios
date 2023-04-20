FROM tomcat:8.5.47-jdk8-openjdk

COPY ./target/femicidios.war /usr/local/tomcat/webapps

CMD ["/usr/local/tomcat/bin/catalina.sh","run"]