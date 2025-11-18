package com.example.weatherreport.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "cityListTable")
data class CityItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "description") val description:String,
)