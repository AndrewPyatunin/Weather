package com.andreich.weather.data.repository

import com.andreich.weather.data.datasource.CityDatasource
import com.andreich.weather.data.mapper.toCityWeather
import com.andreich.weather.data.mapper.toCityWeatherEntity
import com.andreich.weather.data.mapper.toCityWeatherItem
import com.andreich.weather.database.WeatherDao
import com.andreich.weather.domain.model.CityWeather
import com.andreich.weather.domain.model.CityWeatherItem
import com.andreich.weather.domain.model.RequestResult
import com.andreich.weather.domain.repository.WeatherRepository
import com.andreich.weather.network.WeatherApi
import com.andreich.weather.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val cityDatasource: CityDatasource,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override fun getCitiesList(lang: String, country: String): Flow<List<CityWeatherItem>> {
        return weatherDao.getCitiesWeather(lang, country, null)
            .map { list -> list.map { it.toCityWeatherItem() } }
    }

    override fun getCityDetails(id: Int): Flow<CityWeather> {
        return weatherDao.getCityWeather(id).map { it.toCityWeather() }
    }

    override fun searchCity(name: String, lang: String, country: String): Flow<List<CityWeatherItem>> {
        return weatherDao.getCitiesWeather(lang, country, name).map { list -> list.map { it.toCityWeatherItem() } }
    }

    private suspend fun updateCitiesList(lang: String, country: String) {
        getRequestResult { getCitiesApiCall(lang, country) }
    }

    private suspend fun getCitiesApiCall(lang: String, country: String): List<CityWeather> {
        val text = when (lang) {
            "ru" -> {
                cityDatasource.buildCitiesData()
            }

            "en" -> {
                cityDatasource.buildCitiesData().filter { it.country == country }
            }

            else -> emptyList()
        }
        return text.map {
            weatherApi.getWeather(lang, it.lat, it.lng)
                .toCityWeather(it.id, it.name, lang, it.population)
        }
    }

    private suspend fun getRequestResult(apiCall: suspend () -> List<CityWeather>): RequestResult {
        return safeApiCall(apiCall) { list ->
            weatherDao.addCitiesWeatherList(list.map { it.toCityWeatherEntity() })
        }
    }
}