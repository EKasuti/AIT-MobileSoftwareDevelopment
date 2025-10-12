package com.example.minesweeper.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.minesweeper.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onStartGame: (Level) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.welcome_message),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Select Difficulty:", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        DifficultyButton("Easy", Level.EASY, onStartGame)
        DifficultyButton("Medium", Level.MEDIUM, onStartGame)
        DifficultyButton("Hard", Level.HARD, onStartGame)
    }
}

@Composable
private fun DifficultyButton(
    text: String,
    level: Level,
    onStartGame: (Level) -> Unit
) {
    Button(
        onClick = { onStartGame(level) },
        modifier = Modifier.width(200.dp)
    ) {
        Text(text)
    }
}
