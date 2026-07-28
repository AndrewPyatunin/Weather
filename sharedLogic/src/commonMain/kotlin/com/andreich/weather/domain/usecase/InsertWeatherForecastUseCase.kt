package com.andreich.weather.domain.usecase

import com.andreich.weather.domain.model.CityWeatherForecastItem
import com.andreich.weather.domain.repository.WeatherRepository

class InsertWeatherForecastUseCase(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(item: CityWeatherForecastItem) {
        return repository.insertWeatherForecast(item)
    }
}