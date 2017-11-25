FROM openjdk:8-jdk

WORKDIR /simulations
COPY . .

RUN ./gradlew build

CMD ./gradlew runCampaignSimulation