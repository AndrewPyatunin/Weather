package com.andreich.weather.presentation.weatherdetail

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class WeatherUi(
    val dt: Int,
    val hour: String,
    val temp: String,
    val weatherCondition: WeatherCondition,
    val windSpeed: String,
    val windDirection: WindDirection
)