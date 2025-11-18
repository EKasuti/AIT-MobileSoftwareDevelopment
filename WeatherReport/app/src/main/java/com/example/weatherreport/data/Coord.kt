package com.example.weatherreport.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coord(
    @SerialName("lat")
    var lat: Double?,
    @SerialName("lon")
    var lon: Double?
)