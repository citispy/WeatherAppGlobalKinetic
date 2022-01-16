package com.mobile.weatherappglobalkenetic.ui.main

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.mobile.weatherappglobalkenetic.util.Event
import com.mobile.weatherappglobalkenetic.util.FormatUtils
import com.mobile.weatherappglobalkenetic.util.KEY_CITY_NAME
import com.mobile.weatherappglobalkenetic.util.SharedPrefsUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val sharedPrefsUtils: SharedPrefsUtils) : ViewModel() {

    val cityName = MutableLiveData<String?>()

    val location = MutableLiveData<Event<Location>>()

    fun setCityName(city: String?) {
        sharedPrefsUtils.savePrefs(KEY_CITY_NAME, city)
        cityName.value = city
    }

    init {
        val city = sharedPrefsUtils.getPrefs(KEY_CITY_NAME)
        setCityName(city)
    }
}