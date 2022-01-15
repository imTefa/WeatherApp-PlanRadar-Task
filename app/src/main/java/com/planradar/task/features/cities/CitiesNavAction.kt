package com.planradar.task.features.cities

import com.planradar.data.models.City

sealed class CitiesNavAction {

    data class GoToWeatherScreen(val city: City) : CitiesNavAction()
    data class GoToWeatherHistoryScreen(val cityId: Long) : CitiesNavAction()
}
