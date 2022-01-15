package com.planradar.data.models

import com.planradar.data.db.cities.CityEntity

internal object CitiesMapper {

    fun mapCityToDbEntity(city: City) = CityEntity(
        id = city.id,
        name = city.name
    )


    fun mapDbCityToCity(cityEntity: CityEntity) = City(
        id = cityEntity.id,
        name = cityEntity.name
    )
}