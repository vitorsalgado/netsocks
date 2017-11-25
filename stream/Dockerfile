FROM openjdk:8-jdk

WORKDIR /stream
COPY . .

RUN ./gradlew build

CMD java -jar ./build/libs/com.netsocks.stream-1.0.0.jar ${INPUT}