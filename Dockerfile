FROM openjdk:8
WORKDIR /src
COPY . . 
RUN chmod +x gradlew
RUN ./gradlew build

FROM openjdk:8
WORKDIR /app
COPY --from=0 /src/taime-webapi/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
