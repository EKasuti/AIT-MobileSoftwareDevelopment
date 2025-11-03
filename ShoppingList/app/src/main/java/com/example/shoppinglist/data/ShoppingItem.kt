package com.example.shoppinglist.data
import com.example.shoppinglist.R

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "shoppingListTable")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name= "estimatedPrice") val estimatedPrice: Float,
    @ColumnInfo(name = "category") var category: CategoryList,
    @ColumnInfo(name = "isBought") var isBought: Boolean,
    @ColumnInfo(name = "createDate") val createDate:String,
    @ColumnInfo(name = "updatedDate") val updatedDate:String,
)

enum class CategoryList {
    FOOD, ELECTRONIC,BOOK;

    fun getIcon(): Int {
        return if (this == FOOD){
            R.drawable.food
        } else if (this == ELECTRONIC){
            R.drawable.electronics
        } else if (this == BOOK){
            R.drawable.books
        }else {
            // TODO: Default image
            R.drawable.food
        }
    }
}