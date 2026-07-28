package com.andreich.weather.presentation.weatherdetail

import com.andreich.weather.domain.model.CityImage
import com.andreich.weather.domain.model.CityWeatherForecastItem
import com.andreich.weather.presentation.core.UiState

data class WeatherDetailsState(
    val cityWeather: CityWeatherForecastItem? = null,
    val isLoading: Boolean = false,
    val imageLoading: Boolean = false,
    val cityImage: CityImage? = null,
    val isFavorite: Boolean = false
) : UiState
