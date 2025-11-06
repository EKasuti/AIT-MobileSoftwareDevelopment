package com.example.shoppinglist.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShoppingDAO
import com.example.shoppinglist.data.ShoppingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(val shoppingDAO: ShoppingDAO) : ViewModel() {
    fun addShoppingListItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch{
            shoppingDAO.insert(shoppingItem)
        }
    }

    fun updateShoppingListItem(newShoppingItem: ShoppingItem) {
        viewModelScope.launch {
            shoppingDAO.update(newShoppingItem)
        }
    }

    fun changeShoppingItemState(shoppingItem: ShoppingItem, value: Boolean){
        val changedTodo = shoppingItem.copy()
        changedTodo.isBought = value
        viewModelScope.launch {
            shoppingDAO.update(shoppingItem)
        }
    }

    fun removeShoppingItem(shoppingItem: ShoppingItem){
        viewModelScope.launch {
            shoppingDAO.delete(shoppingItem)
        }
    }

    fun removeAllShoppingItems() {
        viewModelScope.launch {
            shoppingDAO.deleteAllItems()
        }
    }

    fun getAllShoppingItems(): Flow<List<ShoppingItem>> {
        return shoppingDAO.getAllShoppingItems()
    }
}