package com.andreich.weather.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.weather.domain.model.RequestType

@Dao
interface CacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCacheData(cache: CacheEntity)

    @Query("SELECT * FROM cache_request WHERE :requestType = type")
    suspend fun getCacheData(requestType: RequestType): CacheEntity?
}