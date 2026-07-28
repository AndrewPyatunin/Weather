package com.andreich.weather.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(

    @SerialName("coord") val coordDto: CoordDto? = CoordDto(),
    val weather: ArrayList<WeatherDto> = arrayListOf(),
    val base: String? = null,
    @SerialName("main") val mainDto: MainDto? = MainDto(),
    val visibility: Int? = null,
    @SerialName("wind") val windDto: WindDto? = WindDto(),
    @SerialName("rain") val rainDto: RainDto? = RainDto(),
    @SerialName("clouds") val cloudsDto: CloudsDto? = CloudsDto(),
    val dt: Int? = null,
    @SerialName("sys") val sysDto: SysDto? = SysDto(),
    val timezone: Int? = null,
    val id: Int? = null,
    val name: String? = null,
    val cod: Int? = null
)
