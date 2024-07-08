FROM node
RUN mkdir -p /app
WORKDIR /app
COPY package.json /app/
RUN ["npm", "install"]
COPY . /app
EXPOSE 4200/tcp
EXPOSE 3000/tcp
CMD [ "sh", "-c", "npx json-server --watch db.json --host 0.0.0.0 & npm start -- --host 0.0.0.0"]