package com.andreich.weather.domain.usecase

import com.andreich.weather.domain.model.RequestResult
import com.andreich.weather.domain.repository.WeatherRepository

class UpdateCityWeatherListUseCase(
    val repository: WeatherRepository
) {
    suspend operator fun invoke(lang: String, country: String): RequestResult {
        return repository.updateCitiesList(lang, country)
    }
}