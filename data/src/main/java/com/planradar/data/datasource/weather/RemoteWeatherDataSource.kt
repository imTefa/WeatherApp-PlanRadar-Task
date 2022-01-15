package com.planradar.data.datasource.weather

import com.planradar.data.models.Weather
import kotlinx.coroutines.flow.Flow

interface RemoteWeatherDataSource {

     suspend fun getWeather(cityId: Long, cityName: String): Flow<Weather>
}