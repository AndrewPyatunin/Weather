package com.andreich.weather.presentation.weatherdetail

import com.andreich.weather.presentation.core.UiEvent

sealed interface WeatherDetailsEvent : UiEvent {

    class AddToFavoriteSuccess(val message: String) : WeatherDetailsEvent

    class RemoveFromFavoriteSuccess(val message: String) : WeatherDetailsEvent
}