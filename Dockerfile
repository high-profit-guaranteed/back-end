FROM openjdk:17
WORKDIR /app
RUN mkdir ./ssl
ARG JAR_FILE=./demo/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","./app.jar"]