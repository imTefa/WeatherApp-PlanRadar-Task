package com.planradar.data.datasource.weather.impl

import com.planradar.data.datasource.weather.RemoteWeatherDataSource
import com.planradar.data.models.City
import com.planradar.data.models.Weather
import com.planradar.data.network.Api
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import kotlin.math.roundToInt

private const val TAG = "RemoteWeatherDataSource"

internal class RemoteWeatherDataSourceImpl(
    private val api: Api,
    private val apiId: String,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RemoteWeatherDataSource {

    override suspend fun getWeather(city: City): Flow<Weather> {
        return flow {
            val weatherResponse = api.getCityWeather(city.name, apiId)
            emit(
                Weather(
                    cityId = city.id!!,
                    cityName = city.name,
                    countryName = city.country,
                    date = Date().time,
                    description = weatherResponse.weatherDescriptions[0].description,
                    iconId = weatherResponse.weatherDescriptions[0].iconId,
                    temp = fromKelvinToCelsius(weatherResponse.weatherInfo.temp),// Convert it to celsius
                    humidity = weatherResponse.weatherInfo.humidity,
                    windSpeed = weatherResponse.windInfo.speed
                )
            )
        }.flowOn(dispatcher)
    }


    private fun fromKelvinToCelsius(k: Double): Double {
        return ((k - 273.15) * 100).roundToInt() / 100.0
    }

}