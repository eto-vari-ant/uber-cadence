FROM openjdk:11
EXPOSE 8080
ADD target/uber-cadence.jar uber-cadence.jar
ENTRYPOINT ["java","-jar","uber-cadence.jar"]