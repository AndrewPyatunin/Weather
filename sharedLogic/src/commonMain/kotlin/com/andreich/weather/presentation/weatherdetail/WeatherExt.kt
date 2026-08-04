package com.andreich.weather.presentation.weatherdetail

sealed interface WindDirection {
    object North : WindDirection
    object NorthEast : WindDirection
    object East : WindDirection
    object SouthEast : WindDirection
    object South : WindDirection
    object SouthWest : WindDirection
    object West : WindDirection
    object NorthWest : WindDirection
}

sealed interface WeatherCondition {
    object ClearDay : WeatherCondition
    object ClearNight : WeatherCondition
    object RainDay : WeatherCondition
    object RainNight : WeatherCondition
    object SnowDay : WeatherCondition
    object SnowNight : WeatherCondition
    object CloudyDay : WeatherCondition
    object CloudyNight : WeatherCondition
    object ThunderStormDay : WeatherCondition
    object ThunderStormNight : WeatherCondition
    object MistDay : WeatherCondition
    object MistNight : WeatherCondition
    object Drizzle : WeatherCondition
    object Tornado : WeatherCondition
    object Dust : WeatherCondition
    object Wind : WeatherCondition
    object Squall : WeatherCondition
}