package com.example.weatherreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.rememberSceneSetupNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.weatherreport.ui.navigation.CitiesScreenRoute
import com.example.weatherreport.ui.navigation.WeatherScreenRoute
import com.example.weatherreport.ui.screen.cities.CitiesScreen
import com.example.weatherreport.ui.screen.home.SplashScreen
import com.example.weatherreport.ui.screen.weather.WeatherScreen
import com.example.weatherreport.ui.theme.WeatherReportTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()

        setContent {
            WeatherReportTheme {
                var showSplash by remember { mutableStateOf(true) }

                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.dark(
                        MaterialTheme.colorScheme.primary.toArgb(),
                    ),
                )

                LaunchedEffect(Unit) {
                    delay(3000)
                    showSplash = false
                }

                if (showSplash) {
                    SplashScreen()
                } else {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.background
                    ) { innerPadding ->
                        NavGraph(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun NavGraph(modifier: Modifier) {
    val backStack = rememberNavBackStack(CitiesScreenRoute)

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = {backStack.removeLastOrNull()},
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider  = entryProvider {
            entry<CitiesScreenRoute> {
                CitiesScreen(
                    onWeatherScreen = { city ->
                        backStack.add(WeatherScreenRoute(city = city))
                    }
                )
            }
            entry<WeatherScreenRoute> { route ->
                WeatherScreen(
                    city = route.city,
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}