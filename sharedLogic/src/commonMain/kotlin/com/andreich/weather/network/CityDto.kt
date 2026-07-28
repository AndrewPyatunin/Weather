package com.andreich.weather.network

import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val id: Int? = null,
    val name: String? = null,
    val coord: CoordDto? = CoordDto(),
    val country: String? = null,
    val population: Int? = null,
    val timezone: Int? = null,
    val sunrise: Int? = null,
    val sunset: Int? = null
)
