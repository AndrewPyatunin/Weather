package com.andreich.weather

import android.app.Application
import com.andreich.weather.di.dataModule
import com.andreich.weather.di.databaseModule
import com.andreich.weather.di.domainModule
import com.andreich.weather.di.networkModule
import com.andreich.weather.di.presentationModule
import com.andreich.weather.di.uiModule
import org.koin.core.context.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val apiKey = BuildConfig.API_KEY
        val clientId = BuildConfig.CLIENT_ID
        startKoin {
            modules(
                uiModule(applicationContext),
                presentationModule,
                networkModule(apiKey, clientId),
                domainModule,
                dataModule(applicationContext),
                databaseModule(applicationContext)
            )
        }
    }
}