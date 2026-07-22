package com.andreich.weather.presentation.weatherlist

import com.andreich.weather.presentation.core.UiEvent

sealed interface WeatherListEvent : UiEvent {

    class NavigateToDetails(val id: Int) : WeatherListEvent
}