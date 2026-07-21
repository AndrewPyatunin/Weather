package com.andreich.weather.data.mapper

import com.andreich.weather.database.CityWeatherEntity
import com.andreich.weather.domain.model.CityWeather
import com.andreich.weather.domain.model.CityWeatherItem

fun CityWeather.toCityWeatherEntity(): CityWeatherEntity {
    return CityWeatherEntity(
        id,
        lon,
        lat,
        name,
        temp,
        feelsLike,
        humidity,
        pressure,
        description,
        icon,
        weatherMain,
        visibility,
        windSpeed,
        windDirectionDeg,
        clouds,
        country,
        sunrise,
        sunset,
        dt,
        timezone,
        lang,
        population
    )
}
fun CityWeatherEntity.toCityWeather(): CityWeather {
    return CityWeather(
        id,
        lon,
        lat,
        name,
        temp,
        feelsLike,
        humidity,
        pressure,
        description,
        icon,
        weatherMain,
        visibility,
        windSpeed,
        windDirectionDeg,
        clouds,
        country,
        sunrise,
        sunset,
        dt,
        timezone,
        lang,
        population
    )
}

fun CityWeatherEntity.toCityWeatherItem(): CityWeatherItem {
    return CityWeatherItem(id, name, temp, description)
}