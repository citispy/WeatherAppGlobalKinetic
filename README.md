# WeatherAppGlobalKinetic
**_Please note: This app won't compile without a PlacesAPI key. If you want to run the app you can contact me on m.yusuf.isaacs@gmail.com or create your own API key._**

## Introduction
This is my submission for the Global Kinetic Tech Task that is part of the interview process.

The task was to create an Android app that displays the weather for a users current location using the OpenWeatherMap Api.

I should mention for transparency that this was my second consecutive tech task for creating a weather app using the OpenWeatherMap Api. I have therefore reused a lot of my old code but I believe I have made significant improvements to the code. As of writing this, I haven't received feedback for my previous task yet, so these improvements are my own.

## Improvisations
According to the requirements I received, one of the criteria used to assess the assignment is improvisation. I took this to mean that there is flexibility in the way I implement the app, and I'm allowed to add features that were not specifically requested. I will list the improvisations I have made but all except the last were requirements in my previous assignment. 

I have improvised in the following ways:
- Added the ability to view a 5 day forecast
- Implemented PlacesAPI so that a user can set their location manually
- Change the background colour depending on the weather type (cloudy, sunny, or rainy) 
- Used the city name in my API request instead of coordinates. I wasn't getting accurate information back when using coordinates

## API Requests
Request are made to the following endpoints:
- api.openweathermap.org/data/2.5/weather
- api.openweathermap.org/data/2.5/forecast

Requests are made using Retrofit. I created a class called ApiService which contains information about each request. I also created a class called ABaseWebRequestManager which can be extended by child request manager classes. Request manager classes handle every about an API request, including loading states, error messages, and successful responses.

Requests require a city name as a parameter and is obtained in two ways, namely, from Geocoding using coordinates, or from the Places API, depending on whether the location was retrieved from GPS or PlacesAPI.

Units are also sent as a parameter in each api request. A unit can either be standard, imperial, or metric. I've set the default unit value to metric.

## App Flow
![This is an image](https://raw.githubusercontent.com/citispy/WeatherAppGlobalKenetic/master/WeatherApp-Flow-Diagram.png)

## Architecture
Firstly, the app is packaged-by-feature where all classes related to one feature or screen is included in the same package.

For the architechture, I used MVVM because it's the recomended approach by Google.

Two Viewmodels were created for sending weather information to the required Fragments. I also created separate Repositories for pulling data from the network and bringing that data into the Viewmodels. This is all done with Livedata.

I also went with the one Activity, many Fragments approach. 

Displaying and navigating between Fragments is done with the NavigationComponent library. 

Next I'll spend some time talking about the Main Activity and Fragments I created and what they are responsible for. 
