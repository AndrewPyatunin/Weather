package com.andreich.weather.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteCity(city: FavoriteCityEntity)

    @Query("SELECT * FROM favorite_city ORDER BY time DESC")
    fun getFavorites(): Flow<List<FavoriteCityEntity>>
}