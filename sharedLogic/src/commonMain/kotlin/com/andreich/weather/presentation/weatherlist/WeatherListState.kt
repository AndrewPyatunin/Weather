package com.andreich.weather.presentation.weatherlist

import com.andreich.weather.domain.model.CityWeatherItem
import com.andreich.weather.presentation.core.UiState

data class WeatherListState(
    val weatherList: List<CityWeatherItem> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingNextPage: Boolean = false,
    val lang: String = "ru",
    val country: String = "ru",
    val menuExpanded: Boolean = false
) : UiState