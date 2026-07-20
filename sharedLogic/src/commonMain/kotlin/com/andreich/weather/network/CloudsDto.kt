package com.andreich.weather.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CloudsDto(
    @SerialName("all") val all: Int? = null
)
