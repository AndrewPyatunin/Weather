package com.andreich.weather.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class CityImageApi(
    private val client: HttpClient
) {
    companion object {
        private const val SEARCH_PHOTOS = "search/photos"
        private const val QUERY = "query"
        private const val PER_PAGE = "per_page"
        private const val PAGE = "page"
        private const val ORIENTATION = "orientation"
        private const val PORTRAIT = "portrait"

        private const val LANDSCAPE = "landscape"
    }

    private fun String.normalizeCityName(): String =
        trim()
            .replace("'", "")
            .replace(Regex("\\s+"), " ")

    suspend fun getCityImage(query: String): ResponseUnsplashDto {
        return client.get {
            url {
                path(SEARCH_PHOTOS)
                parameters.apply {
                    append(QUERY, query.normalizeCityName())
                    append(ORIENTATION, PORTRAIT)
                    append(PER_PAGE, "10")
                    append(PAGE, "1")
                }
            }
        }.body()
    }
}