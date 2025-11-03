package com.example.shoppinglist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDAO {

    @Query("SELECT * FROM shoppingListTable")
    fun getAllShoppingItems(): Flow<List<ShoppingItem>>

    @Query("SELECT * from shoppingListTable WHERE id = :id")
    fun getShoppingItem(id: Int): Flow<ShoppingItem>

    // counts how many row we have in the table
    @Query("SELECT COUNT(*) from shoppingListTable")
    suspend fun getItemsNum(): Int


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: ShoppingItem)

    @Update
    suspend fun update(todo: ShoppingItem)

    @Delete
    suspend fun delete(todo: ShoppingItem)

    @Query("DELETE from shoppingListTable")
    suspend fun deleteAllItems()
}