package com.mobile.weatherappglobalkenetic.ui.weather.forecast

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappglobalkenetic.R
import com.mobile.weatherappglobalkenetic.api.ABaseRequestManager
import com.mobile.weatherappglobalkenetic.api.ApiInterface
import com.mobile.weatherappglobalkenetic.model.FiveDayForecast
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class ForecastRequestManager @Inject constructor(
    private val apiInterface: ApiInterface,
    private val context: Context
) : ABaseRequestManager() {

    val fiveDayForecast = MutableLiveData<FiveDayForecast>()

    fun getForecast(cityName: String?) {
        setIsLoading(true)

        apiInterface.getForecast(cityName).enqueue(object : Callback<FiveDayForecast> {
            override fun onResponse(call: Call<FiveDayForecast>, response: Response<FiveDayForecast>) {
                fiveDayForecast.value = response.body()
                setIsLoading(false)
            }

            override fun onFailure(call: Call<FiveDayForecast>, t: Throwable) {
                fiveDayForecast.value = FiveDayForecast(message = context.getString(R.string.no_internet_message))
                setIsLoading(false)
            }
        })
    }
}