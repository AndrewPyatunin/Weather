package com.andreich.weather.ui.core

import android.content.Context
import android.graphics.Bitmap
import coil3.request.ImageRequest
import coil3.request.bitmapConfig

class ImageRequestFactory(private val context: Context) {

    fun create(url: String): ImageRequest {
        return ImageRequest.Builder(context)
            .data(url)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .build()
    }
}