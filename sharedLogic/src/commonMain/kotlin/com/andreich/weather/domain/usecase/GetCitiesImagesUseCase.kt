package com.andreich.weather.domain.usecase

import com.andreich.weather.domain.model.CityImage
import com.andreich.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetCitiesImagesUseCase(
    private val repository: WeatherRepository
) {

    operator fun invoke(country: String): Flow<List<CityImage>> {
        return repository.getCitiesImages(country)
    }
}