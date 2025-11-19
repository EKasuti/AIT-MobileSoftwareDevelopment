package com.example.weatherreport.ui.screen.cities

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherreport.R
import com.example.weatherreport.data.CityItem
import com.example.weatherreport.ui.components.CityCard
import com.example.weatherreport.ui.components.EmptyState
import com.example.weatherreport.ui.components.NebulaAppBar
import com.example.weatherreport.ui.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesScreen(
    onWeatherScreen: (String) -> Unit,
    cityViewModel: CityListViewModel = hiltViewModel()
) {
    var showAddDialog by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val cityList = cityViewModel.getAllCities().collectAsState(emptyList())

    val filteredList = cityList.value
        .filter { item ->
            item.name.contains(searchQuery, ignoreCase = true)
        }
        .sortedWith(
            compareByDescending<CityItem> { it.isFavorite }
                .thenBy { it.name.lowercase() }
        )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            NebulaAppBar(screenName = stringResource(R.string.city_list))

            Column(
                modifier = Modifier.padding(10.dp)
            ) {

                if (cityList.value.isEmpty()) {
                    EmptyState()
                } else {
                    Column {
                        // Search Bar
                        SearchBar(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { searchQuery = it }
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(filteredList) { cityItem ->
                                CityCard(
                                    cityItem = cityItem,
                                    onCityClick = { cityName ->
                                        onWeatherScreen(cityName)
                                    },
                                    onCityDelete = { cityItem -> cityViewModel.removeCity(cityItem) },
                                    onChangeStatus = { cityItem, checked ->
                                        cityViewModel.changeIsFavoriteState(cityItem, checked)
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }

        // Floating action button to add cities
        FloatingActionButton(
            onClick = { showAddDialog = true },
            containerColor = colorScheme.primary,
            contentColor = Color.White,

            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_city)
            )
        }
    }

    if (showAddDialog) {
        AddCityDialog(
            onDismiss = { showAddDialog = false },
            onAddCity = { cityName, cityDescription ->
                cityViewModel.addCity(
                    CityItem(
                        name = cityName,
                        description = cityDescription,
                        isFavorite = false
                    )
                )
                showAddDialog = false
            }
        )
    }
}

@Composable
fun AddCityDialog(
    onDismiss: () -> Unit,
    onAddCity: (String, String) -> Unit
) {
    var cityName by rememberSaveable { mutableStateOf("") }
    var cityDescription by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    if (cityName.isNotBlank()) {
                        onAddCity(cityName.trim(), cityDescription.trim())
                    }
                }
            ) {
                Text(stringResource(R.string.add))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
        title = {
            Text(stringResource(R.string.add_city))
        },
        text = {
            Column {
                OutlinedTextField(
                    value = cityName,
                    onValueChange = { cityName = it },
                    label = { Text(stringResource(R.string.city_name)) },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = cityDescription,
                    onValueChange = { cityDescription = it },
                    label = { Text(stringResource(R.string.city_description)) },
                    singleLine = false,
                    maxLines = 3
                )
            }
        },
    )
}

