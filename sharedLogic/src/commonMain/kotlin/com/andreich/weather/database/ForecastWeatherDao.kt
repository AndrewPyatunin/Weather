package com.andreich.weather.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastWeather(cityWeatherForecastEntity: CityWeatherForecastEntity)

    @Query("SELECT * FROM forecast_weather WHERE id = :id")
    fun getForecastWeather(id: Int): Flow<CityWeatherForecastEntity?>
}