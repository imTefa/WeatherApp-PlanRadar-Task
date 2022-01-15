package com.planradar.task.features.weather

import com.planradar.data.models.Weather

data class WeatherUiState(
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null
)