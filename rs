docker run --name alleinservice -it --rm \
  -e JPDA_ADDRESS=8000 \
  -e JPDA_TRANSPORT=dt_socket \
  -e JPDA_SUSPEND=n \
  -p 20880:20880  -p 20881:20881  \
  -p 8000:8000 \
  alleinservice \
  /usr/local/tomcat/bin/catalina.sh jpda run
