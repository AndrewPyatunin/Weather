package com.andreich.weather.database

import androidx.room.TypeConverter
import com.andreich.weather.domain.model.RequestType
import com.andreich.weather.domain.model.Weather
import kotlinx.serialization.json.Json

class WeatherConverter {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    @TypeConverter
    fun fromRequestTypeToString(value: RequestType?): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun fromStringToRequestType(value: String?): RequestType? {
        if (value == null) return null
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun fromListWeatherToString(value: List<Weather>?): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun fromStringToListWeather(value: String?): List<Weather>? {
        if (value == null) return null
        return json.decodeFromString(value)
    }
}