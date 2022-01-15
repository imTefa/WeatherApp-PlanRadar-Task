package com.planradar.data.datasource.weather

import com.planradar.data.models.Weather
import kotlinx.coroutines.flow.Flow

interface LocalWeatherDataSource {

    suspend fun saveWeatherRecord(weather: Weather)

    suspend fun getWeatherCallHistory(cityId: Long): Flow<List<Weather>>
}