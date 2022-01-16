package com.mobile.weatherappglobalkenetic.ui.weather.forecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.weatherappglobalkenetic.R
import com.mobile.weatherappglobalkenetic.databinding.FragmentForecastBinding
import com.mobile.weatherappglobalkenetic.ui.main.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastFragment : Fragment() {

    private val forecastViewModel: ForecastViewModel by viewModels()
    private val locationViewModel: LocationViewModel by activityViewModels()
    private lateinit var adapter: ForecastAdapter
    private lateinit var binding: FragmentForecastBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast, container, false)

        initForecastsList()
        observeForecastViewModel()
        observeLocationViewModel()

        return binding.root
    }

    private fun initForecastsList() {
        val forecastList = binding.forecastList
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        adapter = ForecastAdapter(requireContext())
        forecastList.adapter = adapter
    }

    private fun observeForecastViewModel() {
        forecastViewModel.forecast.observe(this) {
            adapter.updateForecasts(it)
        }
    }

    private fun observeLocationViewModel() {
        locationViewModel.cityName.observe(this) {
            if (it != null) {
                forecastViewModel.getForecast(it)
            }
        }
    }
}