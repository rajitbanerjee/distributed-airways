# build environment
FROM node:13.12.0-alpine as build
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
# Build args
ARG BROKER_HOST
ARG BROKER_PORT
ARG ENVIRONMENT
# Environment variables
ENV REACT_APP_BROKER_HOST $BROKER_HOST
ENV REACT_APP_BROKER_PORT $BROKER_PORT
ENV REACT_APP_ENVIRONMENT $ENVIRONMENT

COPY package*.json ./
RUN npm ci --silent
RUN npm install react-scripts@3.4.1 -g --silent
COPY . ./
RUN npm run build

# production environment
FROM nginx:stable-alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
