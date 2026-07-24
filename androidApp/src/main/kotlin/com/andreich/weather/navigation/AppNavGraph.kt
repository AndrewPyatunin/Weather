package com.andreich.weather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    weatherListContent: @Composable () -> Unit,
    weatherSearchContent: @Composable () -> Unit,
    weatherFavoriteContent: @Composable () -> Unit,
    weatherMapContent: @Composable () -> Unit,
    ) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = NavDestinations.WeatherList
    ) {
        composable<NavDestinations.WeatherList> { weatherListContent() }
        composable<NavDestinations.WeatherSearch> { weatherSearchContent() }
        composable<NavDestinations.WeatherFavorite> { weatherFavoriteContent() }
        composable<NavDestinations.WeatherMap> { weatherMapContent() }
    }
}