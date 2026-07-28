package com.andreich.weather.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUnsplashDto(
    val total: Int? = null,
    @SerialName("total_pages") val totalPages: Int? = null,
    val results: ArrayList<ResultsDto> = arrayListOf()
)