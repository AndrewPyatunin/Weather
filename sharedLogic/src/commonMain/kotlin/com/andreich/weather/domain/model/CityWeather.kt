package com.andreich.weather.domain.model

data class CityWeather(
    val id: Int,
    val lon: Double,
    val lat: Double,
    val name: String,
    val temp: Double,
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val description: String,
    val icon: String,
    val weatherMain: String,
    val visibility: Int,
    val windSpeed: Double,
    val windDirectionDeg: Int,
    val clouds: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int,
    val dt: Int,
    val timezone: Int,
    val lang: String,
    val population: Int
)
