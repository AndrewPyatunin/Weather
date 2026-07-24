package com.andreich.weather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(destination: NavDestinations) {
        navHostController.navigate(destination) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
//    private fun NavHostController.popUpGlobalDestination() {
//        while (currentBackStackEntry?.destination?.hasRoute<>())
//    }
}

@Composable
fun rememberNavigationState(navHostController: NavHostController = rememberNavController()) =
    remember { NavigationState(navHostController) }