package com.mobile.weatherappglobalkinetic.ui.weather.forecast

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.mobile.weatherappglobalkinetic.R
import com.mobile.weatherappglobalkinetic.api.ABaseRequestManager
import com.mobile.weatherappglobalkinetic.api.ApiInterface
import com.mobile.weatherappglobalkinetic.model.FiveDayForecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ForecastRequestManager @Inject constructor(
    override val apiInterface: ApiInterface,
    override val context: Context
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