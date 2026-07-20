package com.andreich.weather.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordDto(
    @SerialName("lon") val lon: Double? = null,
    @SerialName("lat") val lat: Double? = null
)
