package com.planradar.task.features.addcity

data class AddCityUiState(
    val cities: List<SearchCityUiState> = emptyList()
) {
    fun isEmpty() = cities.isEmpty()
}


data class SearchCityUiState(
    val cityName: String,
    val countryName: String,
    val onCitySelected: () -> Unit = {}
){
    override fun toString(): String {
        return "$cityName, $countryName"
    }
}