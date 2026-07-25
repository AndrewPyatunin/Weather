package com.andreich.weather.ext

data class AppBarState(
    val title: String = "Weather",
    val showFilter: Boolean = false,
    val onFilterClick: (() -> Unit)? = null
)