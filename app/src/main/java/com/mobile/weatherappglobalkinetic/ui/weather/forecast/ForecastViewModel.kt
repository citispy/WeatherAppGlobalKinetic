package com.mobile.weatherappglobalkinetic.ui.weather.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mobile.weatherappglobalkinetic.R
import com.mobile.weatherappglobalkinetic.model.FiveDayForecast
import com.mobile.weatherappglobalkinetic.model.ForecastListItem
import com.mobile.weatherappglobalkinetic.ui.weather.repository.ForecastRepository
import com.mobile.weatherappglobalkinetic.util.Constants.CLEAR
import com.mobile.weatherappglobalkinetic.util.Constants.CLOUDS
import com.mobile.weatherappglobalkinetic.util.Constants.RAIN
import com.mobile.weatherappglobalkinetic.util.DateUtils
import com.mobile.weatherappglobalkinetic.util.FormatUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val repository: ForecastRepository): ViewModel() {

    private val fiveDayForecast: LiveData<FiveDayForecast> = repository.fiveDayForecast

    val isLoading: LiveData<Boolean> = repository.isLoading

    val forecast: LiveData<ArrayList<ForecastListItem>> = Transformations.map(fiveDayForecast) {
        val list: ArrayList<ForecastListItem> = ArrayList()
        if (it != null) {
            populateList(it, list)
        }
        list
    }

    val message = Transformations.map(fiveDayForecast) {
        it?.message
    }

    private fun populateList(it: FiveDayForecast, list: ArrayList<ForecastListItem>) {
        val forecasts = it.list
        if (forecasts != null) {
            for (i in forecasts.indices) {
                val forecast = forecasts[i]

                //Don't add forecast for today
                if (!DateUtils.isNotToday(forecast.dtTxt)) {
                    continue
                }

                val main = forecast.main

                val temp = main?.temp?.toInt()?.toString()
                val formattedTemp = FormatUtils.getTempFormat(temp)
                val day = DateUtils.getDayForDate(forecast.dtTxt)
                val weatherDescription = forecast.weather?.get(0)?.main
                val imageDrawable = getImageDrawable(weatherDescription)

                if (formattedTemp != null && day != null && imageDrawable != null) {
                    val item = ForecastListItem(formattedTemp, day, imageDrawable)
                    addHighestTempPerDay(list, item)
                }
            }
        }
    }

    private fun addHighestTempPerDay(list: ArrayList<ForecastListItem>, newItem: ForecastListItem) {
        if (list.isEmpty()) {
            list.add(newItem)
            return
        }

        val lastListItem: ForecastListItem = list.last()
        if (lastListItem.day == newItem.day) {
            if (lastListItem.temp < newItem.temp) {
                list[list.lastIndex] = newItem
            }
        } else {
            list.add(newItem)
        }
    }

    private fun getImageDrawable(weatherDescription: String?): Int? {
        return when(weatherDescription) {
            CLOUDS -> R.drawable.ic_cloudy
            RAIN -> R.drawable.ic_rain
            CLEAR -> R.drawable.ic_sunny
            else -> null
        }
    }

    fun getForecast(cityName: String?) {
        repository.getForecast(cityName)
    }
}