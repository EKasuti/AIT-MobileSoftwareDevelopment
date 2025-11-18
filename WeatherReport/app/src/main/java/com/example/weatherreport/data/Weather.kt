package com.example.weatherreport.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    @SerialName("description")
    var description: String?,
    @SerialName("icon")
    var icon: String?,
    @SerialName("id")
    var id: Int?,
    @SerialName("main")
    var main: String?
)