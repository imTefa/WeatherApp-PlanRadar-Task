package com.planradar.data.repositories.cities.impl

import com.planradar.data.datasource.cities.CitiesDataSource
import com.planradar.data.models.City
import com.planradar.data.repositories.cities.CitiesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

internal class CitiesRepositoryImpl(
    private val cityDataSource: CitiesDataSource,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CitiesRepository {


    override suspend fun saveNewCity(city: City) = withContext(coroutineDispatcher) {
        if (cityDataSource.isCityExist(cityName = city.name)) throw Exception("City name already exists")
        cityDataSource.saveNewCity(city)
    }

    override suspend fun getCities(): Flow<List<City>> {
        return cityDataSource.getCities().flowOn(coroutineDispatcher)
    }
}