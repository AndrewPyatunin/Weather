package com.andreich.weather.domain.usecase

import com.andreich.weather.domain.model.RequestResult
import com.andreich.weather.domain.repository.WeatherRepository

class UpdateSearchCityWeatherUseCase(
    val repository: WeatherRepository
) {

    suspend operator fun invoke(name: String, lang: String): RequestResult {
        return repository.updateCityWeatherInfo(name, lang)
    }
}