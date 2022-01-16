# WeatherAppGlobalKinetic
**_Please note: This app won't compile without a PlacesAPI key. If you want to run the app you can contact me on m.yusuf.isaacs@gmail.com or create your own API key._**

## Introduction
This is my submission for the Global Kinetic Tech Task that is part of the interview process.

The task was to create an Android app that displays the weather for a users current location using the OpenWeatherMap Api.

I should mention for transparency that this was my second consecutive tech task for creating a weather app using the OpenWeatherMap Api. I have therefore reused a lot of my old code but I believe I have made significant improvements to the code. As of writing this, I haven't received feedback for my previous task yet, so these improvements are my own.

## Improvisations
According to the requirements I received, one of the criteria used to assess the assignment is improvisation. I took this to mean that there is flexibility in the way I implement the app, and I'm allowed to add features that were not specifically requested. I have therefore improvised in the following ways:
- Added the ability to view a 5 day forecast
- Implemented PlacesAPI so that a user can set their location manually
- Used the city name in my api request instead of coordinates. I wasn't getting accurate information back when using coordinates

## API Requests
Request are made to the following endpoints:
- api.openweathermap.org/data/2.5/weather
- api.openweathermap.org/data/2.5/forecast

A city name is sent as a parameter in requests and is obtained in two ways, namely, from Geocoding using coordinates, or from the Places API, depending on whether the location is retrieved from GPS or PlacesAPI.

Units are also sent as a parameter in each api request. A unit can either be standard, imperial, or metric. I've set the default unit value to metric.

## App Flow
![This is an image](https://raw.githubusercontent.com/citispy/WeatherAppGlobalKenetic/master/WeatherApp-Flow-Diagram.png)

## Architecture
For the architechture of the app, I used MVVM because it's the recomended approach by Google.
