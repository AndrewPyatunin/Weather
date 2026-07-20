package com.andreich.weather.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SysDto(
    @SerialName("type") val type: Int? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("sunrise") val sunrise: Int? = null,
    @SerialName("sunset") val sunset: Int? = null
)
