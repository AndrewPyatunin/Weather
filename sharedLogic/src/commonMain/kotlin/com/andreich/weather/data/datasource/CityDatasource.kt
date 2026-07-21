package com.andreich.weather.data.datasource

expect class CityDatasource {

    suspend fun buildCitiesData(): List<CityDto>
}