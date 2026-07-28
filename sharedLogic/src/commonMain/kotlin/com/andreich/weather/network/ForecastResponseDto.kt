package com.andreich.weather.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastResponseDto(
    val cod: String? = null,
    val message: Int? = null,
    val cnt: Int? = null,
    @SerialName("list") val weatherList: ArrayList<WeatherListDto> = arrayListOf(),
    @SerialName("city") val city: CityDto? = CityDto()
)
