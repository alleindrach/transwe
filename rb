 mvn clean && mvn package -Dmaven.test.skip=true && docker build -f alleinservice -t alleinservice . && docker build -f alleincontroller -t alleincontroller .
