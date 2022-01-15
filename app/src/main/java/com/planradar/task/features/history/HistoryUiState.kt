package com.planradar.task.features.history

data class HistoryUiState(
    val history: List<RecordUiState> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: String? = null
) {
    fun isEmpty() = history.isEmpty()
}


data class RecordUiState(
    val date: Long,
    val description: String,
    val temp: Double,
    val onArrowClicked: () -> Unit
)