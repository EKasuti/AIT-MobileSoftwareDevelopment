package com.example.weatherreport

import android.graphics.Color
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.rememberSceneSetupNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.weatherreport.ui.navigation.CitiesScreenRoute
import com.example.weatherreport.ui.navigation.HomeScreenRoute
import com.example.weatherreport.ui.navigation.WeatherScreenRoute
import com.example.weatherreport.ui.screen.cities.CitiesScreen
import com.example.weatherreport.ui.screen.home.HomeScreen
import com.example.weatherreport.ui.screen.weather.WeatherScreen
import com.example.weatherreport.ui.theme.WeatherReportTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()

        setContent {
            WeatherReportTheme {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.dark(
                        MaterialTheme.colorScheme.primary.toArgb(),
                    ),
                    navigationBarStyle = SystemBarStyle.dark(
                        Color.TRANSPARENT
                    )
                )

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


@Composable
fun NavGraph(modifier: Modifier) {
    val backStack = rememberNavBackStack(HomeScreenRoute)

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
            entry<HomeScreenRoute> {
                HomeScreen(
                    onGetStarted = {backStack.add(CitiesScreenRoute)}
                )
            }
            entry<CitiesScreenRoute> {
                CitiesScreen(
                    onWeatherScreen = { city ->
                        backStack.add(WeatherScreenRoute(city = city))
                    }
                )
            }
            entry<WeatherScreenRoute> { route ->
                WeatherScreen(city = route.city)
            }
        }
    )
}