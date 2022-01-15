package com.planradar.task.features.history

import com.planradar.data.models.Weather

sealed class HistoryNavAction {

    data class GoToHistoryDetails(val weather: Weather): HistoryNavAction()


}