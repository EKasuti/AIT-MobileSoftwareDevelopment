package com.example.weatherreport.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherreport.R
import com.example.weatherreport.data.CityItem

@Composable
fun CityCard(
    cityItem: CityItem,
    onCityClick: (String) -> Unit,
    onCityDelete: (CityItem) -> Unit,
    onChangeStatus: (CityItem, Boolean) -> Unit,
) {
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable { onCityClick(cityItem.name) }
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ){
                Icon(
                    imageVector = if (cityItem.isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = stringResource(R.string.favorite),
                    tint = if (cityItem.isFavorite) Color(0xFFFFC107) else Color.Gray,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .clickable {
                            onChangeStatus(cityItem, !cityItem.isFavorite)
                        }
                        .padding(4.dp)
                )

                Column(
                    modifier = Modifier.padding(start = 12.dp)
                ){
                    Text(
                        text = cityItem.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (cityItem.description.isNotBlank()) {
                        Text(
                            text = cityItem.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(R.string.delete),
                tint = Color.Red,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clickable { showDeleteConfirmation = true }
                    .padding(4.dp)
            )
        }
    }

    if (showDeleteConfirmation){
        AlertDialog(
            onDismissRequest = {showDeleteConfirmation = false},
            title = { Text(stringResource(R.string.delete_city))},
            text = { Text(stringResource(R.string.delete_item_dialog_description, cityItem.name)) },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteConfirmation = false
                    onCityDelete(cityItem)
                }) {
                    Text(stringResource(R.string.delete), color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteConfirmation = false}
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}