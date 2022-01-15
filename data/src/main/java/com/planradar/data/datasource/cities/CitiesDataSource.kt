package com.planradar.data.datasource.cities

import androidx.lifecycle.LiveData
import com.planradar.data.models.City
import kotlinx.coroutines.flow.Flow

interface CitiesDataSource {

    suspend fun saveNewCity(city: City)

    suspend fun getCities(): Flow<List<City>>

    suspend fun isCityExist(cityName: String): Boolean

}