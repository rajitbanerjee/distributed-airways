FROM openjdk:8-jre-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY target/american-airlines*.jar /american-airlines.jar
CMD sleep 5 && /usr/bin/java -jar /american-airlines.jar
