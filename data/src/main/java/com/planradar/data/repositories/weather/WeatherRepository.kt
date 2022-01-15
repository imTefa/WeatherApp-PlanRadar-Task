package com.planradar.data.repositories.weather

import com.planradar.data.models.City
import com.planradar.data.models.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeather(city: City): Flow<Weather>

    suspend fun getWeatherHistory(cityId: Long): Flow<List<Weather>>

}