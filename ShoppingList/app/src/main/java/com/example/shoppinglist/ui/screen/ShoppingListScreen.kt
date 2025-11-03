package com.example.shoppinglist.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.shoppinglist.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinglist.data.CategoryList
import com.example.shoppinglist.data.ShoppingItem
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen (
    shoppingListViewModel: ShoppingListViewModel = viewModel()
) {
    var shoppingItemEdit: ShoppingItem ? by rememberSaveable { mutableStateOf(null) }
    var showShoppingListDialog by rememberSaveable { mutableStateOf(false) }

    Column (modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            title = {
                Row {
                    // TODO: deprecate the image and just use the logo icon
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Logo Icon",
                        modifier = Modifier.size(36.dp),
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
                    // TODO: Deletes  all items
                }) {
                    Icon(Icons.Filled.Delete, null)
                }
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

        if (shoppingListViewModel.getAllShoppingItems().isEmpty()){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    // Logo
                    LogoIcon()
                    Text(stringResource(R.string.your_list_is_empty))
                    Text(stringResource(R.string.add_items_to_get_started))
                    // TODO: Create custom buttons
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.primary,
                            contentColor = colorScheme.onPrimary
                        )
                    ) {
                        Text(stringResource(R.string.add_item))
                    }
                }
            }
        } else {
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                // TODO: Add Filters and maybe search or sorting
               Card (
                   colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
                   shape = RoundedCornerShape(12.dp),
                   modifier = Modifier.padding(5.dp)
               ){
                   Row (
                       modifier = Modifier.padding(5.dp).fillMaxWidth()
                   ){
                       SearchBar()
                       FilterCategories()
                       SortItems()
                   }
               }

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(shoppingListViewModel.getAllShoppingItems()) { shoppingItem ->
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
    var shoppingItemName by remember {
        mutableStateOf(shoppingItemEdit?.name ?: "")
    }
    var shoppingItemDescription by remember {
        mutableStateOf(shoppingItemEdit?.description ?: "")
    }

    Dialog(onDismissRequest = {
        onCancel()
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    if (shoppingItemEdit == null) "New Shopping List Item" else "Edit Shopping List Item",
                    style = MaterialTheme.typography.titleMedium
                )

                // Name
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Item name") },
                    value = shoppingItemName,
                    onValueChange = { shoppingItemName = it }
                )
                // Description
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Item description") },
                    value = shoppingItemDescription,
                    onValueChange = { shoppingItemDescription = it }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        if (shoppingItemEdit == null) {
                            viewModel.addShoppingListItem(
                                ShoppingItem(
                                    id = "",
                                    category = CategoryList.FOOD, // TODO: Add category select option
                                    estimatedPrice = 4.0f, // TODO: Add pricing (properly)
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
                                estimatedPrice = 4.0f,
                                name = shoppingItemName,
                                updatedDate = Date(System.currentTimeMillis()).toString(),
                            )
                            viewModel.updateShoppingListItem(
                                shoppingItemEdit,
                                editedTodo
                            )
                        }
                        onCancel()
                    }) {
                        Text("Save")
                    }
                }
            }
        }

    }
}

@Composable
private fun SearchBar() {
    Card(
        colors = CardDefaults.cardColors(containerColor = colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(5.dp)
    ){
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                modifier = Modifier.size(24.dp),
            )
            Text(
                "Search items...",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

//Categories
@Composable
private fun FilterCategories() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(5.dp)
    ){
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Categories")
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Filter Icon",
                modifier = Modifier.size(24.dp),
            )

        }
    }
}

//Sort Items
@Composable
private fun SortItems() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(5.dp)
    ){
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Sort")
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Sort Icon",
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Composable
private fun LogoIcon() {
    Box(
        modifier = Modifier
            .size(80.dp) // total size including border
            .border(
                width = 2.dp,
                color = colorScheme.primary,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Logo Icon",
            modifier = Modifier.size(40.dp),
            tint = colorScheme.primary
        )
    }
}

@Composable
fun ShoppingCard(
    shoppingItem: ShoppingItem,
    onItemChecked: (ShoppingItem, Boolean) -> Unit,
    onItemEdit: (ShoppingItem) -> Unit,
    onItemDelete: (ShoppingItem) -> Unit,
){
    Card (
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surface,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier.padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox
            Checkbox(
                checked = shoppingItem.isBought,
                onCheckedChange = { checkboxState ->
                    onItemChecked(shoppingItem, checkboxState)
                }
            )

            // Category
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .border(
                        width = 2.dp,
                        color = colorScheme.primary,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = shoppingItem.category.getIcon()),
                    contentDescription = "Category",
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.fillMaxSize(0.05f))

            // Title & Description
            Column {
                // Title
                Text(
                    shoppingItem.name,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    style = if (shoppingItem.isBought) {
                        TextStyle( textDecoration = TextDecoration.LineThrough)
                    } else {
                        TextStyle(textDecoration = TextDecoration.None)
                    }
                )
                // Description
                Text(
                    shoppingItem.description,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    style = if (shoppingItem.isBought) {
                        TextStyle( textDecoration = TextDecoration.LineThrough)
                    } else {
                        TextStyle(textDecoration = TextDecoration.None)
                    }
                )
            }

            // Delete & Edit
            Spacer(modifier = Modifier.fillMaxSize(0.05f))

            Row {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable {
                        onItemDelete(shoppingItem)
                    },
                    tint = Color.Red
                )
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.clickable {
                        onItemEdit(shoppingItem)
                    },
                    tint = Color.Blue
                )
            }
        }
    }
}