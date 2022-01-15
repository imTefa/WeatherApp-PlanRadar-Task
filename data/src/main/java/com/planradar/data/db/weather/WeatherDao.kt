package com.planradar.data.db.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
internal interface WeatherDao {

    @Query("SELECT * FROM weather_history WHERE city_id = :cityId")
    fun getCityHistory(cityId: Long): Flow<List<WeatherEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveCity(city: WeatherEntity)
}