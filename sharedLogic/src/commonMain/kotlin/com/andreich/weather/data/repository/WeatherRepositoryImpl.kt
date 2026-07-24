package com.andreich.weather.data.repository

import com.andreich.weather.core.WeatherLogger
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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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
        return getCities(lang = lang).map {
            weatherApi.getWeatherForCity(name, lang).toCityWeather(it.id, name, lang, it.population)
        }
    }

    private suspend fun getCities(lang: String, country: String = "ru"): List<CityDto> {
        return when (lang) {
            "ru" -> {
                cityDatasource.buildCitiesData().filter { it.iso2.lowercase() == country }.sortedByDescending { it.population }.take(60).apply {
                    WeatherLogger().log("WEATHER_build_list", this.toString())
                }
            }

            "en" -> {
                cityDatasource.buildCitiesData().filter { it.iso2.lowercase() == "us" }.sortedByDescending { it.population }.take(60)
            }

            else -> emptyList()
        }
    }
    private suspend fun getCitiesApiCall(lang: String, country: String): List<CityWeather> =
        coroutineScope {
            val weatherLogger = WeatherLogger()
            getCities(lang = lang, country = country).map { city ->
                weatherLogger.log("WEATHER_API_START", city.name)
                async {
                    runCatching {
//                        weatherApi.getWeather(lang = lang, lat = city.lat, lon = city.lng).toCityWeather(city.id, city.name, lang, city.population)
                            weatherApi.getWeatherForCity(lang = lang, name = city.name)
                                .toCityWeather(city.id, city.name, lang, city.population)
                    }.onFailure {
                        weatherLogger.log("WEATHER_ERROR", it.stackTraceToString())
                    }.getOrNull()
                }
            }.awaitAll()
                .filterNotNull()
        }

    private suspend fun getRequestResult(type: RequestType, apiCall: suspend () -> List<CityWeather>): RequestResult {
        return safeApiCall(apiCall) { list ->
            WeatherLogger().log("WEATHER_LIST_TO_DB", list.toString())
            cacheDao.insertCacheData(CacheEntity(type))
            weatherDao.addCitiesWeatherList(list.map { it.toCityWeatherEntity() })
        }
    }
}