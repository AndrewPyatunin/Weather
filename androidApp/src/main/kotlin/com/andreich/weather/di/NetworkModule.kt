package com.andreich.weather.di

import com.andreich.weather.network.CityImageApi
import com.andreich.weather.network.ClientProvider
import com.andreich.weather.network.WeatherApi
import io.ktor.client.HttpClient
import org.koin.dsl.module

fun networkModule(apiKey: String, clientId: String) = module {
    single<HttpClient> {
        ClientProvider().createHttpClient(apiKey)
    }
    single {
        WeatherApi(get())
    }
    single {
        CityImageApi(ClientProvider().createHttpCityImageClient(clientId))
    }
}