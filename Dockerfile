
FROM maven:3.5-jdk-8-slim AS build
WORKDIR /home/app
COPY src /home/app/src
COPY pom.xml /home/app/
COPY * /home/app/
COPY sonar-project.properties /home/app/
#RUN mvn -Pprod,swagger verify
#RUN mvn clean package -Pprod jib:exportDockerContext
#RUN mvn clean install 

USER root
#RUN mvn compile com.google.cloud.tools:jib-maven-plugin
RUN mvn -Pprod,swagger verify

FROM openjdk:8-alpine
COPY --from=0 /home/app/target/pmo-1-0.0.1-SNAPSHOT.jar /tmp/pmq-service.jar

EXPOSE 80/tcp
ENTRYPOINT ["java","-jar","/tmp/pmq-service.jar"]