FROM openjdk:23-slim

RUN apt-get update && apt-get install -y maven curl

COPY ./ /backend
WORKDIR /backend
RUN [ "mvn", "install", "-DskipCompile", "-DskipTests" ]

CMD [ "mvn", "spring-boot:run" ]
