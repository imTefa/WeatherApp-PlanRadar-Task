package com.planradar.data.di

import com.planradar.data.datasource.cities.CitiesDataSource
import com.planradar.data.datasource.cities.impl.CitiesDataSourceImpl
import com.planradar.data.datasource.weather.RemoteWeatherDataSource
import com.planradar.data.datasource.weather.impl.RemoteWeatherDataSourceImpl
import com.planradar.data.db.cities.CityDao
import com.planradar.data.di.NetworkModule.API_ID_TAG
import com.planradar.data.network.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataSourceModule {

    @Singleton
    @Provides
    fun provideCityDataSource(
        cityDao: CityDao
    ): CitiesDataSource = CitiesDataSourceImpl(cityDao = cityDao)


    @Singleton
    @Provides
    fun provideRemoteWeatherDataSource(
        api: Api,
        @Named(API_ID_TAG) apiId: String
    ): RemoteWeatherDataSource = RemoteWeatherDataSourceImpl(api, apiId)




}