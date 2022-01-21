package com.planradar.data.datasource.cities

import com.planradar.data.models.City
import com.planradar.data.network.models.RemoteCity

interface RemoteCitiesDataSource {

    suspend fun getCities(searchKey: String): List<City>

}