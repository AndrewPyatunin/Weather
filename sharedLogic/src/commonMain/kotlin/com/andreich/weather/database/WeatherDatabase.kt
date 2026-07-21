package com.andreich.weather.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CityWeatherEntity::class], version = 1, exportSchema = true)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}