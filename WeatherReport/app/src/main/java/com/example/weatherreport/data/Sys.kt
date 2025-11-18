package com.example.weatherreport.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sys(
    @SerialName("country")
    var country: String?,
    @SerialName("id")
    var id: Int?,
    @SerialName("sunrise")
    var sunrise: Int?,
    @SerialName("sunset")
    var sunset: Int?,
    @SerialName("type")
    var type: Int?
)