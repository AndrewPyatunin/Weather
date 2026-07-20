package com.andreich.weather.network

import kotlinx.serialization.SerialName

data class WindDto(
    @SerialName("speed") val speed: Double? = null,
    @SerialName("deg") val deg: Int? = null,
    @SerialName("gust") val gust: Double? = null
)
