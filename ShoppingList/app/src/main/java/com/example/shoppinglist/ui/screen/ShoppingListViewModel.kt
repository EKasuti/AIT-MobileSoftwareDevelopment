package com.example.shoppinglist.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShoppingItem

class ShoppingListViewModel: ViewModel() {
    private var _shoppingList=
        mutableStateListOf<ShoppingItem>()

    fun getAllShoppingItems():List<ShoppingItem> {
        return _shoppingList
    }
}