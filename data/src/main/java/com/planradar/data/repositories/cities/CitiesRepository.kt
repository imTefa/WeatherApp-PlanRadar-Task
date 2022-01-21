package com.planradar.data.repositories.cities

import com.planradar.data.models.City
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {

    suspend fun saveNewCity(city: City)

    suspend fun getCities(): Flow<List<City>>

    suspend fun searchForCity(searchKey: String): List<City>

}