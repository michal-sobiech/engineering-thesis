FROM openjdk:23-slim

RUN apt-get update && apt-get install -y maven curl

COPY ./pom.xml /backend/pom.xml
WORKDIR /backend
RUN [ "mvn", "dependency:go-offline", "-B" ]

COPY ./ /backend

CMD [ "mvn", "spring-boot:run" ]
