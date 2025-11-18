package com.example.weatherreport.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDAO {

    @Query("SELECT * FROM cityListTable")
    fun getAllCities(): Flow<List<CityItem>>

    @Query("SELECT * from cityListTable WHERE id = :id")
    fun getCity(id: Int): Flow<CityItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: CityItem)

    @Update
    suspend fun update(todo: CityItem)

    @Delete
    suspend fun delete(todo: CityItem)
}