package com.mobile.weatherappglobalkenetic.ui.weather.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappglobalkenetic.model.CurrentWeatherInfo
import javax.inject.Inject

class CurrentWeatherRepository @Inject constructor(private val webRequestRequestManager: WeatherRequestManager): WeatherRepository {

    override val currentWeather: MutableLiveData<CurrentWeatherInfo> = webRequestRequestManager.currentWeatherInfo
    override val isLoading: LiveData<Boolean> = webRequestRequestManager.isLoading

    override fun getCurrentWeather(cityName: String?) {
        webRequestRequestManager.getCurrentWeatherInfo(cityName)
    }
}