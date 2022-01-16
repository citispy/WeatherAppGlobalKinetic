package com.mobile.weatherappglobalkinetic.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mobile.weatherappglobalkinetic.R
import com.mobile.weatherappglobalkinetic.databinding.FragmentWeatherBinding
import com.mobile.weatherappglobalkinetic.ui.main.LocationViewModel
import com.mobile.weatherappglobalkinetic.ui.main.PermissionsViewModel
import com.mobile.weatherappglobalkinetic.ui.weather.UiViewModel.*
import com.mobile.weatherappglobalkinetic.ui.weather.UiViewModel.UiState.*
import com.mobile.weatherappglobalkinetic.ui.weather.current.WeatherViewModel
import com.mobile.weatherappglobalkinetic.ui.weather.forecast.ForecastViewModel
import com.mobile.weatherappglobalkinetic.util.Event
import com.mobile.weatherappglobalkinetic.util.FormatUtils
import com.mobile.weatherappglobalkinetic.util.TrackingUtils
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for displaying the current weather and 5 day forecast
 * Before retrieving the weather information, we first check if location permissions were granted @see [MainActivity]
 * Then we check if the user has a last known location @see [provideLocation]
 * If the user has a location, then we make a call to the api
 * If the user doesn't have a location, then an error message is displayed
 */

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val locationViewModel: LocationViewModel by activityViewModels()
    private val weatherViewModel: WeatherViewModel by activityViewModels()
    private val forecastViewModel: ForecastViewModel by activityViewModels()
    private val permissionsViewModel: PermissionsViewModel by activityViewModels()
    private val uiViewModel: UiViewModel by viewModels()

    private lateinit var binding: FragmentWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiViewModel.addErrorSource(weatherViewModel.errorMessage)
        uiViewModel.addLoadingSource(weatherViewModel.isLoading)
        uiViewModel.addLoadingSource(forecastViewModel.isLoading)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        binding.refreshLayout.setOnRefreshListener {
            permissionsViewModel.requestLocationPermissions(true)
        }

        initClickListeners()
        observeViewModels()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        uiViewModel.removeErrorSource(weatherViewModel.errorMessage)
        uiViewModel.removeLoadingSource(weatherViewModel.isLoading)
        uiViewModel.removeLoadingSource(forecastViewModel.isLoading)
    }

    private fun initClickListeners() {
        binding.retryWeatherBtn.setOnClickListener {
            provideLocation()
        }
        binding.setLocationBtn.setOnClickListener() {
            findNavController().navigate(R.id.action_weatherFragment_to_placePickerFragment)
        }
    }

    private fun observeViewModels() {
        observeUiState()
        observeLocation()
        observePermissions()
        observerWeather()
        observerForecast()
    }

    private fun observeUiState() {
        uiViewModel.uiState.observe(this) {
            updateUi(it)
        }
    }

    private fun observeLocation() {
        locationViewModel.cityName.observe(this) {
            if (it == null) {
                permissionsViewModel.requestLocationPermissions(true)
            }
        }

        locationViewModel.location.observe(this) {
            val location = it.contentIfNotHandled
            if (location != null) {
                val city = FormatUtils.cityForLocation(location, requireContext())
                locationViewModel.setCityName(city)
            }
        }
    }

    private fun observePermissions() {
        permissionsViewModel.locationPermissionsGranted.observe(this) {
            val granted = it.contentIfNotHandled == true
            if (granted) {
                provideLocation()
            } else {
                uiViewModel.setNoLocation()
            }
        }
    }

    private fun observerWeather() {
        weatherViewModel.errorMessage.observe(this) {
            binding.errorMessageTv.text = it
        }
    }

    private fun observerForecast() {
        forecastViewModel.message.observe(this) {
            if (it != null) {
                binding.errorMessageTv.text = it
            }
        }
    }

    private fun provideLocation() {
        val location = TrackingUtils.getLastKnownLocation(requireContext())
        if (location == null) {
            uiViewModel.setNoLocation()
            return
        }
        locationViewModel.location.value = Event(location)
    }

    private fun updateUi(it: UiState?) {
        binding.errorMessageTv.visibility =
            if (it == ERROR_MESSAGE_RECEIVED || it == NO_LOCATION_FOUND) View.VISIBLE else View.GONE
        binding.retryWeatherBtn.visibility =
            if (it == ERROR_MESSAGE_RECEIVED) View.VISIBLE else View.GONE
        binding.currentWeatherFragment.visibility =
            if (it == SUCCESSFULLY_RETRIEVED_DATA) View.VISIBLE else View.GONE
        binding.forecastFragment.visibility =
            if (it == SUCCESSFULLY_RETRIEVED_DATA) View.VISIBLE else View.GONE
        binding.divider.visibility =
            if (it == SUCCESSFULLY_RETRIEVED_DATA) View.VISIBLE else View.GONE
        binding.setLocationBtn.visibility =
            if (it == NO_LOCATION_FOUND) View.VISIBLE else View.GONE

        if (it == NO_LOCATION_FOUND) {
            binding.errorMessageTv.text = getString(R.string.location_not_found_select_location_manually)
        }

        binding.refreshLayout.isRefreshing = it == LOADING
    }
}