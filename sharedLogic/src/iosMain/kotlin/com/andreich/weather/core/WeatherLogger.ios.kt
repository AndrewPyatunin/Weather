package com.andreich.weather.core

actual class WeatherLogger {
    actual fun log(tag: String, message: String) {
        print(message)
    }
}