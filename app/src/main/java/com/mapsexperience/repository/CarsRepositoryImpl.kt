package com.mapsexperience.repository

import com.mapsexperience.api.RemoteApi
import com.mapsexperience.dto.CarDTO
import com.mapsexperience.room.MapApplicationRoomDb

class CarsRepositoryImpl(val remoteApi: RemoteApi, val db: MapApplicationRoomDb) : CarsRepository {

    override suspend fun getCars(): List<CarDTO> {

        val carsInCache = db.carsDao().getAll()
        if (carsInCache.isNotEmpty()) {
            return carsInCache
        }

        val responseFromApi = remoteApi.getCarsFromApi()
        if (responseFromApi.isSuccessful) {
            val carsFromNetwork = responseFromApi.body()?.placemarks ?: emptyList()
            db.carsDao().insertAll(carsFromNetwork)
            return carsFromNetwork
        }

        return emptyList()
    }
}
