package com.mapsexperience.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mapsexperience.domain.CarInList
import com.mapsexperience.domain.CarOnMap
import org.json.JSONException
import org.json.JSONArray

@Entity(tableName = "cars")
@TypeConverters(DoubleListToStringConverter::class)
data class CarDTO(
    @PrimaryKey val vin: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "interior") val interior: String,
    @ColumnInfo(name = "fuel") val fuel: Int,
    @ColumnInfo(name = "exterior") val exterior: String,
    @ColumnInfo(name = "engine") val engineType: String,
    @ColumnInfo(name = "address") val address: String,
    val coordinates: List<Double>
)

fun CarDTO.transformToCarInList(): CarInList {
    return CarInList(id = vin, name = name, engine = engineType, interior = interior, exterior = exterior)
}

fun CarDTO.transformToCarOnMap(visible: Boolean): CarOnMap {
    return CarOnMap(id = vin, lat = coordinates[1], lon = coordinates[0], name = name, visible = visible)
}

class DoubleListToStringConverter {
    @TypeConverter
    fun stringfromDoubleArray(values: List<Double>): String {
        val jsonArray = JSONArray()
        for (value in values) {
            try {
                jsonArray.put(value)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
        return jsonArray.toString()
    }

    @TypeConverter
    fun doubleArrayFromString(values: String): List<Double> {
        try {
            val jsonArray = JSONArray(values)
            val doubleArray = mutableListOf<Double>()
            val length = jsonArray.length()
            if (length > 0) {
                for (i in 0 until jsonArray.length()) {
                    doubleArray.add(java.lang.Double.parseDouble(jsonArray.getString(i)))
                }
                return doubleArray
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return emptyList()
    }
}