package com.andreich.weather.core

import android.util.Log

actual class WeatherLogger {
    actual fun log(tag: String, message: String) {
        Log.d(tag, message)
    }
}