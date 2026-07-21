package com.andreich.weather.data.mapper

import com.andreich.weather.domain.model.CityWeather
import com.andreich.weather.network.ResponseDto

fun ResponseDto.toCityWeather(id: Int, name: String, lang: String, population: Int): CityWeather {
    return CityWeather(
        id = id,
        name = name,
        lon = coordDto?.lon ?: 0.0,
        lat = coordDto?.lat ?: 0.0,
        temp = mainDto?.temp ?: 0.0,
        feelsLike = mainDto?.feelsLike ?: 0.0,
        humidity = mainDto?.humidity ?: 0,
        pressure = mainDto?.pressure ?: 0,
        description = weather[0].description ?: "",
        icon = weather[0].icon ?: "",
        weatherMain = weather[0].main ?: "",
        visibility = visibility ?: 0,
        windSpeed = windDto?.speed ?: 0.0,
        windDirectionDeg = windDto?.deg ?: 0,
        clouds = cloudsDto?.all ?: 0,
        country = sysDto?.country ?: "",
        sunrise = sysDto?.sunrise ?: 0,
        sunset = sysDto?.sunset ?: 0,
        dt = dt ?: 0,
        timezone = timezone ?: 0,
        lang = lang,
        population = population
    )
}