package com.mobile.weatherappglobalkinetic.ui.main

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.weatherappglobalkinetic.util.Event
import com.mobile.weatherappglobalkinetic.util.KEY_CITY_NAME
import com.mobile.weatherappglobalkinetic.util.SharedPrefsUtils
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