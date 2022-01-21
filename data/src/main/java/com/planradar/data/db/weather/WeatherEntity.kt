package com.planradar.data.db.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.planradar.data.db.cities.CityEntity

@Entity(
    tableName = "weather_history", foreignKeys = [ForeignKey(
        entity = CityEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("city_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
internal data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "city_id") val cityId: Long,
    @ColumnInfo(name = "city_name") val cityName: String,
    @ColumnInfo(name = "country_name") val countryName: String,
    val date: Long,
    val description: String,
    val temp: Double,
    val humidity: Double,
    @ColumnInfo(name = "wind_speed") val windSpeed: Double,
    @ColumnInfo(name = "icon_id") val iconId: String
)