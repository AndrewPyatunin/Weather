package com.andreich.weather.di

import android.content.Context
import com.andreich.weather.data.datasource.CityDatasource
import com.andreich.weather.data.repository.WeatherRepositoryImpl
import com.andreich.weather.domain.repository.WeatherRepository
import org.koin.dsl.module

fun dataModule(context: Context) = module {
    single<WeatherRepository> {
        WeatherRepositoryImpl(get(), get(), get(), get())
    }
    single(createdAtStart = true) {
        CityDatasource(context)
    }
}