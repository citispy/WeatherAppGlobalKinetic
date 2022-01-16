package com.mobile.weatherappglobalkinetic.ui.weather

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.mobile.weatherappglobalkinetic.model.FiveDayForecast
import com.mobile.weatherappglobalkinetic.ui.weather.repository.ForecastRepository
import com.mobile.weatherappglobalkinetic.util.MockResponseFileReader

class FakeForecastRepository private constructor(private val path: String): ForecastRepository {
    override val fiveDayForecast = MutableLiveData<FiveDayForecast>()
    override val isLoading = MutableLiveData<Boolean>()

    override fun getForecast(cityName: String?) {
        val reader = MockResponseFileReader(path)
        val gson = Gson()
        val forecast = gson.fromJson(reader.content, FiveDayForecast::class.java)
        fiveDayForecast.value = forecast
    }

    companion object {
        fun forSuccessfulResponse(): FakeForecastRepository = FakeForecastRepository("successful_forecast_response.json")
        fun forFailedResponse(): FakeForecastRepository = FakeForecastRepository("failed_response.json")
    }
}