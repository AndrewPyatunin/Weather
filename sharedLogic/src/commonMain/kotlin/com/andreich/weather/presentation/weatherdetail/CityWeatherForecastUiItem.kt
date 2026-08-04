package com.andreich.weather.presentation.weatherdetail

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class CityWeatherForecastUiItem(
    val id: Int,
    val cityName: String,
    val forecastWeatherList: List<ForecastWeatherItem>
)