FROM tomcat:9.0-jre8-alpine

COPY target/wizard*.war $CATALINA_HOME/webapps/femicidios.war

HEALTHCHECK --interval=1m --timeout=3s CMD wget --quiet --tries=1 --spider http://localhost:8080/femicidios/ || exit 1