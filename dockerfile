FROM tomcat
MAINTAINER allein <alleindrach@gmail.com>

COPY service-bundle/target/service-bundle-0.0.1 /usr/local/tomcat/webapps/servicebundle
COPY service-cache/target/service-cache-0.0.1 /usr/local/tomcat/webapps/cacheservice
CMD /usr/local/tomcat/bin/catalina.sh jpda run


