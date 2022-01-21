package com.planradar.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val cityId: Long,
    val cityName: String,
    val countryName: String,
    val date: Long,
    val description: String,
    val temp: Double,
    val humidity: Double,
    val windSpeed: Double,
    val iconId: String
) : Parcelable{

    fun provideFullName() = "$cityName, $countryName"
}