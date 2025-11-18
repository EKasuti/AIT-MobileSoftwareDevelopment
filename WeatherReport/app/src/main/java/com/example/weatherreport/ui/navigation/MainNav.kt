package com.example.weatherreport.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
data object HomeScreenRoute: NavKey

@Serializable
data object CitiesScreenRoute: NavKey

@Serializable
data class WeatherScreenRoute(val city: String): NavKey