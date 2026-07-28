package com.andreich.weather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    weatherListContent: @Composable () -> Unit,
    weatherSearchContent: @Composable () -> Unit,
    weatherFavoriteContent: @Composable () -> Unit,
    weatherMapContent: @Composable () -> Unit,
    weatherForecastContent: @Composable (Int) -> Unit
    ) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = NavDestinations.HomeGraph
    ) {
        homeNavGraph {
            weatherListContent()
        }
        composable<NavDestinations.WeatherSearch> { weatherSearchContent() }
        composable<NavDestinations.WeatherFavorite> { weatherFavoriteContent() }
        composable<NavDestinations.WeatherMap> { weatherMapContent() }
        composable<NavDestinations.WeatherForecast> { backStackEntry ->
            val args = backStackEntry.toRoute<NavDestinations.WeatherForecast>()
            weatherForecastContent(args.id)
        }
    }
}