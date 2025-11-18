package com.example.weatherreport.network

import com.example.weatherreport.data.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.openweathermap.org/data/2.5/weather?q=Budapest,hu&units=metric&appid=f3d694bc3e1d44c1ed5a97bd1120e8fe

// Host: https://api.openweathermap.org
// Path: data/2.5/weather
// Query params:
//      q=Budapest,hu
//      units=metric
//      appid=f3d694bc3e1d44c1ed5a97bd1120e8fe

interface WeatherAPI {

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): WeatherData
}