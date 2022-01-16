package com.planradar.task.features.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.planradar.data.models.Weather
import com.planradar.data.repositories.weather.WeatherRepository
import com.planradar.task.TestCoroutineRule
import com.planradar.task.features.cities.mock
import com.planradar.task.getOrAwaitValue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class HistoryViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val savedStateHandle: SavedStateHandle = mock()
    private val weatherRepository: WeatherRepository = mock()

    private val viewModel = HistoryViewModel(savedStateHandle, weatherRepository)


    @Test
    fun `when get history - success & have data - trigger ui state with data`() = runBlocking {
        val currentDate = Date().time
        Mockito.`when`(weatherRepository.getWeatherHistory(1)).thenAnswer {
            flowOf(
                listOf(
                    Weather(
                        cityId = 1,
                        cityName = "Cairo",
                        date = currentDate,
                        description = "Cloudy",
                        temp = 23.0,
                        humidity = 15.0,
                        windSpeed = 50.0,
                        iconId = "01n"
                    )
                )
            )
        }

        viewModel.getHistoryRecord(1)

        val uiState = viewModel.state().getOrAwaitValue()

        assertEquals(1, uiState.history.size)

        assertEquals(currentDate, uiState.history[0].date)
        assertEquals("Cloudy", uiState.history[0].description)
        assertEquals(23.0, uiState.history[0].temp,0.0)
    }

    @Test
    fun `when get history- success & empty data - trigger ui state with empty data`() =
        runBlocking {
            Mockito.`when`(weatherRepository.getWeatherHistory(1)).thenAnswer {
                flowOf(emptyList<Weather>())
            }

            viewModel.getHistoryRecord(1)

            val uiState = viewModel.state().getOrAwaitValue()

            assertEquals(true, uiState.isEmpty())
        }


    @Test
    fun `when save history - any error occurred - trigger error message`() =
        runBlocking {
            Mockito.`when`(
                weatherRepository.getWeatherHistory(1)
            ).thenAnswer {
                throw Exception("fake error message")
            }

            viewModel.getHistoryRecord(1)

            val state = viewModel.state().getOrAwaitValue()

            assertEquals(true, state.isError)
            assertEquals("fake error message", state.error)
        }


}