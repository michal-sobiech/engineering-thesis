FROM openjdk:23-slim

RUN apt-get update && apt-get install -y maven curl

COPY ./ /backend
WORKDIR /backend
RUN [ "mvn", "dependency:resolve" ]

ENTRYPOINT [ "mvn", "spring-boot:run" ]
