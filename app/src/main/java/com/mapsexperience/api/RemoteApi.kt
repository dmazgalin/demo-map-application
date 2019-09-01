package com.mapsexperience.api

import com.mapsexperience.dto.ResponceDTO
import retrofit2.Response
import retrofit2.http.GET

interface RemoteApi {

    @GET("/locations.json")
    suspend fun getCarsFromApi(): Response<ResponceDTO>
}