package com.example.weatherreport.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen (
    onGetStarted: () -> Unit
){
    Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = ("HomeScreen"),
            style = MaterialTheme.typography.headlineMedium,

        )

        Button(
            onClick = { onGetStarted() },
            modifier = Modifier.width(200.dp)
        ) {
            Text(
                text = "Get Started",
                color = Color.White
            )
        }
    }
}