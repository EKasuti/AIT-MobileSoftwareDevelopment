package com.example.weatherreport.ui.screen.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.weatherreport.data.WeatherData
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(
    city: String,
    viewModel: WeatherViewModel = hiltViewModel()
) {
     LaunchedEffect(city) {
         viewModel.getWeather(city)
     }
    Column {
        when (viewModel.weatherUiState) {
            WeatherUiState.Error -> Text(text = "Error loading weather data")
            WeatherUiState.Init,
            WeatherUiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
            is WeatherUiState.Success -> WeatherResultWidget(
                (viewModel.weatherUiState as WeatherUiState.Success).weatherResults
            )
        }
    }
}

@Composable
fun WeatherResultWidget(weatherResults: WeatherData) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = 32.dp,
                bottomEnd = 32.dp
            )
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Location
                Text(
                    text = weatherResults.name.toString(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )

                // Temperature - Large display
                Text(
                    text = "${weatherResults.main?.temp?.roundToInt()}°",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 96.sp,
                        fontWeight = FontWeight.ExtraBold,
                    ),
                    color = Color.White
                )

                // Weather description with icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = getWeatherEmoji(weatherResults.weather?.firstOrNull()?.main ?: "Clear"),
                        fontSize = 24.sp,
                        color = Color.White
                    )
                    Text(
                        text = " ${weatherResults.weather?.firstOrNull()?.description ?: ""}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // High / Low temperatures
                Text(
                    text = "H:${weatherResults.main?.tempMax?.roundToInt()}° L:${weatherResults.main?.tempMin?.roundToInt()}°",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

            }
        }
    }
}


fun getWeatherEmoji(weather: String): String {
    return when (weather.lowercase()) {
        "clear" -> "☀️"
        "clouds" -> "☁️"
        "rain" -> "🌧️"
        "drizzle" -> "🌦️"
        "snow" -> "❄️"
        else -> "🌤️"
    }
}