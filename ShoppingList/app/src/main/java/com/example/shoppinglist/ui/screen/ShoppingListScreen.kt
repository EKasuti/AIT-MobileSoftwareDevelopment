package com.example.shoppinglist.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.data.CategoryList
import com.example.shoppinglist.data.ShoppingItem
import com.example.shoppinglist.ui.components.EmptyState
import com.example.shoppinglist.ui.components.FilterOptions
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen (
    shoppingListViewModel: ShoppingListViewModel = hiltViewModel()
) {
    var shoppingItemEdit: ShoppingItem ? by rememberSaveable { mutableStateOf(null) }
    var showShoppingListDialog by rememberSaveable { mutableStateOf(false) }

    var shoppingList = shoppingListViewModel.getAllShoppingItems().collectAsState(emptyList())
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Column (modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            modifier = Modifier.height(64.dp),
            windowInsets = WindowInsets(0.dp),
            title = {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    // TODO: deprecate the image and just use the logo icon
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
                // TODO: onDelete and onFilter implementation
                FilterOptions(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    onDeleteClick = {  },
                    onFilterClick = {  }
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(shoppingList.value) { shoppingItem ->
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

@Composable
fun ShoppingListDialog(
    viewModel: ShoppingListViewModel,
    shoppingItemEdit: ShoppingItem? = null,
    onCancel: () -> Unit
){
    var shoppingItemName by remember { mutableStateOf(shoppingItemEdit?.name ?: "") }
    var shoppingItemDescription by remember { mutableStateOf(shoppingItemEdit?.description ?: "") }
    var shoppingItemPrice by remember { mutableStateOf(shoppingItemEdit?.estimatedPrice?.toString() ?: "0.00") }
    var shoppingItemCategory by remember { mutableStateOf(shoppingItemEdit?.category ?: CategoryList.FOOD) }
    var expandedCategory by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = {
        onCancel()
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    if (shoppingItemEdit == null) "New Shopping List Item" else "Edit Shopping List Item",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(12.dp))

                // Name
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Item name") },
                    value = shoppingItemName,
                    onValueChange = { shoppingItemName = it }
                )

                Spacer(Modifier.height(10.dp))

                // Category Dropdown
                CategoryDropdownList(expandedCategory, shoppingItemCategory)

                Spacer(Modifier.height(10.dp))

                // Description
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Item description") },
                    value = shoppingItemDescription,
                    onValueChange = { shoppingItemDescription = it }
                )

                Spacer(Modifier.height(10.dp))

                // Estimated Price
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Estimated Price ($)") },
                    value = shoppingItemPrice,
                    onValueChange = { shoppingItemPrice = it },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        if (shoppingItemEdit == null) {
                            viewModel.addShoppingListItem(
                                ShoppingItem(
                                    category = CategoryList.FOOD, // TODO: Add category select option
                                    estimatedPrice = shoppingItemPrice.toFloatOrNull() ?: 0.0f,
                                    name = shoppingItemName,
                                    description = shoppingItemDescription,
                                    createDate = Date(System.currentTimeMillis()).toString(),
                                    updatedDate = Date(System.currentTimeMillis()).toString(),
                                    isBought = false
                                )
                            )
                        } else {
                            val editedTodo = shoppingItemEdit.copy(
                                category = CategoryList.FOOD,
                                estimatedPrice = shoppingItemPrice.toFloatOrNull() ?: 0.0f,
                                name = shoppingItemName,
                                description = shoppingItemDescription,
                                updatedDate = Date(System.currentTimeMillis()).toString(),
                            )
                            viewModel.updateShoppingListItem(
                                editedTodo
                            )
                        }
                        onCancel()
                    }) {
                        Text("Add Item")
                    }
                }
            }
        }

    }
}

@Composable
private fun CategoryDropdownList(
    expandedCategory: Boolean,
    shoppingItemCategory: CategoryList
) {
    var expandedCategory1 = expandedCategory
    var shoppingItemCategory1 = shoppingItemCategory
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expandedCategory1 = !expandedCategory1 },
            label = { Text("Category") },
            value = shoppingItemCategory1.name,
            onValueChange = { },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = if (expandedCategory1) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown"
                )
            }
        )

        DropdownMenu(
            expanded = expandedCategory1,
            onDismissRequest = { expandedCategory1 = false }
        ) {
            CategoryList.entries.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = {
                        shoppingItemCategory1 = category
                        expandedCategory1 = false
                    }
                )
            }
        }
    }
}

@Composable
fun ShoppingCard(
    shoppingItem: ShoppingItem,
    onItemChecked: (ShoppingItem, Boolean) -> Unit,
    onItemEdit: (ShoppingItem) -> Unit,
    onItemDelete: (ShoppingItem) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(Modifier.padding(12.dp)) {
            // Top row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = shoppingItem.isBought,
                        onCheckedChange = { checked -> onItemChecked(shoppingItem, checked) }
                    )
                    Image(
                        painter = painterResource(id = shoppingItem.category.getIcon()),
                        contentDescription = "Item Icon",
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = shoppingItem.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Green, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = shoppingItem.category.name,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Green
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "$${shoppingItem.estimatedPrice}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand or collapse",
                        tint = colorScheme.onSurface
                    )
                }
            }

            if (expanded) {
                Spacer(Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = shoppingItem.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = colorScheme.onSurfaceVariant
                    )

                    Row {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red,
                            modifier = Modifier
                                .clickable { showDeleteConfirmation = true }
                                .padding(4.dp)
                        )
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit",
                            tint = Color.Blue,
                            modifier = Modifier
                                .clickable { onItemEdit(shoppingItem) }
                                .padding(4.dp)
                        )
                    }
                }
            }

            if (showDeleteConfirmation){
                AlertDialog(
                    onDismissRequest = {showDeleteConfirmation = false},
                    title = { Text("Delete Item")},
                    text = { Text("Are you sure you want to delete \"${shoppingItem.name}\"? This action cannot be undone.") },
                    confirmButton = {
                        TextButton(onClick = {
                            showDeleteConfirmation = false
                            onItemDelete(shoppingItem)
                        }) {
                            Text("Delete", color = Color.Red)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDeleteConfirmation = false}
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}