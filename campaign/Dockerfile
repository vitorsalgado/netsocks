# build stage
# -----------------------------------------------------
FROM openjdk:8-jdk

WORKDIR /campaign
COPY . .
RUN ./gradlew build

# run stage
# -----------------------------------------------------
FROM openjdk:8-alpine
WORKDIR /campaign

COPY --from=0 /campaign/build/libs/campaign-1.0-SNAPSHOT.jar .
EXPOSE 8080

CMD java -jar campaign-1.0-SNAPSHOT.jar