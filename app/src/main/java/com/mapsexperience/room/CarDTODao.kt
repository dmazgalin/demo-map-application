package com.mapsexperience.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mapsexperience.dto.CarDTO

@Dao
interface CarDTODao {
    @Query("SELECT * FROM cars")
    fun getAll(): List<CarDTO>

    @Query("SELECT * FROM cars WHERE vin IN (:vinIds)")
    fun loadAllByVinIds(vinIds: IntArray): List<CarDTO>

    @Insert
    fun insertAll(cars: List<CarDTO>)

    @Delete
    fun delete(user: CarDTO)
}