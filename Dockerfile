#FROM openjdk:11-jdk-slim
#
#WORKDIR /src
#COPY . /src
#
#RUN apt-get update
#RUN apt-get install -y dos2unix
#RUN dos2unix gradlew
#
#RUN bash gradlew shadowJar
#
#WORKDIR /run
#RUN cp /src/build/libs/*.jar /run/server.jar
#
#EXPOSE 8080
#
#CMD java -jar /run/server.jar

FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/ktor-docker-sample.jar
ENTRYPOINT ["java","-jar","/app/ktor-docker-sample.jar"]