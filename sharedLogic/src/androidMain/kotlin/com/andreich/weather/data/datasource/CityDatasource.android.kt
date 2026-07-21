package com.andreich.weather.data.datasource

import android.content.Context
import kotlinx.serialization.json.Json
import weather.sharedlogic.generated.resources.Res

actual class CityDatasource(
    val context: Context
) {

    private var citiesList: List<CityDto> = emptyList()
    private val json = Json { ignoreUnknownKeys = true }

    actual suspend fun buildCitiesData(): List<CityDto> {
        if (citiesList.isNotEmpty()) return citiesList
        else {
            val citiesText = Res.readBytes("files/citiesSorted.json").decodeToString()
            synchronized(Unit) {
                if (citiesList.isEmpty()) {
                    citiesList =
                        json.decodeFromString<List<CityDto>>(citiesText)//.associateBy { it.name.trim().lowercase() }
                }
                return citiesList
            }
        }


    }
}