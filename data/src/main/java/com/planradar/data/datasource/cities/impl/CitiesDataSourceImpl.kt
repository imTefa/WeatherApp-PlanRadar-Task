package com.planradar.data.datasource.cities.impl

import com.planradar.data.datasource.cities.CitiesDataSource
import com.planradar.data.db.cities.CityDao
import com.planradar.data.db.cities.CityEntity
import com.planradar.data.models.City
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class CitiesDataSourceImpl(
    private val cityDao: CityDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CitiesDataSource {

    override suspend fun saveNewCity(city: City) {
        withContext(dispatcher) {
            cityDao.saveCity(
                CityEntity(
                    name = city.name,
                    country = city.country
                )
            )
        }
    }

    override suspend fun getCities(): Flow<List<City>> {
        return cityDao.getAllCities().map { list ->
            list.map { cityEntity ->
                City(
                    id = cityEntity.id,
                    name = cityEntity.name,
                    country = cityEntity.country
                )
            }
        }.flowOn(dispatcher)
    }

    override suspend fun isCityExist(cityName: String): Boolean {
        return withContext(dispatcher) {
            cityDao.getRowCount(cityName) > 0
        }
    }

}