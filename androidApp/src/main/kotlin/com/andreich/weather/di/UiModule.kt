package com.andreich.weather.di

import android.content.Context
import coil3.ImageLoader
import coil3.request.allowHardware
import coil3.request.crossfade
import org.koin.dsl.module

fun uiModule(context: Context) = module {
    single<ImageLoader> {
        ImageLoader.Builder(context)
            .allowHardware(false)
            .crossfade(true)
            .build()
    }
}