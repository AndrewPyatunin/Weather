package com.andreich.weather.network

import kotlinx.serialization.Serializable

@Serializable
data class ResultsDto(
    val id: String? = null,
    val urls: ImageUrlsDto? = ImageUrlsDto(),
)