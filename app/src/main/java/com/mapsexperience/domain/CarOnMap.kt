package com.mapsexperience.domain

data class CarOnMap(
    val id: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val visible: Boolean
)