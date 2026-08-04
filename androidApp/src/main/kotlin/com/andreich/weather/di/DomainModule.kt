package com.andreich.weather.di

import com.andreich.weather.domain.usecase.GetCitiesImagesUseCase
import com.andreich.weather.domain.usecase.GetCityImageUseCase
import com.andreich.weather.domain.usecase.GetCityListUseCase
import com.andreich.weather.domain.usecase.GetCityWeatherInfoUseCase
import com.andreich.weather.domain.usecase.GetFavoritesUseCase
import com.andreich.weather.domain.usecase.InsertFavoriteCityUseCase
import com.andreich.weather.domain.usecase.SearchCityUseCase
import com.andreich.weather.domain.usecase.UpdateCitiesImagesUseCase
import com.andreich.weather.domain.usecase.UpdateCityImageUseCase
import com.andreich.weather.domain.usecase.UpdateCityWeatherListUseCase
import com.andreich.weather.domain.usecase.UpdateSearchCityWeatherUseCase
import com.andreich.weather.domain.usecase.UpdateWeatherForecastUseCase
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
    factory {
        GetCityImageUseCase(get())
    }
    factory {
        UpdateCitiesImagesUseCase(get())
    }
    factory {
        GetCitiesImagesUseCase(get())
    }
    factory {
        UpdateCityImageUseCase(get())
    }
    factory {
        UpdateWeatherForecastUseCase(get())
    }
    factory {
        InsertFavoriteCityUseCase(get())
    }
    factory {
        GetFavoritesUseCase(get())
    }
}