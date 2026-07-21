package com.andreich.weather.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andreich.weather.domain.model.RequestType
import kotlin.time.Clock

@Entity(tableName = "cache_request")
data class CacheEntity(
    @PrimaryKey
    val type: RequestType,
    val time: Long = Clock.System.now().epochSeconds,
)
