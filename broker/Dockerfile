FROM openjdk:8-jre-alpine
COPY target/broker*.jar /broker.jar
CMD sleep 10 && /usr/bin/java -jar /broker.jar
