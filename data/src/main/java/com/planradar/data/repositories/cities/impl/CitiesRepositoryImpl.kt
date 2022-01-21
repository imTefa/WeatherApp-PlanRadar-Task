package com.planradar.data.repositories.cities.impl

import com.planradar.data.datasource.cities.CitiesDataSource
import com.planradar.data.datasource.cities.RemoteCitiesDataSource
import com.planradar.data.models.City
import com.planradar.data.repositories.cities.CitiesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

internal class CitiesRepositoryImpl(
    private val localeCitiesDataSource: CitiesDataSource,
    private val remoteCityDataSource: RemoteCitiesDataSource,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CitiesRepository {


    override suspend fun saveNewCity(city: City) = withContext(coroutineDispatcher) {
        if (localeCitiesDataSource.isCityExist(cityName = city.name)) throw Exception("City name already exists")
        localeCitiesDataSource.saveNewCity(city)
    }

    override suspend fun getCities(): Flow<List<City>> {
        return localeCitiesDataSource.getCities().flowOn(coroutineDispatcher)
    }

    override suspend fun searchForCity(searchKey: String): List<City> {
        return withContext(coroutineDispatcher) {
            remoteCityDataSource.getCities(searchKey)
        }
    }
}