package com.planradar.data.repositories.cities

import androidx.lifecycle.LiveData
import com.planradar.data.models.City
import kotlinx.coroutines.flow.Flow

interface  CitiesRepository {

    suspend fun saveNewCity(city: City)

    suspend fun getCities(): Flow<List<City>>

}