package com.andreich.weather.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CityWeatherEntity::class, CacheEntity::class], version = 1, exportSchema = true)
@TypeConverters(value = [WeatherConverter::class])
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    abstract fun cacheDao(): CacheDao
}