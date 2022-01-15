package com.planradar.data.di

import com.planradar.data.datasource.cities.CitiesDataSource
import com.planradar.data.datasource.cities.impl.CitiesDataSourceImpl
import com.planradar.data.db.cities.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataSourceModule {

    @Singleton
    @Provides
    fun provideCityDataSource(
        cityDao: CityDao
    ): CitiesDataSource = CitiesDataSourceImpl(cityDao = cityDao)


}