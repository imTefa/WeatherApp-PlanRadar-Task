package com.planradar.task.features.addcity

import android.text.Editable
import android.util.Log
import androidx.lifecycle.*
import com.planradar.data.models.City
import com.planradar.data.repositories.cities.CitiesRepository
import com.planradar.task.R
import com.planradar.task.utils.resourcewrapper.ResourceWrapper
import com.planradar.task.utils.uiutils.Consumable
import com.planradar.task.utils.uiutils.SimpleTextWatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "AddCityViewModel"

@HiltViewModel
class AddCityViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val citiesRepository: CitiesRepository,
    private val resourceWrapper: ResourceWrapper
) : ViewModel() {

    private val _state = MutableLiveData(AddCityUiState())
    fun state(): LiveData<AddCityUiState> = _state

    private val _error = MutableLiveData<Consumable<String>>()
    fun errorState(): LiveData<Consumable<String>> = _error

    private val _navAction = MutableLiveData<Consumable<AddCityNavAction>>()
    fun navAction(): LiveData<Consumable<AddCityNavAction>> = _navAction

    private val searchKey = MutableStateFlow("")

    val textWatcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(text: Editable) {
            Log.i(TAG, "afterTextChanged: $text")
            searchKey.value = text.toString()
        }
    }


    init {
        viewModelScope.launch {
            searchKey.asStateFlow()
                .debounce(500)
                .distinctUntilChanged { old, new -> old == new }
                .collect {
                    Log.i(TAG, "collecting: $it")
                    val cities = citiesRepository.searchForCity(it)
                        .map { city ->
                            SearchCityUiState(
                                cityName = city.name,
                                countryName = city.country,
                                onCitySelected = { saveNewCity(city) }
                            )
                        }
                    _state.value = AddCityUiState(cities)
                }
        }
    }


    private fun saveNewCity(city: City) {
        val exHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            _error.value =
                Consumable(throwable.message ?: resourceWrapper.getString(R.string.error_common))
        }

        viewModelScope.launch(exHandler) {
            citiesRepository.saveNewCity(city)
            _navAction.value = Consumable(AddCityNavAction.GoBack)
        }

    }

}