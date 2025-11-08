package com.example.shoppinglist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.shoppinglist.data.CategoryList
import com.example.shoppinglist.data.ShoppingItem
import com.example.shoppinglist.ui.screen.ShoppingListViewModel
import java.util.*

@Composable
fun ShoppingListDialog(
    viewModel: ShoppingListViewModel,
    shoppingItemEdit: ShoppingItem? = null,
    onCancel: () -> Unit
) {
    var shoppingItemName by remember { mutableStateOf(shoppingItemEdit?.name ?: "") }
    var shoppingItemDescription by remember { mutableStateOf(shoppingItemEdit?.description ?: "") }
    var shoppingItemPrice by remember { mutableStateOf(shoppingItemEdit?.estimatedPrice?.toString() ?: "0.00") }
    var shoppingItemCategory by remember { mutableStateOf(shoppingItemEdit?.category ?: CategoryList.FOOD) }
    var expandedCategory by remember { mutableStateOf(false) }


    // Error states
    var nameError by remember { mutableStateOf<String?>(null) }
    var priceError by remember { mutableStateOf<String?>(null) }

    fun validateInput(): Boolean {
        var valid = true

        // validate name
        if (shoppingItemName.isBlank()) {
            nameError = "Please enter a name"
            valid = false
        } else {
            nameError = null
        }

        // validate price
        try {
            val price = shoppingItemPrice.toFloat()
            if (price < 0) {
                priceError = "Price cannot be negative"
                valid = false
            } else {
                priceError = null
            }
        } catch (_: Exception) {
            priceError = "Please enter a valid number"
            valid = false
        }

        return valid
    }

    Dialog(onDismissRequest = onCancel) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
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
                    onValueChange = {
                        shoppingItemName = it
                    },
                    isError = nameError != null
                )
                if (nameError != null) {
                    Text(nameError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }

                Spacer(Modifier.height(10.dp))

                // Category (Default : Food)
                CategoryDropdownList(
                    expanded = expandedCategory,
                    selectedCategory = shoppingItemCategory,
                    onExpandedChange = { expandedCategory = it },
                    onCategorySelected = { shoppingItemCategory = it }
                )

                Spacer(Modifier.height(10.dp))

                // Description (optional)
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
                        keyboardType = KeyboardType.Decimal
                    ),
                    isError = priceError != null
                )
                if (priceError != null) {
                    Text(priceError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                }

                Spacer(Modifier.height(16.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onCancel) { Text("Cancel") }
                    Spacer(Modifier.width(8.dp))
                    TextButton(onClick = {
                        if (validateInput()) {
                            val newItem = ShoppingItem(
                                category = shoppingItemCategory,
                                estimatedPrice = shoppingItemPrice.toFloatOrNull() ?: 0.0f,
                                name = shoppingItemName,
                                description = shoppingItemDescription,
                                createDate = Date().toString(),
                                updatedDate = Date().toString(),
                                isBought = shoppingItemEdit?.isBought == true
                            )

                            if (shoppingItemEdit == null) {
                                viewModel.addShoppingListItem(newItem)
                            } else {
                                viewModel.updateShoppingListItem(newItem.copy(id = shoppingItemEdit.id))
                            }
                            onCancel()
                        }
                    }) {
                        Text(if (shoppingItemEdit == null) "Add Item" else "Update Item")
                    }
                }
            }
        }
    }
}


@Composable
private fun CategoryDropdownList(
    expanded: Boolean,
    selectedCategory: CategoryList,
    onExpandedChange: (Boolean) -> Unit,
    onCategorySelected: (CategoryList) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Category") },
            value = selectedCategory.name,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.clickable {
                        onExpandedChange(!expanded)
                    }
                )
            },
            enabled = true,
            singleLine = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onExpandedChange(false)
            }
        ) {
            CategoryList.entries.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.name) },
                    onClick = {
                        onCategorySelected(category)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}
