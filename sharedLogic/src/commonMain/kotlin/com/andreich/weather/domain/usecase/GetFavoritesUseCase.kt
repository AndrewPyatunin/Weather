package com.andreich.weather.domain.usecase

import com.andreich.weather.domain.model.FavoriteCity
import com.andreich.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(
    private val repository: WeatherRepository
) {

    operator fun invoke(): Flow<List<FavoriteCity>> {
        return repository.getFavorites()
    }
}