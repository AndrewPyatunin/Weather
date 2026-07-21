package com.andreich.weather.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM cities_weather WHERE :lang = lang OR :lang = '' " +
            "AND :country = country OR :country = '' || :country AND :name = name OR :name IS NULL" +
            " ORDER BY population DESC")
    fun getCitiesWeather(lang: String, country: String, name: String?): Flow<List<CityWeatherEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCitiesWeatherList(list: List<CityWeatherEntity>)

    @Query("SELECT * FROM cities_weather WHERE :id = id LIMIT 1")
    fun getCityWeather(id: Int): Flow<CityWeatherEntity>
}