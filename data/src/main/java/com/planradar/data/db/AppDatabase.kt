package com.planradar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.planradar.data.db.cities.CityDao
import com.planradar.data.db.cities.CityEntity
import com.planradar.data.db.weather.WeatherDao
import com.planradar.data.db.weather.WeatherEntity

@Database(entities = [CityEntity::class, WeatherEntity::class], version = 2)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao
}