package com.mobile.weatherappglobalkinetic.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.weatherappglobalkinetic.util.Event

class PermissionsViewModel : ViewModel() {

    val locationPermissionsRequested = MutableLiveData<Event<Boolean>>()

    val locationPermissionsGranted = MutableLiveData<Event<Boolean>>()

    fun requestLocationPermissions(request : Boolean) {
        locationPermissionsRequested.value = Event(request)
    }

    fun permissionsGranted(granted: Boolean) {
        locationPermissionsGranted.value = Event(granted)
    }
}