package com.andreich.weather.data.repository

import com.andreich.weather.data.datasource.CityDatasource
import com.andreich.weather.data.datasource.CityDto
import com.andreich.weather.data.mapper.toCityWeather
import com.andreich.weather.data.mapper.toCityWeatherEntity
import com.andreich.weather.data.mapper.toCityWeatherItem
import com.andreich.weather.database.CacheDao
import com.andreich.weather.database.CacheEntity
import com.andreich.weather.database.WeatherDao
import com.andreich.weather.domain.model.CityWeather
import com.andreich.weather.domain.model.CityWeatherItem
import com.andreich.weather.domain.model.RequestResult
import com.andreich.weather.domain.model.RequestType
import com.andreich.weather.domain.repository.WeatherRepository
import com.andreich.weather.network.WeatherApi
import com.andreich.weather.network.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val cityDatasource: CityDatasource,
    private val weatherDao: WeatherDao,
    private val cacheDao: CacheDao
) : WeatherRepository {

    private val CACHE_EXPIRED = 600L

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

    override suspend fun updateCitiesList(lang: String, country: String): RequestResult {
        val currentTime = Clock.System.now().epochSeconds
        val type = if (lang == "ru") RequestType.WeatherRequestRu(country) else RequestType.WeatherRequestEn(country)
        val cacheData = cacheDao.getCacheData(type)
        cacheData?.time?.let { if (currentTime - it < CACHE_EXPIRED) return RequestResult.Success }
        return getRequestResult(type) { getCitiesApiCall(lang, country) }
    }

    override suspend fun updateCityWeatherInfo(name: String, lang: String): RequestResult {
        val currentTime = Clock.System.now().epochSeconds
        val type = if (lang == "ru") RequestType.SearchRequestRu(name) else RequestType.SearchRequestEn(name)
        val cacheData = cacheDao.getCacheData(type)
        cacheData?.time?.let { if (currentTime - it < CACHE_EXPIRED) return RequestResult.Success }
        return getRequestResult(type) { searchCityApiCall(lang, name) }
    }

    private suspend fun searchCityApiCall(lang: String, name: String): List<CityWeather> {
        return getCities(lang = lang, name = name).map {
            weatherApi.getWeatherForCity(name, lang).toCityWeather(it.id, name, lang, it.population)
        }
    }

    private suspend fun getCities(lang: String, name: String = "", country: String? = null): List<CityDto> {
        return when (lang) {
            "ru" -> {
                cityDatasource.buildCitiesData()
            }

            "en" -> {
                cityDatasource.buildCitiesData().filter { it.name.contains(name) }.apply {
                    country?.let { filter { it.country == country } }
                }
            }

            else -> emptyList()
        }
    }
    private suspend fun getCitiesApiCall(lang: String, country: String): List<CityWeather> {
        return getCities(lang = lang, country = country).map {
            weatherApi.getWeather(lang, it.lat, it.lng)
                .toCityWeather(it.id, it.name, lang, it.population)
        }
    }

    private suspend fun getRequestResult(type: RequestType, apiCall: suspend () -> List<CityWeather>): RequestResult {
        return safeApiCall(apiCall) { list ->
            cacheDao.insertCacheData(CacheEntity(type))
            weatherDao.addCitiesWeatherList(list.map { it.toCityWeatherEntity() })
        }
    }
}