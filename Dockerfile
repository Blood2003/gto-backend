FROM java:8 as build
RUN apt-get update && apt-get install -y maven

WORKDIR /usr/src/app/

COPY pom.xml .
COPY src src
RUN ["mvn", "clean", "install"]

FROM java:8
COPY --from=build /usr/src/app/target .

EXPOSE 11080
CMD ["java", "-jar", "./backend-1.0-SNAPSHOT.jar"]


