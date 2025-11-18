package com.example.weatherreport.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Main(
    @SerialName("feels_like")
    var feelsLike: Double?,
    @SerialName("grnd_level")
    var grndLevel: Int?,
    @SerialName("humidity")
    var humidity: Int?,
    @SerialName("pressure")
    var pressure: Int?,
    @SerialName("sea_level")
    var seaLevel: Int?,
    @SerialName("temp")
    var temp: Double?,
    @SerialName("temp_max")
    var tempMax: Double?,
    @SerialName("temp_min")
    var tempMin: Double?
)