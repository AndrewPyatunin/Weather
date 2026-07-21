package com.andreich.weather.database

import android.content.Context
import androidx.room.Room

actual class WeatherDatabaseFactory(
    private val context: Context
) {

    actual fun create(): WeatherDatabase {
        return Room.databaseBuilder(
            context = context,
            name = "weather.db",
            klass = WeatherDatabase::class.java
        ).build()
    }
}