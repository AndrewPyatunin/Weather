package com.andreich.weather.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreich.weather.domain.model.Weather

@Entity(tableName = "forecast_weather")
data class CityWeatherForecastEntity(
    @PrimaryKey
    val id: Int,
    val cityName: String,
    val weatherForecastList: List<Weather>,
    val timezone: Int
)
