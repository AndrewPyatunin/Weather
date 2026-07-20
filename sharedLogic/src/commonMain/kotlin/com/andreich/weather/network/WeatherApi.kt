package com.andreich.weather.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class WeatherApi(
    private val client: HttpClient
) {

    companion object {
        const val CURRENT_WEATHER = "data/2.5/weather"

        const val UNITS = "units"
        const val METRIC = "metric"
        const val LANG = "lang"
        const val RU = "ru"
        const val EN = "en"
        const val QUERY = "q"

        const val LAT = "lat"
        const val LON = "lon"
    }

    suspend fun getWeather(lang: String = RU, lat: Double, lon: Double): ResponseDto {
        return client.get {
            url {
                path(CURRENT_WEATHER)
                parameters.apply {
                    append(UNITS, METRIC)
                    append(LANG, lang)
                    append(LAT, lat.toString())
                    append(LON, lon.toString())
                }
            }
        }.body()
    }

    suspend fun getWeatherForCity(name: String, lang: String = RU): ResponseDto {
        return client.get {
            url {
                path(CURRENT_WEATHER)
                parameters.apply {
                    append(QUERY, name)
                    append(UNITS, METRIC)
                    append(LANG, lang)
                }
            }
        }.body()
    }
}