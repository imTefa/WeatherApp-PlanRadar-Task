package com.planradar.task.features.weather

import androidx.lifecycle.*
import com.planradar.data.models.City
import com.planradar.data.repositories.weather.WeatherRepository
import com.planradar.task.R
import com.planradar.task.utils.resourcewrapper.ResourceWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val weatherRepository: WeatherRepository,
    private val resourceWrapper: ResourceWrapper
) : ViewModel() {

    private val _state = MutableLiveData<WeatherUiState>()
    fun state(): LiveData<WeatherUiState> = _state

    private val exHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _state.value = WeatherUiState(
            isError = true,
            error = throwable.message ?: resourceWrapper.getString(R.string.error_common)
        )
    }

    fun getWeather(city: City) {
        _state.value = WeatherUiState(isLoading = true)
        viewModelScope.launch(exHandler) {
            weatherRepository.getWeather(city).collect {
                _state.value = WeatherUiState(
                    weather = it
                )
            }
        }
    }

}