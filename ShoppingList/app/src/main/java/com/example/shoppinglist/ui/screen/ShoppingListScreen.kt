package com.example.shoppinglist.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R

//import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen (
//    todoViewModel: ShoppingListViewModel = viewModel()
) {
    Column (modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            title = {
                Row {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        modifier = Modifier.size(32.dp).padding(end = 8.dp),
                        contentDescription = "Logo",
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
    }
}