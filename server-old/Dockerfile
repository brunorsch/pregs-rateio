FROM amazoncorretto:21 as build

WORKDIR /app

COPY . .

RUN ./gradlew clean build --no-daemon

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=build /app/build/libs/pregsrateio-0.1-all-optimized.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
