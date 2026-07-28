package com.andreich.weather.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavDestinations {

    @Serializable
    data object HomeGraph : NavDestinations
    @Serializable
    data object WeatherList : NavDestinations

    @Serializable
    data object WeatherSearch : NavDestinations

    @Serializable
    data object WeatherFavorite : NavDestinations

    @Serializable
    data object WeatherMap : NavDestinations

    @Serializable
    data class WeatherForecast(val id: Int) : NavDestinations
}