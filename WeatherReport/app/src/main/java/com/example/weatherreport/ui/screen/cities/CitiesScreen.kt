package com.example.weatherreport.ui.screen.cities

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherreport.ui.components.NebulaAppBar

@Composable
fun CitiesScreen(
    onWeatherScreen: () -> Unit
) {
    Column{
        NebulaAppBar(
            screenName = "Cities Screen",
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp)
        ) {
            Text(
                text = ("City List"),
                style = MaterialTheme.typography.headlineMedium
            )
            // TODO: Small description
            Text(
                text = ("Small Description"),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { onWeatherScreen() },
                modifier = Modifier.width(200.dp)
            ) {
                Text("Weather details")
            }
        }
    }
}
