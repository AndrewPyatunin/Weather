package com.andreich.weather.data.datasource

import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val id: Int,
    val name: String,
    val lng: Double,
    val lat: Double,
    val country: String,
    val iso2: String,
    val population: Int
)
