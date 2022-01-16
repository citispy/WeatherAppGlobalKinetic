package com.mobile.weatherappglobalkinetic.ui.weather.current

import androidx.lifecycle.LiveData
import com.mobile.weatherappglobalkinetic.model.CurrentWeatherInfo

interface WeatherRepository {
    val currentWeather: LiveData<CurrentWeatherInfo>
    val isLoading: LiveData<Boolean>
    fun getCurrentWeather(cityName: String?)
}