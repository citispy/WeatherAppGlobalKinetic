package com.mobile.weatherappglobalkenetic.ui.weather.repository

import androidx.lifecycle.LiveData
import com.mobile.weatherappglobalkenetic.model.FiveDayForecast

interface ForecastRepository {
    val fiveDayForecast: LiveData<FiveDayForecast>
    val isLoading: LiveData<Boolean>
    fun getForecast(cityName: String?)
}