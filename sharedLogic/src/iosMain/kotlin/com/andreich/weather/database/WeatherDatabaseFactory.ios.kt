package com.andreich.weather.database

import androidx.room.Room

actual class WeatherDatabaseFactory {

    actual fun create(): WeatherDatabase {
        return Room.databaseBuilder<WeatherDatabase>(name = "weather.db").build()
    }
}