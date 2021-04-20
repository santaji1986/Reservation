# Pro way of writing Dockerfile
FROM maven:3-openjdk-11 as build
RUN mkdir /app
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim
RUN mkdir /project
COPY --from=build /app/target/reservation-0.0.1-SNAPSHOT.jar /project
WORKDIR /project
ENTRYPOINT ["java","-jar","reservation-0.0.1-SNAPSHOT.jar"]
