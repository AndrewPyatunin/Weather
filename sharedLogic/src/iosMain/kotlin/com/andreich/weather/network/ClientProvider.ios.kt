package com.andreich.weather.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json

actual class ClientProvider {

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

        private const val BASE_UNSPLASH_URL = "https://api.unsplash.com/"

        private const val API_KEY = "appid"
        private const val CLIENT_ID = "client_id"
    }


    actual fun createHttpClient(apiKey: String): HttpClient {
        return HttpClient(Darwin) {
            defaultRequest {
                url(BASE_URL)
                url {
                    parameters.append(API_KEY, apiKey)
                }
            }
            install(ContentNegotiation) {
                json()
            }
        }
    }

    actual fun createHttpCityImageClient(clientId: String): HttpClient {
        return HttpClient(Darwin) {
            defaultRequest {
                url(BASE_UNSPLASH_URL)
                url {
                    parameters.append(CLIENT_ID, clientId)
                }
            }
            install(ContentNegotiation) {
                json()
            }
        }
    }
}
