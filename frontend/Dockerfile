FROM node:alpine3.10
WORKDIR /app
COPY htdocs/package.json /app
RUN npm install
COPY htdocs/. /app
CMD [ "npm", "run", "dev" ]
