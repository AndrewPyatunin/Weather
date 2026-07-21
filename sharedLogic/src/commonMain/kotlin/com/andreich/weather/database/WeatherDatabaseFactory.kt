package com.andreich.weather.database

expect class WeatherDatabaseFactory {

    fun create(): WeatherDatabase
}