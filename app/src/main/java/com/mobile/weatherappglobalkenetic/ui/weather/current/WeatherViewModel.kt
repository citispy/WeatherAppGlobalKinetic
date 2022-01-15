package com.mobile.weatherappglobalkenetic.ui.weather.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mobile.weatherappglobalkenetic.R
import com.mobile.weatherappglobalkenetic.model.CurrentWeatherInfo
import com.mobile.weatherappglobalkenetic.util.Constants.CLEAR
import com.mobile.weatherappglobalkenetic.util.Constants.CLOUDS
import com.mobile.weatherappglobalkenetic.util.Constants.RAIN
import com.mobile.weatherappglobalkenetic.util.FormatUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    private val currentWeatherInfo: LiveData<CurrentWeatherInfo> = repository.currentWeather

    val isLoading: LiveData<Boolean> = repository.isLoading

    val currentTemp: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        val temp = it.main?.temp?.toInt()?.toString()
        FormatUtils.getTempFormat(temp)
    }

    val minTemp: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        val temp = it.main?.temp?.toInt()?.toString()
        FormatUtils.getTempFormat(temp)
    }

    val maxTemp: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        val temp = it.main?.temp?.toInt()?.toString()
        FormatUtils.getTempFormat(temp)
    }

    val weatherDescription: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.weather?.get(0)?.main
    }

    val errorMessage: LiveData<String?> = Transformations.map(currentWeatherInfo) {
        it.errorMessage
    }

    val backgroundColor: LiveData<Int?> = Transformations.map(currentWeatherInfo) {
        when (it.weather?.get(0)?.main) {
            RAIN -> R.color.rain_dark_grey
            CLEAR -> R.color.clear_green
            CLOUDS -> R.color.clouds_grey
            else -> null
        }
    }

    fun getCurrentWeather(cityName: String?) {
        repository.getCurrentWeather(cityName)
    }
}