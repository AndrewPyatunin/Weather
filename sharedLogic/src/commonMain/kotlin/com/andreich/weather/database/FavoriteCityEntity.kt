package com.andreich.weather.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock

@Entity(tableName = "favorite_city")
data class FavoriteCityEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val time: Long = Clock.System.now().epochSeconds
)