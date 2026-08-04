package com.andreich.weather.presentation.weatherdetail

sealed interface TimeOfDay {
    object Day : TimeOfDay
    object Night : TimeOfDay
}