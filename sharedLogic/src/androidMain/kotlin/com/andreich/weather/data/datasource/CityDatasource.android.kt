package com.andreich.weather.data.datasource

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import weather.sharedlogic.generated.resources.Res

actual class CityDatasource(
    val context: Context
) {

    init {
        CoroutineScope(Dispatchers.Default).launch {
            buildCitiesData()
        }
    }

    private var citiesList: List<CityDto> = emptyList()
    private val json = Json { ignoreUnknownKeys = true }

    actual suspend fun buildCitiesData(): List<CityDto> {
        if (citiesList.isNotEmpty()) return citiesList
        else {
            val citiesText = Res.readBytes("files/citiesSorted.json").decodeToString()
            synchronized(Unit) {
                if (citiesList.isEmpty()) {
                    citiesList =
                        json.decodeFromString<List<CityDto>>(citiesText)
                }
                return citiesList
            }
        }


    }
}