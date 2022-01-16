package com.mobile.weatherappglobalkinetic.ui.weather.repository

import androidx.lifecycle.LiveData
import com.mobile.weatherappglobalkinetic.model.FiveDayForecast

interface ForecastRepository {
    val fiveDayForecast: LiveData<FiveDayForecast>
    val isLoading: LiveData<Boolean>
    fun getForecast(cityName: String?)
}