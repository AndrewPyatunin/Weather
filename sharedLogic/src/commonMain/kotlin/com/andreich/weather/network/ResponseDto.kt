package com.andreich.weather.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseDto(

    @SerialName("coord") val coordDto: CoordDto? = CoordDto(),
    @SerialName("weather") val weather: ArrayList<WeatherDto> = arrayListOf(),
    @SerialName("base") val base: String? = null,
    @SerialName("main") val mainDto: MainDto? = MainDto(),
    @SerialName("visibility") val visibility: Int? = null,
    @SerialName("wind") val windDto: WindDto? = WindDto(),
    @SerialName("rain") val rainDto: RainDto? = RainDto(),
    @SerialName("clouds") val cloudsDto: CloudsDto? = CloudsDto(),
    @SerialName("dt") val dt: Int? = null,
    @SerialName("sys") val sysDto: SysDto? = SysDto(),
    @SerialName("timezone") val timezone: Int? = null,
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("cod") val cod: Int? = null
)
