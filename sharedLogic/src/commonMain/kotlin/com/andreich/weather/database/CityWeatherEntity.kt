package com.andreich.weather.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities_weather")
data class CityWeatherEntity(
    @PrimaryKey
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
