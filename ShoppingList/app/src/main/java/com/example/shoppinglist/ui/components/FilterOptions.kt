package com.example.shoppinglist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.shoppinglist.data.CategoryList

@Composable
fun FilterOptions(
    value: String,
    onValueChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    selectedCategory: CategoryList?,
    onCategorySelected: (CategoryList?) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteAllConfirmation by remember { mutableStateOf(false) }
    var showFilterDialog by rememberSaveable { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Custom TextField
            Surface(
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, colorScheme.outlineVariant),
                color = colorScheme.surface,
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = colorScheme.onSurface
                        ),
                        decorationBox = { innerTextField ->
                            if (value.isEmpty()) {
                                Text(
                                    text = "Search items...",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorScheme.onSurfaceVariant
                                )
                            }
                            innerTextField()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }

            Spacer(Modifier.width(4.dp))

            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",
                tint = colorScheme.onSurface,
                modifier = Modifier
                    .clickable { showDeleteAllConfirmation = true }
                    .padding(4.dp)
            )

            Icon(
                imageVector = Icons.Default.FilterAlt,
                contentDescription = "Filter",
                tint = colorScheme.onSurface,
                modifier = Modifier
                    .clickable { showFilterDialog = true }
                    .padding(4.dp)
            )
        }

        if (showFilterDialog){
            AlertDialog(
                onDismissRequest = { showFilterDialog = false},
                title = { Text("Filter by Category")},
                text = {
                    Column {
                        Text(
                            "All",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onCategorySelected(null)
                                    showFilterDialog = false
                                }
                                .background(
                                    if (selectedCategory == null)
                                        colorScheme.primary else Color.Transparent,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(vertical = 8.dp, horizontal = 12.dp),
                            color = if (selectedCategory == null) Color.White else colorScheme.onSurface
                        )
                        CategoryList.entries.forEach { category ->
                            Text(
                                text = category.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onCategorySelected(category)
                                        showFilterDialog = false
                                    }
                                    .background(
                                        if (selectedCategory == category)
                                            colorScheme.primary else Color.Transparent,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    .padding(vertical = 8.dp, horizontal = 12.dp),
                                color = if (selectedCategory == category) Color.White else colorScheme.onSurface
                            )
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showFilterDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }

        if (showDeleteAllConfirmation) {
            AlertDialog(
                onDismissRequest = {showDeleteAllConfirmation = false},
                title = { Text("Delete Item")},
                text = { Text("Are you sure you want to delete all items? This action cannot be undone.") },
                confirmButton = {
                    TextButton(onClick = {
                        showDeleteAllConfirmation = false
                        onDeleteClick()
                    }) {
                        Text("Delete", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDeleteAllConfirmation = false}
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}