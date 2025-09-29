package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.pointer.pointerInput
import com.example.tictactoe.ui.theme.TicTacToeTheme
import hu.bme.ait.tictactoe.ui.screen.TicTacToeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TicTacToeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TicTacToeScreenOld(modifier: Modifier) {
    var circleX by rememberSaveable { mutableStateOf(0f) }
    var circleY by rememberSaveable { mutableStateOf(0f) }

    var points by rememberSaveable {
        mutableStateOf<List<Offset>>(emptyList()) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(modifier = Modifier
            .fillMaxWidth(0.8f)
            .aspectRatio(1f)
            .pointerInput(key1 = Unit) {
                detectTapGestures {
                        offset ->
                    Log.d("TAG_CLICK_POS", "${offset.x} ${offset.y}")
                    circleX = offset.x
                    circleY = offset.y

                    // append the new coordinate to the list
                    points = points + offset
                }
            }
        )
        {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawRect(
                color = Color.Red,
                size = size
            )

            drawCircle(
                color = Color.Blue,
                radius = 100f,
                style = Fill,
                center = Offset(circleX,
                    if (circleY > canvasHeight) canvasHeight else circleY)
            )

            points.forEach {
                drawCircle(
                    Color.Cyan,
                    radius = 100f,
                    style = Fill,
                    center = it
                )
            }

        }
    }
}