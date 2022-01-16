package com.mobile.weatherappglobalkinetic.ui.weather

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.mobile.weatherappglobalkinetic.model.CurrentWeatherInfo
import com.mobile.weatherappglobalkinetic.ui.weather.current.WeatherRepository
import com.mobile.weatherappglobalkinetic.util.MockResponseFileReader

class FakeCurrentWeatherRepository private constructor(private val path: String): WeatherRepository {
    override val currentWeather =  MutableLiveData<CurrentWeatherInfo>()
    override val isLoading = MutableLiveData<Boolean>()

    override fun getCurrentWeather(cityName: String?) {
        val reader = MockResponseFileReader(path)
        val gson = Gson()
        val currentWeatherInfo = gson.fromJson(reader.content, CurrentWeatherInfo::class.java)
        currentWeather.value = currentWeatherInfo
    }

    companion object {
        fun forSuccessfulResponse(): FakeCurrentWeatherRepository = FakeCurrentWeatherRepository("successful_current_weather_response.json")
        fun forFailedResponse(): FakeCurrentWeatherRepository = FakeCurrentWeatherRepository("failed_response.json")
    }
}