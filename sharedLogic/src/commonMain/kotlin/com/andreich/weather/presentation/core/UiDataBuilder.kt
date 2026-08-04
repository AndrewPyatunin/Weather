package com.andreich.weather.presentation.core

import com.andreich.weather.domain.model.Weather
import com.andreich.weather.presentation.weatherdetail.ForecastWeatherItem

expect class UiDataBuilder {

    fun makeForecastWeatherItemList(weatherList: List<Weather>, timezone: Int): List<ForecastWeatherItem>
}