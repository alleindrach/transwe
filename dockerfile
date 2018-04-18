FROM tomcat
MAINTAINER allein <alleindrach@gmail.com>

COPY service-user/target/service-user-0.0.1 /usr/local/tomcat/webapps/userservice
COPY service-cache/target/service-cache-0.0.1 /usr/local/tomcat/webapps/cacheservice
CMD /usr/local/tomcat/bin/catalina.sh jpda run


