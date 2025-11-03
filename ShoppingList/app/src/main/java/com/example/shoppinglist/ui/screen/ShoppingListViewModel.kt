package com.example.shoppinglist.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.CategoryList
import com.example.shoppinglist.data.ShoppingItem

class ShoppingListViewModel: ViewModel() {
    private var _shoppingList=
        mutableStateListOf<ShoppingItem>()

    init {
        _shoppingList.add(
            ShoppingItem(
                "0",
                CategoryList.FOOD,
                "Apples",
                "Red apples",
                4.0F,
                isBought = false,
                "2025. 10. 09.",
                "2025. 10. 09."
            ),
        )
        _shoppingList.add(
            ShoppingItem(
                "1",
                CategoryList.ELECTRONIC,
                "Phone",
                "Nothing phone",
                4.0F,
                isBought = false,
                "2025. 10. 09.",
                "2025. 10. 09."
            )
        )
        _shoppingList.add(
            ShoppingItem(
                "2",
                CategoryList.BOOK,
                "The boy who harness the wind",
                "Course book",
                4.0F,
                isBought = false,
                "2025. 10. 09.",
                "2025. 10. 09."
            )
        )
    }

    fun addShoppingListItem(shoppingItem: ShoppingItem) {
        _shoppingList.add(shoppingItem)
    }

    fun updateShoppingListItem(originalShoppingItem: ShoppingItem, newShoppingItem: ShoppingItem) {
        val index = _shoppingList.indexOf(originalShoppingItem)
        _shoppingList[index] = newShoppingItem
    }


    fun changeShoppingItemState(shoppingItem: ShoppingItem, value: Boolean){
        val index = _shoppingList.indexOf(shoppingItem)

        val newShoppingItem= shoppingItem.copy(
            name = shoppingItem.name,
            description = shoppingItem.description,
            estimatedPrice = shoppingItem.estimatedPrice,
            createDate = shoppingItem.createDate,
            updatedDate = shoppingItem.updatedDate,
            category = shoppingItem.category,
            isBought = value,
        )

        _shoppingList[index] = newShoppingItem
    }

    fun removeShoppingItem(shoppingItem: ShoppingItem){
        _shoppingList.remove(shoppingItem)

    }

    fun getAllShoppingItems():List<ShoppingItem> {
        return _shoppingList
    }
}