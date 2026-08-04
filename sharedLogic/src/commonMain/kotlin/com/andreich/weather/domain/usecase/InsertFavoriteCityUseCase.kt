package com.andreich.weather.domain.usecase

import com.andreich.weather.domain.model.FavoriteCity
import com.andreich.weather.domain.repository.WeatherRepository

class InsertFavoriteCityUseCase(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(item: FavoriteCity) {
        return repository.insertFavoriteCity(item)
    }
}