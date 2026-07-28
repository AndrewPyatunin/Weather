package com.andreich.weather.domain.usecase

import com.andreich.weather.domain.model.RequestResult
import com.andreich.weather.domain.repository.WeatherRepository

class UpdateCityImageUseCase(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(id: Int): RequestResult {
        return repository.updateCityImage(id)
    }
}