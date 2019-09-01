package com.mapsexperience.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mapsexperience.dto.CarDTO

@Database(entities = arrayOf(CarDTO::class), version = 1)
abstract class MapApplicationRoomDb : RoomDatabase() {
    abstract fun carsDao(): CarDTODao
}