package com.andreich.weather.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteCity(
    val id: Int,
    val name: String
)