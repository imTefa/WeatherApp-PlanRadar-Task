package com.planradar.data.di

import com.planradar.data.datasource.cities.CitiesDataSource
import com.planradar.data.datasource.cities.RemoteCitiesDataSource
import com.planradar.data.datasource.weather.LocalWeatherDataSource
import com.planradar.data.datasource.weather.RemoteWeatherDataSource
import com.planradar.data.repositories.cities.CitiesRepository
import com.planradar.data.repositories.cities.impl.CitiesRepositoryImpl
import com.planradar.data.repositories.weather.WeatherRepository
import com.planradar.data.repositories.weather.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object RepositoriesModule {

    @Provides
    fun provideCitiesRepository(
        citiesDataSource: CitiesDataSource,
        remoteCitiesDataSource: RemoteCitiesDataSource
    ): CitiesRepository = CitiesRepositoryImpl(
        localeCitiesDataSource = citiesDataSource,
        remoteCityDataSource = remoteCitiesDataSource
    )


    @Provides
    fun provideWeatherRepository(
        remoteWeatherDataSource: RemoteWeatherDataSource,
        localWeatherDataSource: LocalWeatherDataSource
    ): WeatherRepository = WeatherRepositoryImpl(remoteWeatherDataSource, localWeatherDataSource)
}