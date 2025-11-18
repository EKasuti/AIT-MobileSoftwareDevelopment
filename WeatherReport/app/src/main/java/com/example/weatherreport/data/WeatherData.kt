package com.example.weatherreport.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    @SerialName("base")
    var base: String?,
    @SerialName("clouds")
    var clouds: Clouds?,
    @SerialName("cod")
    var cod: Int?,
    @SerialName("coord")
    var coord: Coord?,
    @SerialName("dt")
    var dt: Int?,
    @SerialName("id")
    var id: Int?,
    @SerialName("main")
    var main: Main?,
    @SerialName("name")
    var name: String?,
    @SerialName("sys")
    var sys: Sys?,
    @SerialName("timezone")
    var timezone: Int?,
    @SerialName("visibility")
    var visibility: Int?,
    @SerialName("weather")
    var weather: List<Weather?>?,
    @SerialName("wind")
    var wind: Wind?
)