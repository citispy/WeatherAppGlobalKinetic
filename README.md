# WeatherAppGlobalKinetic

## Introduction
This is my submission for the Global Kinetic Tech Task that is part of their interview process.

The task was to create an Android app that displays the weather for a user's current location using the OpenWeatherMap API.

I should mention for transparency that this was my second consecutive tech task to create a weather app using the OpenWeatherMap API. I have therefore reused a lot of my old code but I believe I have made significant improvements. As of writing, I haven't received feedback for my previous task, so these improvements are my own.

## Improvisations
According to the requirements I received, one of the criteria used to assess the assignment is improvisation. I took this to mean that there is flexibility in the way I implement the app, and I'm allowed to add features that were not specifically requested. I will list the improvisations I have made but all except the first were requirements in my previous assignment. 

I have improvised in the following ways:
- Used the city name in my API request instead of coordinates. I wasn't getting accurate information back when using coordinates
- Added the ability to view a 5-day forecast
- Implemented PlacesAPI so that a user can set their location manually
- Change the background colour depending on the weather type (cloudy, sunny, or rainy)

## API Requests
Requests are made to the following endpoints:
- api.openweathermap.org/data/2.5/weather
- api.openweathermap.org/data/2.5/forecast

Requests are made using Retrofit. I created a class called ApiInterface which contains information about each request. I also created an abstract class called ABaseWebRequestManager which is extended by child request manager classes. Request manager classes handle everything about an API request, including loading states, error messages, and successful responses.

Requests require a city name as a parameter and are obtained in one of two ways, namely, from Geocoding using coordinates, or from the Places API, depending on whether the location was retrieved from GPS or PlacesAPI.

Units are also sent as a parameter in each API request. A unit can either be standard, imperial, or metric. I've set the default unit value to metric.

## App Flow
![This is an image](https://raw.githubusercontent.com/citispy/WeatherAppGlobalKenetic/master/WeatherApp-Flow-Diagram.png)

## Architecture
Firstly, the app is packaged-by-feature where all classes related to one feature or screen is included in the same package.

For the architecture, I used MVVM because it's the recommended approach by Google.

Two ViewModels were created for sending weather information to the required Fragments. I also created separate Repositories for pulling data from the network and bringing that data into the ViewModels. This is all done with LiveData.

I also went with the one Activity, many Fragments approach. Displaying and navigating between Fragments is done with the NavigationComponent library. 

## Screens and Responsibilities
Next, I'll spend some time talking about the MainActivity and Fragments I created and what they are responsible for.

### MainActivity
The MainActivity has several responsibilities which include:
- Setup of the NavGraph and Toolbar
- Requesting Permissions
- Setting the background colour when the weather type changes
- Setting the title of the Toolbar to the current city name

Permissions are requested by Fragments who require them through the PermissionsViewModel. The Fragment then uses a LiveData to observe whether the permissions were granted or not and responds appropriately.

The background colour is set by observing the WeatherViewModel so that when a successful API call is made, the Activity has access to the colour id.

The toolbar title is set by observing the LocationViewModel. The LocationViewModel checks whether a city name has been saved in SharedPreferences. If it has, the title is set. If not, the title is set when the last known location is found or the user sets their location in PlacesAPI.

### WeatherFragment
The WeatherFragment's main responsibility is displaying the CurrentWeatherFragment and the ForecastFragment. Other responsibilities of the WeatherFragment include:
- Keeping track of the UI state
- The UI state can either be:
  - LOADING
  - ERROR_MESSAGE_RECEIVED
  - SUCCESSFULLY_RETRIEVED_DATA
  - NO_LOCATION_FOUND
- Requesting Permissions if the city name has not been saved to SharedPreferences
- Getting the last known location if location permissions have been granted and then setting the location in the LocationViewModel
- Setting the city name in the LocationViewModel if a location was set by last known location or PlacesAPI
- Displaying error messages for API errors and no location errors

The UI state of the Fragment is determined by the UiViewModel which has a MediatorLiveData. WeatherViewModel.ErrorMessage, WeatherViewModel.isLoading and ForecastViewModel.isLoading are added as sources for this MediatorLiveData. I previously had ForecastViewModel.message as a source as well but it was causing issues. I'm not sure if this is a good solution for keeping track of the UI state but I think it's working fine. It does need some work though. I also created an enum class inside the UiViewModel for the different UI states. The observer inside the WeatherFragment then updates the UI by hiding views that are not required for that state or setting the error TextView text.

### CurrentWeatherFragment
The CurrentWeatherFragment has a single responsibility, displaying the current weather for the city that was set. An API call is made by observing the city name in the LocationViewModel. If the city name is not null, an API call is made.

The WeatherViewModel is used in this Fragment and before displaying the results, Transformation.map() is used several times to convert the data is to the correct format.

### ForecastFragment
The ForecastFragment also has a single responsibility, displaying a 5-day forecast inside a RecyclerView. Just as with the WeatherFragment, An API call is made by observing the city name in the LocationViewModel. If the city name is not null, an API call is made.

The response received in the API response gives several forecasts for each day. I didn't want to display all forecasts so I decided to only include the maximum temperature for each day. This is done by the addHighestTempPerDay() method. The forecast is also not added if the forecast is for the current day. These are all done inside a Transformation.map(). The Transformation.map() is used several times in this ViewModel to format data correctly to be used inside the ForecastFragment.

## Bugs
- Loading progress stops when the current weather is obtained from the API. There are times when the forecast hasn't been retrieved yet but there's no loading progress shown
- Clicking the edit icon in the Toolbar at the start destination and then again in the next destination crashes the app
- When setting a location using PlacesAPI and then being navigated back to the start destination you click the refresh icon, no loading progress is shown and the no location error displays before showing the weather for the current location

## Potential Improvements to be made
This list is not exhaustive but I thought I would list a few improvements that I think could be made:
- Create an ApiError object that creates error messages depending on the error type
- City name should be set inside the LocationViewModel when the location LiveData's value is set, instead of the WeatherFragment
- An API call is made on each device configuration change
- When initializing the LocationViewModel and the city name is retrieved from SharedPreferences, the setCityName() method is called which re-saves the city name in SharedPreferences before setting the location LiveData value
- Observing network connectivity issues using the ConnectivityManager
- Scoping ViewModels to the NavGraph so that the ViewModel is not kept alive when not needed. This would be important if more fragments were added to the app
- My ForecastAdapter uses notifyDataSetChanged() to update the adapter. This is not ideal
- I don't have much experience with Unit testing and do think my unit tests can be improved. I especially think I could make use of mock objects to test my DateUtils class. At the moment I'm not testing whether a specific date equals today's date

## Libraries Used
- Retrofit
- Gson
- OkHttp
- Hilt
- NavComponent
- JUnit4, Android Test Library, Android Architecture Components Core Test Library, and Robolectric
- EasyPermissions
- PlacesAPI
