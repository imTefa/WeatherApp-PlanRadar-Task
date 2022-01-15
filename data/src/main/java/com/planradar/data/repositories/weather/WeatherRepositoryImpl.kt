package com.planradar.data.repositories.weather

import com.planradar.data.datasource.weather.LocalWeatherDataSource
import com.planradar.data.datasource.weather.RemoteWeatherDataSource
import com.planradar.data.models.City
import com.planradar.data.models.Weather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

private const val TAG = "WeatherRepositoryImpl"

internal class WeatherRepositoryImpl(
    private val remoteWeatherDataSource: RemoteWeatherDataSource,
    private val localeWeatherDataSource: LocalWeatherDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {

    override suspend fun getWeather(city: City): Flow<Weather> {
        return remoteWeatherDataSource.getWeather(cityId = city.id!!, cityName = city.name).onEach {
            localeWeatherDataSource.saveWeatherRecord(it)
        }.flowOn(dispatcher)
    }

    override suspend fun getWeatherHistory(cityId: Long): Flow<List<Weather>> {
        return localeWeatherDataSource.getWeatherCallHistory(cityId).flowOn(dispatcher)
    }


}


