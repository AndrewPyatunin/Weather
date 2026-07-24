package com.andreich.weather.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface RequestType {

    @Serializable
    data class SearchRequestRu(val name: String) : RequestType

    @Serializable
    data class WeatherRequestRu(val country: String) : RequestType

    @Serializable
    data class SearchRequestEn(val name: String) : RequestType

    @Serializable
    data class WeatherRequestEn(val country: String) : RequestType
}