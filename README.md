# WeatherAppGlobalKinetic
This is my submission for the Global Kenetic Tech Task that is part of the interview process.

The task was to create an Android app that displays the weather for a users current location using the OpenWeatherMap Api.

I should mention for transparency that this was my second consecutive tech task for creating a weather app using the OpenWeatherMap Api. I have therefore reused a lot of my old code but I believe I have made significant improvements to the code. As of writing this, I haven't received feedback for my previous task yet, so these improvements are my own.

## Improvisations
According to the requirements I received, one of the criteria used to assess the assignment is improvisation. I took this to mean that there is flexibility in the way I implement the app, and I'm allowed to add features that were not specifically requested. I have therefore improvised in the following ways:
- Added the ability to view a 5 day forecast
- Implemented PlacesAPI so that a user can set their location manually
- Used the city name in my api request instead of coordinates. I wasn't getting accurate information back when using coordinates

## API Requests

api.openweathermap.org/data/2.5/weather
api.openweathermap.org/data/2.5/forecast
Requests are made using city name as a parameter. I decided to use the city name in the request because when sending coordinates, the values received back are not accurate. The city name is retrieved in two ways, either from Geocoding using coordinates, or from the Places API, depending on whether the location is retrieved from GPS or PlacesAPI.
![This is an image](https://raw.githubusercontent.com/citispy/WeatherAppGlobalKenetic/master/WeatherApp-Flow-Diagram.png)
