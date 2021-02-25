FROM adoptopenjdk/openjdk11:ubi
MAINTAINER Ahmar Ghaffar <ahmarghaffar@yahoo.com>
COPY ./build /inshur/
EXPOSE 8080
CMD ["java", "-jar", "/inshur/libs/weather-app.jar"]