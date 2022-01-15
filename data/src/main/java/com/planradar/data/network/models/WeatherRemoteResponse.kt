package com.planradar.data.network.models

import com.squareup.moshi.Json


//most of the this response data ignored (as we don't need them)
internal data class WeatherRemoteResponse(
    @Json(name = "weather") val weatherDescriptions: List<RemoteWeatherDescription>,
    @Json(name = "main") val weatherInfo: MainRemoteWeatherInfo,
    @Json(name = "wind") val windInfo: WindInfo
)



internal data class RemoteWeatherDescription(
    val id: Long,
    val main: String,
    val description: String,
    @Json(name = "icon") val iconId: String
)

internal data class MainRemoteWeatherInfo(
    val temp: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double,
    val pressure: Double,
    val humidity: Double
)

internal data class WindInfo(
    val speed: Double,
    val deg: Int
)