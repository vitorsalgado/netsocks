# build stage
# -----------------------------------------------------
FROM openjdk:8-jdk

WORKDIR /fan
COPY . .
RUN ./gradlew build

# run stage
# -----------------------------------------------------
FROM openjdk:8-alpine
WORKDIR /fan

COPY --from=0 /fan/build/libs/associatefan-1.0-SNAPSHOT.jar .
EXPOSE 8081

CMD java -jar associatefan-1.0-SNAPSHOT.jar