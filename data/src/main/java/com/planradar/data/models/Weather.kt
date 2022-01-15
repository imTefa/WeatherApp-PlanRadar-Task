package com.planradar.data.models

data class Weather(
    val cityId: Long,
    val cityName: String,
    val date: Long,
    val description: String,
    val temp: Double,
    val humidity: Double,
    val windSpeed: Double,
    val iconId: String
)