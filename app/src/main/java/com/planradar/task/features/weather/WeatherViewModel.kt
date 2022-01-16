package com.planradar.task.features.weather

import androidx.lifecycle.*
import com.planradar.data.models.City
import com.planradar.data.repositories.weather.WeatherRepository
import com.planradar.task.R
import com.planradar.task.utils.resourcewrapper.ResourceWrapper
import com.planradar.task.utils.systemmanger.NetworkManger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val weatherRepository: WeatherRepository,
    private val resourceWrapper: ResourceWrapper,
    private val networkManger: NetworkManger
) : ViewModel() {

    private val _state = MutableLiveData<WeatherUiState>()
    fun state(): LiveData<WeatherUiState> = _state

    private val exHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        triggerError(throwable.message ?: resourceWrapper.getString(R.string.error_common))
    }

    fun getWeather(city: City) {
        if (!networkManger.isOnline()) {
            triggerError(resourceWrapper.getString(R.string.error_no_internet_connection))
            return
        }
        _state.value = WeatherUiState(isLoading = true)
        viewModelScope.launch(exHandler) {
            weatherRepository.getWeather(city).collect {
                _state.value = WeatherUiState(
                    weather = it
                )
            }
        }
    }

    private fun triggerError(errorMessage: String) {
        _state.value = WeatherUiState(
            isError = true,
            error = errorMessage
        )
    }

}