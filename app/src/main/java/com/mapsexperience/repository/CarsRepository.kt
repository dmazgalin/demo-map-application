package com.mapsexperience.repository

import com.mapsexperience.dto.CarDTO

interface CarsRepository {
    suspend fun getCars(): List<CarDTO>
}