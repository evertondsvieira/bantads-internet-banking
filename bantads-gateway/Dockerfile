#Build stage
FROM node:16-alpine AS build

WORKDIR /app

COPY package*.json .

RUN npm install

COPY . .

EXPOSE 3000/tcp

CMD [  "node", "src/server.js"]