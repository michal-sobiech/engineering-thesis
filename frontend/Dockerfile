FROM node:22.12.0

COPY ./ /frontend
WORKDIR /frontend
RUN npm i

ENTRYPOINT [ "npm", "start" ]
