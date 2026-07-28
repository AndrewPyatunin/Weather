package com.andreich.weather.presentation.weatherlist

import com.andreich.weather.presentation.core.UiIntent

sealed interface WeatherListIntent : UiIntent {

    object ObserveWeather : WeatherListIntent

    object UpdateWeather : WeatherListIntent

    object UpdateCitiesImages : WeatherListIntent

    class ObserveCitiesImages(val id: Int) : WeatherListIntent

    object LoadNextPage : WeatherListIntent

    object ShowMenu : WeatherListIntent

    object HideMenu : WeatherListIntent

    class WeatherItemClick(val id: Int) : WeatherListIntent
}