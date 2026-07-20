package com.andreich.weather

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform