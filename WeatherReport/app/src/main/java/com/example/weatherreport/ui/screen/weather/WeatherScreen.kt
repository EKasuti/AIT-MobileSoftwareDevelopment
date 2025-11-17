package com.example.weatherreport.ui.screen.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.weatherreport.ui.components.NebulaAppBar

@Composable
fun WeatherScreen() {
    Column{
        NebulaAppBar(
            screenName = "Budapest",
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = ("Weather Details"),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
