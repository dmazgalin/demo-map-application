package com.mapsexperience.repository

import com.mapsexperience.api.RemoteApi
import com.mapsexperience.dto.CarDTO
import com.mapsexperience.dto.ResponceDTO
import com.mapsexperience.room.CarDTODao
import com.mapsexperience.room.MapApplicationRoomDb
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

class CarsRepositoryImplTest {

    @Mock
    lateinit var remoteApi: RemoteApi
    @Mock
    lateinit var db: MapApplicationRoomDb
    @Mock
    lateinit var response: Response<ResponceDTO>
    @Mock
    lateinit var carsDao: CarDTODao

    //SUT
    private lateinit var carsRepository: CarsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        carsRepository = CarsRepositoryImpl(remoteApi, db)
    }

    @Test
    fun getCarsFromCache() = runBlocking {
        whenever(db.carsDao()) doReturn carsDao
        whenever(carsDao.getAll()) doReturn listOf(getCar(1))

        val result = carsRepository.getCars()
        assert(result.size == 1)
    }

    @Test
    fun getCarsFromEmptyFeed() = runBlocking {

        whenever(db.carsDao()) doReturn carsDao
        whenever(carsDao.getAll()) doReturn emptyList()
        whenever(response.body()) doReturn ResponceDTO(emptyList())
        whenever(response.isSuccessful) doReturn true
        whenever(remoteApi.getCarsFromApi()) doReturn response

        val result = carsRepository.getCars()
        assert(result.isEmpty())
    }


    @Test
    fun getCarsFromNonEmptyFeed() = runBlocking {

        whenever(db.carsDao()) doReturn carsDao
        whenever(carsDao.getAll()) doReturn emptyList()
        whenever(response.body()) doReturn ResponceDTO(getCarsFeed())
        whenever(response.isSuccessful) doReturn true
        whenever(remoteApi.getCarsFromApi()) doReturn response

        val result = carsRepository.getCars()
        assert(result.size == 2)
    }

    private fun getCarsFeed(): List<CarDTO> = listOf(getCar(1), getCar(2))

    private fun getCar(i: Int): CarDTO = CarDTO(
        vin = "vin $i",
        name = "name $i",
        interior = "",
        fuel = 10,
        exterior = "",
        engineType = "random",
        address = "",
        coordinates = emptyList()
    )
}