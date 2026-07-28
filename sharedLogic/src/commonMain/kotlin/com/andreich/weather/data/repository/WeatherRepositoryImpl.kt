package com.andreich.weather.data.repository

import com.andreich.weather.core.WeatherLogger
import com.andreich.weather.data.datasource.CityDatasource
import com.andreich.weather.data.datasource.CityDto
import com.andreich.weather.data.mapper.toCityImage
import com.andreich.weather.data.mapper.toCityImageEntity
import com.andreich.weather.data.mapper.toCityWeather
import com.andreich.weather.data.mapper.toCityWeatherEntity
import com.andreich.weather.data.mapper.toCityWeatherForecastEntity
import com.andreich.weather.data.mapper.toCityWeatherForecastItem
import com.andreich.weather.data.mapper.toCityWeatherItem
import com.andreich.weather.database.CacheDao
import com.andreich.weather.database.CacheEntity
import com.andreich.weather.database.CityImageDao
import com.andreich.weather.database.ForecastWeatherDao
import com.andreich.weather.database.WeatherDao
import com.andreich.weather.domain.model.CityImage
import com.andreich.weather.domain.model.CityWeather
import com.andreich.weather.domain.model.CityWeatherForecastItem
import com.andreich.weather.domain.model.CityWeatherItem
import com.andreich.weather.domain.model.RequestResult
import com.andreich.weather.domain.model.RequestType
import com.andreich.weather.domain.repository.WeatherRepository
import com.andreich.weather.network.CityImageApi
import com.andreich.weather.network.WeatherApi
import com.andreich.weather.network.safeApiCall
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlin.collections.map
import kotlin.time.Clock

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val cityImageApi: CityImageApi,
    private val cityDatasource: CityDatasource,
    private val cityImageDao: CityImageDao,
    private val weatherDao: WeatherDao,
    private val forecastWeatherDao: ForecastWeatherDao,
    private val cacheDao: CacheDao
) : WeatherRepository {

    private val CACHE_EXPIRED = 900L

    override fun getCitiesList(lang: String, country: String): Flow<List<CityWeatherItem>> {
        return weatherDao.getCitiesWeather(lang, country, null)
            .map { list -> list.map { it.toCityWeatherItem() } }
    }

    override fun getCityDetails(id: Int): Flow<CityWeatherForecastItem> {
        return forecastWeatherDao.getForecastWeather(id).map { it.toCityWeatherForecastItem() }/*weatherDao.getCityWeather(id).map { it.toCityWeather() }*/
    }

    override fun getCitiesImages(country: String): Flow<List<CityImage>> {
        return cityImageDao.getCountryCitiesImages(country)
            .map { list -> list.map { it.toCityImage() } }
    }

    override fun searchCity(
        name: String,
        lang: String,
        country: String
    ): Flow<List<CityWeatherItem>> {
        return weatherDao.getCitiesWeather(lang, country, name)
            .map { list -> list.map { it.toCityWeatherItem() } }
    }

    override fun getCityImage(id: Int): Flow<CityImage> {
        return cityImageDao.getCityImage(id).map { it.toCityImage() }
    }

    override suspend fun insertWeatherForecast(item: CityWeatherForecastItem) {
        return forecastWeatherDao.insertForecastWeather(item.toCityWeatherForecastEntity()).apply {
        }
    }

    override suspend fun updateCitiesList(lang: String, country: String): RequestResult {
        val currentTime = Clock.System.now().epochSeconds
        val type =
            if (lang == "ru") RequestType.WeatherRequestRu(country) else RequestType.WeatherRequestEn(
                country
            )
        val cacheData = cacheDao.getCacheData(type)
        cacheData?.time?.let { if (currentTime - it < CACHE_EXPIRED) return RequestResult.Undefined }
        return getRequestResult(type) { getCitiesApiCall(lang, country) }
    }

    override suspend fun updateWeatherForecast(
        id: Int
    ): RequestResult {
        val city = getCities().find { it.id == id }
        if (city == null) return RequestResult.Failure.NotFound("There is no city with this id")
        val currentTime = Clock.System.now().epochSeconds
        val type = RequestType.WeatherForecastRequest(city.name, city.country)
        val cacheData = cacheDao.getCacheData(type)
        cacheData?.time?.let { if (currentTime - it < CACHE_EXPIRED) return RequestResult.Undefined }
        return getForecastWeatherApiCall(city.name, city.country, id)
    }

    override suspend fun updateCityWeatherInfo(name: String, lang: String): RequestResult {
        val currentTime = Clock.System.now().epochSeconds
        val type =
            if (lang == "ru") RequestType.SearchRequestRu(name) else RequestType.SearchRequestEn(
                name
            )
        val cacheData = cacheDao.getCacheData(type)
        cacheData?.time?.let { if (currentTime - it < CACHE_EXPIRED) return RequestResult.Undefined }
        return getRequestResult(type) { searchCityApiCall(lang, name) }
    }

    override suspend fun updateCitiesImages(country: String): RequestResult {
        val type = RequestType.CitiesImagesForCountryRequest(country)
        val cacheData = cacheDao.getCacheData(type)
        cacheData?.let { return RequestResult.Undefined }
        return getCityImagesApiCall(country)
    }

    override suspend fun updateCityImage(id: Int): RequestResult {
        val type = RequestType.CityImageRequest(id)
        val cacheData = cacheDao.getCacheData(type)
        cacheData?.let { return RequestResult.Undefined }
        return getCityImageApiCall(id)
    }

    private suspend fun searchCityApiCall(lang: String, name: String): List<CityWeather> {
        return getCities().map {
            weatherApi.getWeatherForCity(name, lang).toCityWeather(it.id, name, lang, it.population)
        }
    }

    private suspend fun getCities(country: String = "ru"): List<CityDto> {
        return cityDatasource.buildCitiesData().filter { it.iso2.lowercase() == country }
            .sortedByDescending { it.population }.take(60)
    }

    private suspend fun getCityImagesApiCall(country: String): RequestResult {
        return safeApiCall(apiCall = {
            val cities = getCities(country)
            val images = cityImageApi.getCityImage(cities.first().name)
            if (images.results.isNotEmpty())
                cities.mapNotNull { city ->
                    images.results[0].urls?.toCityImage(
                        city.id,
                        city.name,
                        country
                    )
                }
            else emptyList()
            }) {
            putCityImageIntoDatabase(country, cityImages = it)
        }
    }

    private suspend fun getCityImageApiCall(id: Int): RequestResult {
        return safeApiCall(apiCall = {
            val cityImage = cityImageDao.getCityImage(id).first()
            val image = cityImageApi.getCityImage(cityImage.name)
            if (image.results.isNotEmpty()) {
                image.results[0].urls?.toCityImage(id, cityImage.name, cityImage.country)
            } else null
        }) {
            it?.let {
                cacheDao.insertCacheData(CacheEntity(RequestType.CityImageRequest(id)))
                cityImageDao.insertCityImage(it.toCityImageEntity())
            }

        }
    }

    private suspend fun putCityImageIntoDatabase(country: String, cityImages: List<CityImage>) {
        cacheDao.insertCacheData(CacheEntity(RequestType.CitiesImagesForCountryRequest(country)))
        cityImageDao.insertCityImageList(cityImages.map { it.toCityImageEntity() })
    }

    private suspend fun getCitiesApiCall(lang: String, country: String): List<CityWeather> =
        coroutineScope {
            val weatherLogger = WeatherLogger()
            getCities(country = country).map { city ->
                weatherLogger.log("WEATHER_API_START", city.name)
                async {
                    runCatching {
                        weatherApi.getWeatherForCity(lang = lang, name = city.name)
                            .toCityWeather(city.id, city.name, lang, city.population)
                    }.onFailure {
                        weatherLogger.log("WEATHER_ERROR", it.stackTraceToString())
                    }.getOrNull()
                }
            }.awaitAll()
                .filterNotNull()
        }

    private suspend fun getForecastWeatherApiCall(name: String, country: String, id: Int): RequestResult {
        return safeApiCall(apiCall = {
            weatherApi.getForecastWeatherForCity(name, country).toCityWeatherForecastEntity(id, name)
        }) {
            cacheDao.insertCacheData(CacheEntity(RequestType.WeatherForecastRequest(name, country)))
            forecastWeatherDao.insertForecastWeather(it)
        }
    }

    private suspend fun getRequestResult(
        type: RequestType,
        apiCall: suspend () -> List<CityWeather>
    ): RequestResult {
        return safeApiCall(apiCall) { list ->
            cacheDao.insertCacheData(CacheEntity(type))
            weatherDao.addCitiesWeatherList(list.map { it.toCityWeatherEntity() })
        }
    }
}