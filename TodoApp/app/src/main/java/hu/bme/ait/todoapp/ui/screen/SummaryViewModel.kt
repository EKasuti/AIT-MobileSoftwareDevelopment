package hu.bme.ait.todoapp.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.bme.ait.todoapp.ui.navigation.SummaryScreenRoute


class SummaryViewModel(
    summaryScreenRoute: SummaryScreenRoute
) : ViewModel() {
    var allTodo by mutableStateOf(0)
    var importantTodo by mutableStateOf(0)

    init {
        allTodo = summaryScreenRoute.allTodoNum
        importantTodo = summaryScreenRoute.importantTodoNum
    }


    class Factory(
        private val key: SummaryScreenRoute
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SummaryViewModel(key) as T
        }
    }

}