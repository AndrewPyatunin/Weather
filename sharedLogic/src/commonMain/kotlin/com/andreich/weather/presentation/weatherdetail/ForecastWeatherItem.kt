package com.andreich.weather.presentation.weatherdetail

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class ForecastWeatherItem(
    val header: String,
    val listWeather: List<WeatherUi>
)