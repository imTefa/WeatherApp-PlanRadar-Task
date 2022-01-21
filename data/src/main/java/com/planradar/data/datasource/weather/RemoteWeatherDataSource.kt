package com.planradar.data.datasource.weather

import com.planradar.data.models.City
import com.planradar.data.models.Weather
import kotlinx.coroutines.flow.Flow

interface RemoteWeatherDataSource {

     suspend fun getWeather(city: City): Flow<Weather>
}