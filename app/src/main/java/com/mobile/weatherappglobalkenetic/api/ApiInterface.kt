package com.mobile.weatherappglobalkenetic.api

import com.mobile.weatherappglobalkenetic.model.CurrentWeatherInfo
import com.mobile.weatherappglobalkenetic.model.FiveDayForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather")
    fun getCurrentWeatherInfo(
        @Query("q") cityName: String?,
        @Query("units") units: String = "metric"
    ): Call<CurrentWeatherInfo>

    @GET("forecast")
    fun getForecast(
        @Query("q") cityName: String?,
        @Query("units") units: String = "metric"
    ): Call<FiveDayForecast>

}