package com.andreich.weather.di

import android.content.Context
import com.andreich.weather.database.CacheDao
import com.andreich.weather.database.WeatherDao
import com.andreich.weather.database.WeatherDatabase
import com.andreich.weather.database.WeatherDatabaseFactory
import org.koin.dsl.module

fun databaseModule(context: Context) = module {
    single<WeatherDatabaseFactory> {
        WeatherDatabaseFactory(context)
    }
    single<WeatherDatabase> {
        get<WeatherDatabaseFactory>().create()
    }
    single<WeatherDao> {
        get<WeatherDatabase>().weatherDao()
    }
    single<CacheDao> {
        get<WeatherDatabase>().cacheDao()
    }
}