package com.mobile.weatherappglobalkenetic.ui.weather.current

import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappglobalkenetic.api.ApiInterface
import com.mobile.weatherappglobalkenetic.model.CurrentWeatherInfo
import com.mobile.weatherappglobalkenetic.api.ABaseRequestManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class WeatherRequestManager @Inject constructor(private val apiInterface: ApiInterface): ABaseRequestManager() {

    val currentWeatherInfo = MutableLiveData<CurrentWeatherInfo>()

    fun getCurrentWeatherInfo(cityName: String?) {
        setIsLoading(true)

        apiInterface.getCurrentWeatherInfo(cityName).enqueue(object : Callback<CurrentWeatherInfo> {
            override fun onResponse(call: Call<CurrentWeatherInfo>, response: Response<CurrentWeatherInfo>) {
                if(response.code() == 404) {
                    currentWeatherInfo.value = CurrentWeatherInfo(errorMessage = "City not found!")
                } else {
                    currentWeatherInfo.value = response.body()
                }
                setIsLoading(false)
            }

            override fun onFailure(call: Call<CurrentWeatherInfo>, t: Throwable) {
                // TODO: Extract string resources 
                val message: String = if (t is IOException) {
                    "Could not retrieve the current weather. You may have lost your internet connection"
                } else {
                    "There was an error. Could not retrieve the current weather."
                }

                currentWeatherInfo.value = CurrentWeatherInfo(errorMessage = message)
                setIsLoading(false)
            }
        })
    }
}