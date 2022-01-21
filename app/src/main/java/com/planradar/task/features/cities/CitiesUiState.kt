package com.planradar.task.features.cities

data class CitiesUiState(val cities: List<CityUiState>)

data class CityUiState(
    val id: Long,
    val name: String,
    val countryName: String,
    val onNavigateToCityWeather: () -> Unit = {},
    val onNavigateToCityInfo: () -> Unit = {}
) {

    override fun toString(): String {
        return "$name, $countryName"
    }

}

fun CitiesUiState.isEmpty() = cities.isEmpty()

