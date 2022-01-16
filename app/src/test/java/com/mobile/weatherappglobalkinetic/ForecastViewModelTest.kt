package com.mobile.weatherappglobalkinetic.ui.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mobile.weatherappglobalkinetic.model.ForecastListItem
import com.mobile.weatherappglobalkinetic.ui.weather.forecast.ForecastViewModel
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForecastViewModelTest : TestCase() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val repositoryForSuccess = FakeForecastRepository.forSuccessfulResponse()
    private val repositoryForFailure = FakeForecastRepository.forFailedResponse()

    @Test
    fun `check list size equals 6`() {
        val viewModel = ForecastViewModel(repositoryForSuccess)
        viewModel.getForecast("Test")
        val observer: Observer<ArrayList<ForecastListItem>?> = Observer {
            assert(it?.size == 6)
        }

        viewModel.forecast.observeForever(observer)
        viewModel.forecast.removeObserver(observer)
    }

    @Test
    fun `check list item values`() {
        val viewModel = ForecastViewModel(repositoryForSuccess)
        viewModel.getForecast("Test")
        val observer: Observer<ArrayList<ForecastListItem>?> = Observer {
            val item1 = it?.get(0)
            assert(item1?.temp == "25°" && item1.day == "Sunday" && item1.imageDrawableId == 2131165316)
            val item2 = it?.get(1)
            assert(item2?.temp == "22°" && item2.day == "Monday" && item2.imageDrawableId == 2131165307)
            val item3 = it?.get(2)
            assert(item3?.temp == "20°" && item3.day == "Tuesday" && item3.imageDrawableId == 2131165316)
            val item4 = it?.get(3)
            assert(item4?.temp == "25°" && item4.day == "Wednesday" && item4.imageDrawableId == 2131165318)
            val item5 = it?.get(4)
            assert(item5?.temp == "29°" && item5.day == "Thursday" && item5.imageDrawableId == 2131165307)
            val item6 = it?.get(5)
            assert(item6?.temp == "24°" && item6.day == "Friday" && item6.imageDrawableId == 2131165307)
        }

        viewModel.forecast.observeForever(observer)
        viewModel.forecast.removeObserver(observer)
    }

    @Test
    fun `check values for failed response`() {
        val viewModel = ForecastViewModel(repositoryForFailure)
        viewModel.getForecast("Test")
        val observer: Observer<ArrayList<ForecastListItem>?> = Observer {
            assert(it?.isEmpty() == true)
        }

        viewModel.forecast.observeForever(observer)
        viewModel.forecast.removeObserver(observer)
    }

    @Test
    fun `check message for successful response`() {
        val viewModel = ForecastViewModel(repositoryForSuccess)
        viewModel.getForecast("Test")
        val observer: Observer<String?> = Observer {
            assert(it == "0")
        }

        viewModel.message.observeForever(observer)
        viewModel.message.removeObserver(observer)
    }

    @Test
    fun `check message for failed response`() {
        val viewModel = ForecastViewModel(repositoryForFailure)
        viewModel.getForecast("Test")
        val observer: Observer<String?> = Observer {
            assert(it == "city not found")
        }

        viewModel.message.observeForever(observer)
        viewModel.message.removeObserver(observer)
    }
}