package com.planradar.task.features.cities

import androidx.lifecycle.*
import com.planradar.data.models.City
import com.planradar.data.repositories.cities.CitiesRepository
import com.planradar.task.utils.uiutils.Consumable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val citiesRepository: CitiesRepository
) : ViewModel() {


    private val _cities = MutableLiveData<CitiesUiState>()
    fun getUiState(): LiveData<CitiesUiState> = _cities

    private val _error = MutableLiveData<Consumable<String>>()
    fun errorState(): LiveData<Consumable<String>> = _error

    private val _navAction = MutableLiveData<Consumable<CitiesNavAction>>()
    fun navAction(): LiveData<Consumable<CitiesNavAction>> = _navAction

    fun getCities() {
        viewModelScope.launch {
            citiesRepository.getCities().collect { list ->
                _cities.value = CitiesUiState(
                    cities = if (list.isNullOrEmpty()) emptyList() else list.map { city ->
                        CityUiState(
                            id = city.id!!,
                            name = city.name,
                            onNavigateToCityInfo = { navigateToCityInfo(city) },
                            onNavigateToCityWeather = { navigateToCityWeather(city) }
                        )
                    }
                )
            }
        }
    }

    fun saveNewCity(cityName: String) {
        val exHandler = CoroutineExceptionHandler { _, throwable ->
           // throwable.printStackTrace()
            _error.value = Consumable(throwable.message ?: "Error occurred, please try again.")
        }

        viewModelScope.launch(exHandler) {
            if (cityName.isEmpty()) throw Exception("City name mustn't be empty.")
            citiesRepository.saveNewCity(City(name = cityName))
        }

    }

    private fun navigateToCityWeather(city: City) {
        _navAction.value = Consumable(CitiesNavAction.GoToWeatherScreen(city))
    }

    private fun navigateToCityInfo(city: City) {
        _navAction.value = Consumable(CitiesNavAction.GoToWeatherHistoryScreen(city))
    }

}