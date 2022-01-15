package com.planradar.data.db.cities

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CityDao {

    @Query("SELECT * FROM cities")
    fun getAllCities(): Flow<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCity(city: CityEntity)

    @Query("SELECT COUNT('id') FROM cities WHERE city_name = :name")
    fun getRowCount(name: String): Int
}