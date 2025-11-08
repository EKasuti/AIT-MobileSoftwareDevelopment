package com.example.shoppinglist.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.data.ShoppingItem
import com.example.shoppinglist.ui.components.EmptyState
import com.example.shoppinglist.ui.components.FilterOptions
import com.example.shoppinglist.ui.components.ShoppingCard
import com.example.shoppinglist.ui.components.ShoppingListDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen (
    shoppingListViewModel: ShoppingListViewModel = hiltViewModel()
) {
    var shoppingItemEdit: ShoppingItem ? by rememberSaveable { mutableStateOf(null) }
    var showShoppingListDialog by rememberSaveable { mutableStateOf(false) }

    var shoppingList = shoppingListViewModel.getAllShoppingItems().collectAsState(emptyList())
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val filteredList = shoppingList.value.filter { item ->
        item.name.contains(searchQuery, ignoreCase = true)
    }
    Column (modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            modifier = Modifier.height(64.dp),
            windowInsets = WindowInsets(0.dp),
            title = {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Logo Icon",
                        modifier = Modifier.size(32.dp),
                        tint = colorScheme.onPrimary
                    )
                    Text(stringResource(R.string.papyrus))
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorScheme.primary,
                titleContentColor = colorScheme.onPrimary,
                actionIconContentColor = colorScheme.onPrimary,
            ),
            actions = {
                IconButton(onClick = {
                    showShoppingListDialog = true
                }) {
                    Icon(Icons.Filled.AddCircle, null)
                }
            }
        )

        if (showShoppingListDialog) {
            ShoppingListDialog(
                shoppingListViewModel,
                shoppingItemEdit,
                onCancel = {
                    showShoppingListDialog = false
                    shoppingItemEdit = null
                }
            )
        }

        if (shoppingList.value.isEmpty()){
            EmptyState(
                onAddItemClick = { showShoppingListDialog = true}
            )
        } else {
            Column {
                // TODO: onFilter implementation
                FilterOptions(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    onDeleteClick = { shoppingListViewModel.removeAllShoppingItems() },
                    onFilterClick = {  }
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredList) { shoppingItem ->
                        ShoppingCard(
                            shoppingItem,
                            onItemChecked = { shoppingItem, checked ->
                                shoppingListViewModel.changeShoppingItemState(shoppingItem, checked)
                            },
                            onItemDelete = { shoppingItem -> shoppingListViewModel.removeShoppingItem(shoppingItem) },
                            onItemEdit = { selectedItem ->
                                shoppingItemEdit = selectedItem
                                showShoppingListDialog = true
                            },
                        )
                    }
                }
            }
        }
    }
}
