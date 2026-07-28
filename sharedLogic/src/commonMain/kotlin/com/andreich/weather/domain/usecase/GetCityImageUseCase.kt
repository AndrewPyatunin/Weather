package com.andreich.weather.domain.usecase

import com.andreich.weather.domain.model.CityImage
import com.andreich.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetCityImageUseCase(
    private val repository: WeatherRepository
) {

    operator fun invoke(id: Int): Flow<CityImage> {
        return repository.getCityImage(id)
    }
}