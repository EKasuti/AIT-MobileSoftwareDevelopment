package com.example.todoapp

import com.example.todoapp.ui.navigation.SummaryScreenRoute
import com.example.todoapp.ui.navigation.TodoScreenRoute
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.rememberSceneSetupNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.todoapp.ui.screen.TodoScreen
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.ui.screen.SummaryScreen
import com.example.todoapp.ui.screen.SummaryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
    val backStack = rememberNavBackStack(TodoScreenRoute)

    NavDisplay(
        //modifier = modifier,
        backStack = backStack,
        onBack = {backStack.removeLastOrNull()},
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider  = entryProvider {
            entry<TodoScreenRoute> {
                TodoScreen(
                    onSummaryClick = {
                            allTodo, importantTodo ->
                        backStack.add(SummaryScreenRoute(
                            allTodo, importantTodo))
                    }
                )
            }
            entry<SummaryScreenRoute> {
                SummaryScreen(
                    summaryViewModel = viewModel(
                        factory = SummaryViewModel.Factory(it)
                    )
                )
            }
        }
    )
}