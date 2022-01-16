package com.mobile.weatherappglobalkinetic.model


import com.google.gson.annotations.SerializedName

data class FiveDayForecast(
    @SerializedName("cod")
    val cod: String? = null,
    @SerializedName("list")
    val list: List<Forecast>? = null,
    @SerializedName("message")
    val message: String? = null,
)