package com.planradar.data.repositories.weather

import android.util.Log
import com.planradar.data.datasource.weather.RemoteWeatherDataSource
import com.planradar.data.models.City
import com.planradar.data.models.Weather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

private const val TAG = "WeatherRepositoryImpl"

internal class WeatherRepositoryImpl(
    private val remoteWeatherDataSource: RemoteWeatherDataSource,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {

    override suspend fun getWeather(city: City): Flow<Weather> {
        return remoteWeatherDataSource.getWeather(cityId = city.id!!, cityName = city.name)
    }

}


