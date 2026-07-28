package com.andreich.weather.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CityImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCityImageList(list: List<CityImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCityImage(cityImageEntity: CityImageEntity)

    @Query("SELECT * FROM city_image WHERE :id = id LIMIT 1")
    fun getCityImage(id: Int): Flow<CityImageEntity>

    @Query("SELECT * FROM city_image WHERE :country = country")
    fun getCountryCitiesImages(country: String): Flow<List<CityImageEntity>>
}