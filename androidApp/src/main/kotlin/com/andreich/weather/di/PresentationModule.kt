package com.andreich.weather.di

import com.andreich.weather.presentation.core.UiDataBuilder
import com.andreich.weather.presentation.weatherdetail.WeatherDetailsViewModel
import com.andreich.weather.presentation.weatherlist.WeatherListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import java.util.Locale

fun presentationModule(locale: Locale) = module {
    single {
        UiDataBuilder(locale)
    }
    viewModel<WeatherListViewModel> {
        WeatherListViewModel(get(), get(), get(), get())
    }
    viewModel<WeatherDetailsViewModel> {
        WeatherDetailsViewModel(get(), get(), get(), get(), get(), get(), get())
    }
}