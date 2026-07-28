package com.andreich.weather.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val temp: Double,
    val feelsLike: Double,
    val dt: Int,
    val humidity: Int,
    val pressure: Int,
    val description: String,
    val icon: String,
    val weatherMain: String,
    val visibility: Int,
    val windSpeed: Double,
    val windDirectionDeg: Int,
    val clouds: Int,
    val dtTxt: String,
)
