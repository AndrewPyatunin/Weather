package com.andreich.weather.domain.usecase

import com.andreich.weather.domain.model.CityWeather
import com.andreich.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetCityWeatherInfoUseCase(
    private val repository: WeatherRepository
) {

    operator fun invoke(id: Int): Flow<CityWeather> {
        return repository.getCityDetails(id)
    }
}