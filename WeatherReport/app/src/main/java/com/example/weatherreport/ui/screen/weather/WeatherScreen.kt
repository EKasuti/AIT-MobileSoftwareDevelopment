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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.weatherreport.R
import com.example.weatherreport.data.WeatherData
import com.example.weatherreport.ui.screen.weather.components.MainWeatherCard
import kotlin.math.roundToInt
import kotlinx.coroutines.delay

@Composable
fun WeatherScreen(
    city: String,
    onBack: () -> Unit,
    viewModel: WeatherViewModel = hiltViewModel()
) {
     LaunchedEffect(city) {
         viewModel.getWeather(city)
     }
    Column {
        when (viewModel.weatherUiState) {
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
            is WeatherUiState.Error -> {
                LaunchedEffect(Unit) {
                    delay(1200)
                    onBack() // we take them back to cities page
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.couldn_t_load_weather),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.you_ll_be_redirected_shortly),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherResultWidget(weatherResults: WeatherData) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainWeatherCard(weatherResults)

        Spacer(modifier = Modifier.height(12.dp))


        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            // Temperature card
            InfoCard(title = stringResource(R.string.temperature_details)) {
                InfoRow(stringResource(R.string.feels_like), "${weatherResults.main?.feelsLike?.roundToInt()}°")
                InfoRow(stringResource(R.string.humidity), "${weatherResults.main?.humidity}%")
                InfoRow(stringResource(R.string.pressure), "${weatherResults.main?.pressure} hPa")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Wind Card
            InfoCard(title = stringResource(R.string.wind)) {
                InfoRow(stringResource(R.string.speed), "${weatherResults.wind?.speed} m/s")
                InfoRow(stringResource(R.string.direction), "${weatherResults.wind?.deg}°")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Atmospheric Card
            InfoCard(title = stringResource(R.string.atmospheric)) {
                InfoRow(stringResource(R.string.visibility), "${(weatherResults.visibility ?: 0) / 1000} km")
                InfoRow(stringResource(R.string.cloudiness), "${weatherResults.clouds?.all}%")
            }
        }
    }
}

@Composable
fun InfoCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            content()
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, fontWeight = FontWeight.SemiBold)
    }
}