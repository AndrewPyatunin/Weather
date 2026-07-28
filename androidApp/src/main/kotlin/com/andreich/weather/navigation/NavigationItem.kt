package com.andreich.weather.navigation

import com.andreich.weather.R


sealed class NavigationItem(
    val destination: NavDestinations,
    val titleResId: Int,
    val iconResId: Int
) {

    object Home : NavigationItem(
        NavDestinations.HomeGraph,
        R.string.app_name,
        R.drawable.home_24px
    )

    object Search : NavigationItem(
        NavDestinations.WeatherSearch,
        R.string.app_name,
        R.drawable.search_24px
    )

    object Map : NavigationItem(
        NavDestinations.WeatherMap,
        R.string.app_name,
        R.drawable.file_map_24px
    )

    object Favorite : NavigationItem(
        NavDestinations.WeatherFavorite,
        R.string.app_name,
        R.drawable.favorite_24px
    )
}