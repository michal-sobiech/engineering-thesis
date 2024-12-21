FROM nginx:latest

RUN apt-get update && apt-get install curl

ENTRYPOINT [ "/docker-entrypoint.sh" ]

CMD [ "nginx", "-g", "daemon off;" ]