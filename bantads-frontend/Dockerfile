FROM node
RUN mkdir -p /app
WORKDIR /app
COPY package.json /app/
RUN ["npm", "install"]
COPY . /app
EXPOSE 4200/tcp
CMD [ "sh", "-c", "npm start -- --host 0.0.0.0"]