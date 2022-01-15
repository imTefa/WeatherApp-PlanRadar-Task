package com.planradar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.planradar.data.db.cities.CityDao
import com.planradar.data.db.cities.CityEntity

@Database(entities = [CityEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
}