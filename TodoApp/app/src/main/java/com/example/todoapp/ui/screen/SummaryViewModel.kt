package com.example.todoapp.ui.screen

import com.example.todoapp.ui.navigation.SummaryScreenRoute
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SummaryViewModel(
    summaryScreenRoute: SummaryScreenRoute
) : ViewModel() {
    var allTodo by mutableIntStateOf(0)
    var importantTodo by mutableIntStateOf(0)

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