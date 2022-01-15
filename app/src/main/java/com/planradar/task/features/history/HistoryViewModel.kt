package com.planradar.task.features.history

import androidx.lifecycle.*
import com.planradar.data.models.Weather
import com.planradar.data.repositories.weather.WeatherRepository
import com.planradar.task.utils.uiutils.Consumable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _state = MutableLiveData(HistoryUiState())
    fun state(): LiveData<HistoryUiState> = _state

    private val _navAction = MutableLiveData<Consumable<HistoryNavAction>>()
    fun navAction(): LiveData<Consumable<HistoryNavAction>> = _navAction


    fun getHistoryRecord(cityId: Long) {
        val exHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            _state.value = HistoryUiState(isError = true, error = throwable.message)
        }

        viewModelScope.launch(exHandler) {
            _state.value = HistoryUiState(isLoading = true)
            weatherRepository.getWeatherHistory(cityId).collect {
                _state.value = HistoryUiState(history = it.map { weather ->
                    RecordUiState(
                        date = weather.date,
                        description = weather.description,
                        temp = weather.temp,
                        onArrowClicked = {
                            navigateToDetailsScreen(weather)
                        }
                    )
                })
            }
        }
    }

    private fun navigateToDetailsScreen(weather: Weather) {
        _navAction.value = Consumable(HistoryNavAction.GoToHistoryDetails(weather))
    }
}