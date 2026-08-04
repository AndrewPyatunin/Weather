package com.andreich.weather.navigation

import com.andreich.weather.R


sealed class NavigationItem(
    val destination: NavDestinations,
    val titleResId: Int,
    val iconResId: Int
) {

    object Home : NavigationItem(
        NavDestinations.HomeGraph,
        R.string.home,
        R.drawable.home_24px
    )

    object Search : NavigationItem(
        NavDestinations.WeatherSearch,
        R.string.search,
        R.drawable.search_24px
    )

    object Map : NavigationItem(
        NavDestinations.WeatherMap,
        R.string.map,
        R.drawable.file_map_24px
    )

    object Favorite : NavigationItem(
        NavDestinations.WeatherFavorite,
        R.string.favorite,
        R.drawable.favorite_24px
    )
}