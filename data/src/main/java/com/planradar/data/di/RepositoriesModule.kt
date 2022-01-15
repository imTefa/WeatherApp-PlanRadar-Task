package com.planradar.data.di

import com.planradar.data.datasource.cities.CitiesDataSource
import com.planradar.data.repositories.cities.CitiesRepository
import com.planradar.data.repositories.cities.impl.CitiesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object RepositoriesModule {

    @Provides
    fun provideCitiesRepository(
        citiesDataSource: CitiesDataSource
    ): CitiesRepository = CitiesRepositoryImpl(cityDataSource = citiesDataSource)
}