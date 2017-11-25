FROM node:8.9.1

COPY package.json package-lock.json ./

RUN npm install --production

COPY . .

CMD node index.js