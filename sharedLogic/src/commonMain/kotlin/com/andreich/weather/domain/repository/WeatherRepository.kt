package com.andreich.weather.domain.repository

import com.andreich.weather.domain.model.CityWeather
import com.andreich.weather.domain.model.CityWeatherItem
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getCitiesList(lang: String, country: String): Flow<List<CityWeatherItem>>

    fun getCityDetails(id: Int): Flow<CityWeather>

    fun searchCity(name: String, lang: String, country: String): Flow<List<CityWeatherItem>>
}