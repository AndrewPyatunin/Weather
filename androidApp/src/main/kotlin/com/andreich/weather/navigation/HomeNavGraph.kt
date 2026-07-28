package com.andreich.weather.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.homeNavGraph(
    weatherListContent: @Composable () -> Unit
) {
    navigation<NavDestinations.HomeGraph>(startDestination = NavDestinations.WeatherList) {
        composable<NavDestinations.WeatherList> {
            weatherListContent()
        }
    }
}