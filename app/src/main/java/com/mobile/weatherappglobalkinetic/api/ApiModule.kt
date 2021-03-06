package com.mobile.weatherappglobalkinetic.api

import android.content.Context
import com.mobile.weatherappglobalkinetic.BuildConfig
import com.mobile.weatherappglobalkinetic.ui.weather.current.CurrentWeatherRepository
import com.mobile.weatherappglobalkinetic.ui.weather.current.WeatherRepository
import com.mobile.weatherappglobalkinetic.ui.weather.current.WeatherRequestManager
import com.mobile.weatherappglobalkinetic.ui.weather.forecast.ForecastRequestManager
import com.mobile.weatherappglobalkinetic.ui.weather.repository.ForecastRepository
import com.mobile.weatherappglobalkinetic.ui.weather.repository.ForecastWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                var request = chain.request()
                val url = request.url
                    .newBuilder()
                    .addQueryParameter("APPID", BuildConfig.OPEN_WEATHER_API_KEY).build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }.build()

    @Singleton
    @Provides
    fun provideRetroFit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)

    @Singleton
    @Provides
    fun provideWeatherRequestManager(apiInterface: ApiInterface, @ApplicationContext context: Context):
            WeatherRequestManager = WeatherRequestManager(apiInterface, context)

    @Singleton
    @Provides
    fun provideForecastRequestManager(apiInterface: ApiInterface, @ApplicationContext context: Context):
            ForecastRequestManager = ForecastRequestManager(apiInterface, context)

    @Singleton
    @Provides
    fun providesWeatherRepository(webRequestRequestManager: WeatherRequestManager) =
        CurrentWeatherRepository(webRequestRequestManager) as WeatherRepository

    @Singleton
    @Provides
    fun providesForecastRepository(forecastRequestManager: ForecastRequestManager) =
        ForecastWeatherRepository(forecastRequestManager) as ForecastRepository
}