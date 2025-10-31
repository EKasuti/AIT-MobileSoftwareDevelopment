package com.example.shoppinglist.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import androidx.lifecycle.viewmodel.compose.viewModel

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
                        tint = colorResource(R.color.white)
                    )
                    Text("Papyrus")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorResource(R.color.primary),
                titleContentColor = colorResource(R.color.white),
                actionIconContentColor = colorResource(R.color.white),
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
                    //Text: Your list is empty
                    Text("Your List is Empty")
                    // Text: Add items to get started
                    Text("Add items to get started")
                    // + Add Item button
                    // TODO: Create custom buttons
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.primary),
                            contentColor = colorResource(R.color.white)
                        )
                    ) {
                        Text("+ Add Item")
                    }
                }
            }
        } else {
            Text("Not empty")
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
                color = colorResource(R.color.primary),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Logo Icon",
            modifier = Modifier.size(40.dp),
            tint = colorResource(R.color.primary)
        )
    }
}