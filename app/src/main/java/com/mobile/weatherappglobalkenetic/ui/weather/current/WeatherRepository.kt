package com.mobile.weatherappglobalkenetic.ui.weather.current

import androidx.lifecycle.LiveData
import com.mobile.weatherappglobalkenetic.model.CurrentWeatherInfo

interface WeatherRepository {
    val currentWeather: LiveData<CurrentWeatherInfo>
    val isLoading: LiveData<Boolean>
    fun getCurrentWeather(cityName: String?)
}