package com.mobile.weatherappglobalkenetic.ui.weather.current

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.mobile.weatherappglobalkenetic.R
import com.mobile.weatherappglobalkenetic.databinding.FragmentCurrentWeatherBinding
import com.mobile.weatherappglobalkenetic.ui.main.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {

    private val locationViewModel: LocationViewModel by activityViewModels()
    private val weatherViewModel: WeatherViewModel by activityViewModels()
    private lateinit var binding: FragmentCurrentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_current_weather, container, false)
        observerWeatherViewModel()
        observeLocationViewModel()

        return binding.root
    }

    private fun observerWeatherViewModel() {
        weatherViewModel.currentTemp.observe(this) {
            binding.temp.text = it
            binding.currentTemp.text = it
        }

        weatherViewModel.minTemp.observe(this) {
            binding.minTemp.text = it
        }

        weatherViewModel.maxTemp.observe(this) {
            binding.maxTemp.text = it
        }

        weatherViewModel.weatherDescription.observe(this) {
            binding.weatherDescription.text = it
        }
    }

    private fun observeLocationViewModel() {
        locationViewModel.cityName.observe(this) {
            if (it != null) {
                weatherViewModel.getCurrentWeather(it)
            }
        }
    }
}