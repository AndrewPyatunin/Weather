package com.andreich.weather.core

expect class WeatherLogger() {
    fun log(tag: String, message: String)
}