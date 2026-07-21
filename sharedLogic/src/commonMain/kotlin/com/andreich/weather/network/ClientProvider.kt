package com.andreich.weather.network

import io.ktor.client.HttpClient

expect class ClientProvider {

    fun createHttpClient(apiKey: String): HttpClient
}