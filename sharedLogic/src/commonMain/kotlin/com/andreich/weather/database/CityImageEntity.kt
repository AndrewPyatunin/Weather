package com.andreich.weather.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_image")
data class CityImageEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val country: String,
    val regularImage: String,
    val thumbImage: String
)