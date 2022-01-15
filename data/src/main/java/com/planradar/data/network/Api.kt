package com.planradar.data.network

import com.planradar.data.network.models.WeatherRemoteResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface Api {

    @GET("weather")
    suspend fun getCityWeather(
        @Query("q") cityName: String,
        @Query("appid") apiId: String
    ): WeatherRemoteResponse
}