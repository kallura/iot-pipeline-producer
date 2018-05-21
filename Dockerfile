FROM openjdk:8-jre-alpine
COPY ./target/iot-pipeline-producer.jar /usr/src/iot/producer/
WORKDIR /usr/src/iot/producer
EXPOSE 8080
CMD ["java", "-jar", "iot-pipeline-producer.jar"]