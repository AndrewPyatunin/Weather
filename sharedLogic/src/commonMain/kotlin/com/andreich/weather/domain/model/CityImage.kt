package com.andreich.weather.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class CityImage(
    val id: Int,
    val name: String,
    val country: String,
    val regular: String,
    val thumb: String
)
