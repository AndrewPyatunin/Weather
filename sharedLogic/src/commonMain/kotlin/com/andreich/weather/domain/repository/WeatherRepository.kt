package com.andreich.weather.domain.repository

import com.andreich.weather.domain.model.CityWeather
import com.andreich.weather.domain.model.CityImage
import com.andreich.weather.domain.model.CityWeatherItem
import com.andreich.weather.domain.model.RequestResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getCitiesList(lang: String, country: String): Flow<List<CityWeatherItem>>

    fun getCityDetails(id: Int): Flow<CityWeather>
    fun getCitiesImages(country: String): Flow<List<CityImage>>

    fun searchCity(name: String, lang: String, country: String): Flow<List<CityWeatherItem>>

    fun getCityImage(id: Int): Flow<CityImage>

    suspend fun updateCitiesList(lang: String, country: String): RequestResult

    suspend fun updateCityWeatherInfo(name: String, lang: String): RequestResult

    suspend fun updateCitiesImages(country: String): RequestResult

    suspend fun updateCityImage(id: Int): RequestResult
}