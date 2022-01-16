package com.mobile.weatherappglobalkinetic.api

import android.content.Context
import androidx.lifecycle.MutableLiveData

abstract class ABaseRequestManager {

    val isLoading = MutableLiveData<Boolean>()
    abstract val apiInterface: ApiInterface
    abstract val context: Context

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.value = isLoading
    }
}