package com.example.shoppinglist.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinglist.data.ShoppingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen (
    shoppingListViewModel: ShoppingListViewModel = viewModel()
) {
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
                    // TODO: Module to add an item
                }) {
                    Icon(Icons.Filled.AddCircle, null)
                }
            }
        )

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
                            onShoppingItemChecked = { shoppingItem, checked ->
                                shoppingListViewModel.changeShoppingItemState(shoppingItem, checked)
                            },
                            onItemDelete = { shoppingItem -> shoppingListViewModel.removeShoppingItem(shoppingItem) },
                        )
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
    onShoppingItemChecked: (ShoppingItem, Boolean) -> Unit,
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
                    onShoppingItemChecked(shoppingItem, checkboxState)
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
                    tint = Color.Blue
                )
            }
        }
    }
}