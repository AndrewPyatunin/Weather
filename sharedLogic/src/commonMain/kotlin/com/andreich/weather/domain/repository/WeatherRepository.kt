package com.andreich.weather.domain.repository

import com.andreich.weather.domain.model.CityWeather
import com.andreich.weather.domain.model.CityWeatherItem
import com.andreich.weather.domain.model.RequestResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getCitiesList(lang: String, country: String): Flow<List<CityWeatherItem>>

    fun getCityDetails(id: Int): Flow<CityWeather>

    fun searchCity(name: String, lang: String, country: String): Flow<List<CityWeatherItem>>

    suspend fun updateCitiesList(lang: String, country: String): RequestResult

    suspend fun updateCityWeatherInfo(name: String, lang: String): RequestResult
}