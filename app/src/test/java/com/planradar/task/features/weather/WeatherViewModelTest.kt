package com.planradar.task.features.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.planradar.data.models.City
import com.planradar.data.models.Weather
import com.planradar.data.repositories.weather.WeatherRepository
import com.planradar.task.TestCoroutineRule
import com.planradar.task.features.cities.mock
import com.planradar.task.getOrAwaitValue
import junit.framework.Assert.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class WeatherViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val savedStateHandle: SavedStateHandle = mock()
    private val weatherRepository: WeatherRepository = mock()


    private var weatherViewModel = WeatherViewModel(savedStateHandle, weatherRepository)

    @Test
    fun `when get city weather - everything is ok - trigger ui state with weatherUiState`() =
        runBlocking {
            val city = City(1, "Cairo")

            Mockito.`when`(
                weatherRepository.getWeather(city)
            ).thenAnswer {
                flowOf(
                    Weather(
                        cityId = city.id!!,
                        cityName = city.name,
                        date = Date().time,
                        description = "Cloudy",
                        temp = 23.0,
                        humidity = 15.0,
                        windSpeed = 50.0,
                        iconId = "01n"
                    )
                )
            }

            weatherViewModel.getWeather(city)

            val state = weatherViewModel.state().getOrAwaitValue()
            assertNotNull(state.weather)
            assertEquals(city.id, state.weather?.cityId)
            assertEquals(city.name, state.weather?.cityName)

        }


    @Test
    fun `when get city weather - fetch fails - trigger ui state with error`() =
        runBlocking {
            val city = City(1, "Cairo")
            Mockito.`when`(
                weatherRepository.getWeather(city)
            ).thenAnswer {
                throw Exception("Error occurred")
            }

            weatherViewModel.getWeather(city)

            val state = weatherViewModel.state().getOrAwaitValue()
            assertNull(state.weather)
            assertEquals(true, state.isError)
            assertEquals("Error occurred", state.error)

        }
}