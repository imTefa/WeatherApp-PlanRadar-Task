package com.planradar.task.features.cities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.planradar.data.models.City
import com.planradar.data.repositories.cities.CitiesRepository
import com.planradar.task.R
import com.planradar.task.TestCoroutineRule
import com.planradar.task.getOrAwaitValue
import com.planradar.task.utils.resourcewrapper.ResourceWrapper
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class CitiesViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CitiesViewModel

    private val citiesRepository: CitiesRepository = mock()
    private val savedStateHandle: SavedStateHandle = mock()
    private val resourceWrapper: ResourceWrapper = mock()

    @Before
    fun before() {
        viewModel = CitiesViewModel(savedStateHandle, citiesRepository)
    }

    @Test
    fun `when get saved cities - success & have data - trigger ui state with data`() = runBlocking {
        val fakeData = provideFakeListOfCities()
        Mockito.`when`(citiesRepository.getCities()).thenAnswer {
            flowOf(fakeData)
        }

        viewModel.getCities()

        val uiState = viewModel.getUiState().getOrAwaitValue()

        assertEquals(2, uiState.cities.size)


        assertEquals(fakeData[0].id, uiState.cities[0].id)
        assertEquals(fakeData[0].name, uiState.cities[0].name)

        assertEquals(fakeData[1].id, uiState.cities[1].id)
        assertEquals(fakeData[1].name, uiState.cities[1].name)
    }

    @Test
    fun `when get saved cities - success & empty data - trigger ui state with empty data`() =
        runBlocking {
            val fakeData = provideFakeListOfCities()
            Mockito.`when`(citiesRepository.getCities()).thenAnswer {
                flowOf(emptyList<City>())
            }

            viewModel.getCities()

            val uiState = viewModel.getUiState().getOrAwaitValue()

            assertEquals(0, uiState.cities.size)
        }

}

inline fun <reified T> mock(): T = Mockito.mock(T::class.java)

fun provideFakeListOfCities() = listOf(
    City(1, "Cairo","EG"),
    City(2, "London","GB")
)
