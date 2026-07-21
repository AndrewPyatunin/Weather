package com.andreich.weather.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class CityWeatherItem(
    val id: Int,
    val name: String,
    val temp: Double,
    val description: String
)