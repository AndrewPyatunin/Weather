package com.andreich.weather.presentation.weatherdetail

import androidx.compose.runtime.Immutable
import com.andreich.weather.domain.model.CityImage
import com.andreich.weather.presentation.core.UiState

@Immutable
data class WeatherDetailsState(
    val cityWeather: CityWeatherForecastUiItem? = null,
    val isLoading: Boolean = false,
    val imageLoading: Boolean = false,
    val cityImage: CityImage? = null,
    val isFavorite: Boolean = false,
    val lang: String = "ru",
    val id: Int? = null
) : UiState
