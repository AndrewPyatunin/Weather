package com.andreich.weather.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherListDto(
    val dt: Int? = null,
    val main: MainDto? = MainDto(),
    val weather: ArrayList<WeatherDto> = arrayListOf(),
    val clouds: CloudsDto? = CloudsDto(),
    val wind: WindDto? = WindDto(),
    val visibility: Int? = null,
    val pop: Double? = null,
    val rain: RainDto? = RainDto(),
    val sys: SysDto? = SysDto(),
    @SerialName("dt_txt") val dtTxt: String? = null
)
