package com.mapsexperience.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.test.testLiveDataWrapper
import com.mapsexperience.dto.CarDTO
import com.mapsexperience.repository.CarsRepository
import com.mapsexperience.util.TestCoroutineScopeProvider
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MapActivityViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var carsRepository: CarsRepository

    val coroutineScope = TestCoroutineScope()
    val coroutineScopeProvider = TestCoroutineScopeProvider(coroutineScope)

    //SUT
    private lateinit var viewModel: MapActivityViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = MapActivityViewModel(carsRepository, coroutineScopeProvider)
    }

    @Test
    fun getCarsInList() = coroutineScope.runBlockingTest{

        whenever(carsRepository.getCars()) doReturn getCarsDTO()

        val carsDataObserver = viewModel.getCarsOnMapLiveData().testLiveDataWrapper()

        viewModel.getCarsOnMap()

        val carList = carsDataObserver.observedValues.last()
        assert(carList?.size == 2)
    }

    @After
    fun tearDown() {
        coroutineScope.cleanupTestCoroutines()
    }

    private fun getCarsDTO(): List<CarDTO> = listOf(getCar(1), getCar(2))

    private fun getCar(i: Int): CarDTO = CarDTO(
        vin = "vin $i",
        name = "name $i",
        interior = "",
        fuel = 10,
        exterior = "",
        engineType = "random",
        address = "",
        coordinates = listOf(10.0, 10.0, 0.0)
    )
}