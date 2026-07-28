package com.andreich.weather.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CityImage(
    val id: Int,
    val name: String,
    val country: String,
    val regular: String,
    val thumb: String
)
