package com.andreich.weather.domain.usecase

import com.andreich.weather.domain.model.CityWeatherItem
import com.andreich.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class SearchCityUseCase(
    private val repository: WeatherRepository
) {

    operator fun invoke(name: String, lang: String, country: String): Flow<List<CityWeatherItem>> {
        return repository.searchCity(name, lang, country)
    }
}