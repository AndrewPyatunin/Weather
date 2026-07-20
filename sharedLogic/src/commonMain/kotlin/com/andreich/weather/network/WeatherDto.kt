package com.andreich.weather.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("mainDto") val main: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("icon") val icon: String? = null
)
