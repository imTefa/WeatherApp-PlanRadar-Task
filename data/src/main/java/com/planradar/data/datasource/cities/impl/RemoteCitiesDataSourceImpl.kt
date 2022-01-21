package com.planradar.data.datasource.cities.impl

import com.planradar.data.datasource.cities.RemoteCitiesDataSource
import com.planradar.data.models.City
import com.planradar.data.network.models.RemoteCity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


internal class RemoteCitiesDataSourceImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RemoteCitiesDataSource {


    override suspend fun getCities(searchKey: String): List<City> {
        return withContext(dispatcher) {
            if (searchKey.isEmpty()) emptyList<City>()
            else
                provideFakeRemoteCities(searchKey).map {
                    City(
                        name = it.cityName,
                        country = it.countryName
                    )
                }
        }
    }


    //This should be like API call, or whatever
    //But as it's not clear in task specs I added this demo data
    private fun provideFakeRemoteCities(searchKey: String) = listOf(
        RemoteCity("Cairo", "EG"),
        RemoteCity("Giza", "EG"),
        RemoteCity("Alexandria", "EG"),
        RemoteCity("Luxor", "EG"),
        RemoteCity("Hurghada", "EG"),
        RemoteCity("Sharm El-Sheikh", "EG"),
        RemoteCity("London", "GB"),
        RemoteCity("Liverpool", "GB"),
        RemoteCity("Manchester", "GB"),
        RemoteCity("Birmingham", "GB"),
        RemoteCity("Cambridge", "GB"),
        RemoteCity("Vienna", "AT"),
        RemoteCity("Innsbruck", "AT"),
        RemoteCity("Salzburg", "AT"),
        RemoteCity("Linz", "AT"),
        RemoteCity("Villach", "AT"),
        RemoteCity("Paris", "FR"),
        RemoteCity("Marseille", "FR"),
        RemoteCity("Lyon", "FR"),
        RemoteCity("Nice", "FR"),
        RemoteCity("Lille", "FR"),
    ).filter {
        it.cityName.startsWith(searchKey, true)
    }
}