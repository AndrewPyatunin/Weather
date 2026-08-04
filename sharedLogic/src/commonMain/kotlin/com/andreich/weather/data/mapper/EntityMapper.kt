package com.andreich.weather.data.mapper

import com.andreich.weather.database.CityImageEntity
import com.andreich.weather.database.CityWeatherEntity
import com.andreich.weather.database.CityWeatherForecastEntity
import com.andreich.weather.database.FavoriteCityEntity
import com.andreich.weather.domain.model.CityImage
import com.andreich.weather.domain.model.CityWeather
import com.andreich.weather.domain.model.CityWeatherForecastItem
import com.andreich.weather.domain.model.CityWeatherItem
import com.andreich.weather.domain.model.FavoriteCity

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

fun CityImage.toCityImageEntity(): CityImageEntity {
    return CityImageEntity(
        id = id,
        name = name,
        country = country,
        regularImage = regular,
        thumbImage = thumb
    )
}

fun CityImageEntity.toCityImage(): CityImage {
    return CityImage(
        id, name, country, regularImage, thumbImage
    )
}

fun CityWeatherForecastEntity.toCityWeatherForecastItem(): CityWeatherForecastItem {
    return CityWeatherForecastItem(
        id, cityName, weatherForecastList, timezone
    )
}

fun CityWeatherForecastItem.toCityWeatherForecastEntity(): CityWeatherForecastEntity {
    return CityWeatherForecastEntity(
        id, cityName, forecastWeather, timezone
    )
}

fun FavoriteCityEntity.toFavoriteCity(): FavoriteCity {
    return FavoriteCity(id, name)
}

fun FavoriteCity.toFavoriteCityEntity(): FavoriteCityEntity {
    return FavoriteCityEntity(id, name)
}