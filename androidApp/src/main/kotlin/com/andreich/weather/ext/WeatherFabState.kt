package com.andreich.weather.ext

import kotlinx.coroutines.Job

class WeatherFabState(
    val visible: Boolean,
    val onClick: () -> Job
)