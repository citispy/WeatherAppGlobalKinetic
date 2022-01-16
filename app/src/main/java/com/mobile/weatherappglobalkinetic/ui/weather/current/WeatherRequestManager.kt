package com.mobile.weatherappglobalkinetic.ui.weather.current

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappglobalkinetic.R
import com.mobile.weatherappglobalkinetic.api.ApiInterface
import com.mobile.weatherappglobalkinetic.model.CurrentWeatherInfo
import com.mobile.weatherappglobalkinetic.api.ABaseRequestManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class WeatherRequestManager @Inject constructor(private val apiInterface: ApiInterface, private val context: Context): ABaseRequestManager() {

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
                    context.getString(R.string.no_internet_message)
                } else {
                    context.getString(R.string.unknown_error_message)
                }

                currentWeatherInfo.value = CurrentWeatherInfo(errorMessage = message)
                setIsLoading(false)
            }
        })
    }
}