package com.andreich.weather.di

import com.andreich.weather.domain.usecase.GetCityListUseCase
import com.andreich.weather.domain.usecase.GetCityWeatherInfoUseCase
import com.andreich.weather.domain.usecase.SearchCityUseCase
import com.andreich.weather.domain.usecase.UpdateCityWeatherListUseCase
import com.andreich.weather.domain.usecase.UpdateSearchCityWeatherUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        GetCityListUseCase(get())
    }
    factory {
        GetCityWeatherInfoUseCase(get())
    }
    factory {
        SearchCityUseCase(get())
    }
    factory {
        UpdateCityWeatherListUseCase(get())
    }
    factory {
        UpdateSearchCityWeatherUseCase(get())
    }
}