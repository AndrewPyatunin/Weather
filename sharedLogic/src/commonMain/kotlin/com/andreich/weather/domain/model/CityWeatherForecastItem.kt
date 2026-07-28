package com.andreich.weather.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CityWeatherForecastItem(
    val id: Int,
    val cityName: String,
    val forecastWeather: List<Weather>,
    val isFavorite: Boolean
)
