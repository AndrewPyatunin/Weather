package com.andreich.weather.presentation.weatherdetail

import com.andreich.weather.presentation.core.UiIntent

sealed interface WeatherDetailsIntent : UiIntent {

    class ObserveWeather(val id: Int) : WeatherDetailsIntent

    class UpdateWeather(val id: Int) : WeatherDetailsIntent

    class LoadImage(val id: Int) : WeatherDetailsIntent

    class UpdateImage(val id: Int) : WeatherDetailsIntent

    object AddToFavorite : WeatherDetailsIntent

    object RemoveFromFavorite : WeatherDetailsIntent
}