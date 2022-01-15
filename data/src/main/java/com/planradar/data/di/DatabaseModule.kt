package com.planradar.data.di

import android.content.Context
import androidx.room.Room
import com.planradar.data.db.AppDatabase
import com.planradar.data.db.cities.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {


    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "app-database"
        )
            .fallbackToDestructiveMigration()//not important to provide migration for now
            .build()
    }


    @Provides
    fun provideImageDao(
        appDatabase: AppDatabase
    ): CityDao {
        return appDatabase.cityDao()
    }

}