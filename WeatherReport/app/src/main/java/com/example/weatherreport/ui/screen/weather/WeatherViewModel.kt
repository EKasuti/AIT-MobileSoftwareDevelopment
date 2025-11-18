package com.example.weatherreport.ui.screen.weather


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherreport.data.WeatherData
import com.example.weatherreport.network.WeatherAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface WeatherUiState {
    object Init : WeatherUiState
    object Loading : WeatherUiState
    data class Success(val weatherResults: WeatherData) : WeatherUiState
    object Error : WeatherUiState
}

@HiltViewModel
class WeatherViewModel @Inject constructor(val weatherAPI: WeatherAPI) : ViewModel() {
    var weatherUiState: WeatherUiState by mutableStateOf(WeatherUiState.Init)

    fun getWeather(city: String){
        weatherUiState = WeatherUiState.Loading

        Log.d("DEBUG - WeatherViewModel", "Fetching weather for: $city")

        // launch coroutine for network communication
        viewModelScope.launch {
            try {
                val result = weatherAPI.getWeather(
                    city = city,
                    units = "metric",
                    apiKey = "f3d694bc3e1d44c1ed5a97bd1120e8fe"
                )
                Log.d("DEBUG - WeatherViewModel", "Success: $result")
                weatherUiState = WeatherUiState.Success(result)
            } catch (e: Exception) {
                Log.e("DEBUG - WeatherViewModel", "Error: ${e.message}", e)
                weatherUiState = WeatherUiState.Error
            }
        }
    }
}