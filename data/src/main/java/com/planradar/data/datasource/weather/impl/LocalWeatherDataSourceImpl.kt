package com.planradar.data.datasource.weather.impl

import com.planradar.data.datasource.weather.LocalWeatherDataSource
import com.planradar.data.db.weather.WeatherDao
import com.planradar.data.db.weather.WeatherEntity
import com.planradar.data.models.Weather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class LocalWeatherDataSourceImpl(
    private val weatherDao: WeatherDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocalWeatherDataSource {


    override suspend fun saveWeatherRecord(weather: Weather) {
        withContext(dispatcher) {
            weatherDao.saveCity(
                WeatherEntity(
                    cityId = weather.cityId,
                    cityName = weather.cityName,
                    countryName = weather.countryName,
                    date = weather.date,
                    description = weather.description,
                    temp = weather.temp,
                    humidity = weather.humidity,
                    windSpeed = weather.windSpeed,
                    iconId = weather.iconId
                )
            )
        }
    }

    override suspend fun getWeatherCallHistory(cityId: Long): Flow<List<Weather>> {
        return weatherDao.getCityHistory(cityId).map {
            it.map { weatherEntity ->
                Weather(
                    cityId = weatherEntity.cityId,
                    cityName = weatherEntity.cityName,
                    countryName = weatherEntity.countryName,
                    date = weatherEntity.date,
                    description = weatherEntity.description,
                    temp = weatherEntity.temp,
                    humidity = weatherEntity.humidity,
                    windSpeed = weatherEntity.windSpeed,
                    iconId = weatherEntity.iconId
                )
            }
        }
    }
}