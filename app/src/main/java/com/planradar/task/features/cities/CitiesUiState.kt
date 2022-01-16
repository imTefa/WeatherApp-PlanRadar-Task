package com.planradar.task.features.cities

data class CitiesUiState(val cities: List<CityUiState>)

data class CityUiState(
    val id: Long,
    val name: String,
    val onNavigateToCityWeather: () -> Unit = {},
    val onNavigateToCityInfo: () -> Unit = {}
)

fun CitiesUiState.isEmpty() = cities.isEmpty()

