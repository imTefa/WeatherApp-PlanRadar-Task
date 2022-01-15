package com.planradar.data.datasource.cities.impl

import com.planradar.data.datasource.cities.CitiesDataSource
import com.planradar.data.db.cities.CityDao
import com.planradar.data.db.cities.CityEntity
import com.planradar.data.models.City
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CitiesDataSourceImpl(
    private val cityDao: CityDao,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CitiesDataSource {

    override suspend fun saveNewCity(city: City) {
        cityDao.saveCity(CityEntity(name = city.name))
    }

    override suspend fun getCities(): Flow<List<City>> {
        return cityDao.getAllCities().map { list ->
            list.map { cityEntity ->
                City(
                    id = cityEntity.id,
                    name = cityEntity.name
                )
            }
        }
    }

    override suspend fun isCityExist(cityName: String): Boolean {
        return cityDao.getRowCount(cityName) > 0
    }

}