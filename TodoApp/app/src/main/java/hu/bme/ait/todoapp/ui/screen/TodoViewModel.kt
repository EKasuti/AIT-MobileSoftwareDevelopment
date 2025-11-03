package hu.bme.ait.todoapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.ait.todoapp.data.TodoDAO
import hu.bme.ait.todoapp.data.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(val todoDAO: TodoDAO) : ViewModel() {

    suspend fun getAllTodoNum() : Int {
        return todoDAO.getTodosNum()
    }

    suspend fun getImportantTodoNum() : Int {
        return todoDAO.getImportantTodosNum()
    }

    fun getAllToDoList(): Flow<List<TodoItem>> {
        return todoDAO.getAllTodos()
    }

    fun addTodoList(todoItem: TodoItem) {
        viewModelScope.launch {
            todoDAO.insert(todoItem)
        }
    }

    fun removeTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            todoDAO.delete(todoItem)
        }
    }

    fun removeAllTodos() {
        viewModelScope.launch {
            todoDAO.deleteAllTodos()
        }
    }

    fun updateTodo(newTodoItem: TodoItem) {
        viewModelScope.launch {
            todoDAO.update(newTodoItem)
        }
    }

    fun changeTodoState(todoItem: TodoItem, value: Boolean) {
        val changedTodo = todoItem.copy()
        changedTodo.isDone = value
        viewModelScope.launch {
            todoDAO.update(todoItem)
        }
    }
}