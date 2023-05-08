FROM maven:3.8.6-openjdk-18 AS build
COPY src /home/app/src
COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

FROM openjdk:17-jdk-alpine
COPY --from=build /home/app/target/citybike-0.0.1-SNAPSHOT.jar /usr/local/lib/citybikeapp.jar
EXPOSE 3000
ENTRYPOINT ["java","-jar","/usr/local/lib/citybikeapp.jar"]
