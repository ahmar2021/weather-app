# Getting Started
## Introduction
A weather app for getting the hottest day for a location over the next 8 days

## Building the app
#### Building the artifact
```
gradle clean build
```
#### Building Docker container
```
docker build -t weather-app:1.0 .
```

## Running the app
#### Running the jar
```
java -jar build/libs/weather-app.jar
```
#### Running the Docker container
```
docker run -p 8080:8080 weather-app:1.0
```

## Running the app
#### Request
Requests can be submitted on the `/forecasts` endpoint with a `longitude` and `latitude` for the location as query parameters.
```
GET http://localhost:8080/forecasts?longitude=12.12&latitude=-9.1
```

#### Response
The response is a JSON structure containing the following fields:
- `dt`: The hottest day timestamp
- `date`: The hottest day in human readable format
- `temperature`: The hottest temperature in the next 7 days
- `humidity`: The humidity value for the hottest day
```
{
    "dt": 1614855600,
    "date": "2021-03-04T11:00:00.000+00:00",
    "temperature": 300.52,
    "humidity": 70
}
```

## Notes
The app is designed for Java 11 but should build and run fine for Java 12/13.