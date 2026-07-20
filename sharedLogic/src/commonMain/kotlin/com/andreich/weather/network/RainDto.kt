package com.andreich.weather.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RainDto(
    @SerialName("1h") val oneHour: Double? = null
)
