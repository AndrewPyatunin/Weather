package com.andreich.weather.data.datasource

actual class CityDatasource {

    actual suspend fun buildCitiesData(): List<CityDto> {
        return emptyList()
    }
}