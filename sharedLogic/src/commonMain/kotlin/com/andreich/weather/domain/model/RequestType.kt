package com.andreich.weather.domain.model

sealed interface RequestType {

    data class SearchRequestRu(val name: String) : RequestType

    data class WeatherRequestRu(val country: String) : RequestType

    data class SearchRequestEn(val name: String) : RequestType

    data class WeatherRequestEn(val country: String) : RequestType
}