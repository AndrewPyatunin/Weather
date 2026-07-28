package com.andreich.weather.domain.usecase

import com.andreich.weather.domain.model.RequestResult
import com.andreich.weather.domain.repository.WeatherRepository

class UpdateWeatherForecastUseCase(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(id: Int): RequestResult {
        return repository.updateWeatherForecast(id)
    }
}