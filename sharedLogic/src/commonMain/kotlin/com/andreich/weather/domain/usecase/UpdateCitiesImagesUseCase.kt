package com.andreich.weather.domain.usecase

import com.andreich.weather.domain.model.RequestResult
import com.andreich.weather.domain.repository.WeatherRepository

class UpdateCitiesImagesUseCase(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(country: String): RequestResult {
        return repository.updateCitiesImages(country = country)
    }
}