package com.example.shoppinglist.data
import com.example.shoppinglist.R

data class ShoppingItem(
    val id: String,
    val category: CategoryList,
    val name: String,
    val description: String,
    val estimatedPrice: Float,
    val isBought: Boolean,
    val createDate:String,
    val updatedDate: String,
)

enum class CategoryList {
    FOOD, ELECTRONIC,BOOK;

    fun getIcon(): Int {
        return if (this == FOOD){
            R.drawable.food
        } else if (this == ELECTRONIC){
            // TODO: Get electronics image
            R.drawable.food
        } else if (this == BOOK){
            // TODO: get food image
            R.drawable.food
        }else {
            // TODO: Default image
            R.drawable.food
        }
    }
}