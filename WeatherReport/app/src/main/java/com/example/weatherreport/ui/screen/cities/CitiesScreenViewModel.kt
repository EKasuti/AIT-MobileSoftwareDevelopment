package com.example.weatherreport.ui.screen.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherreport.data.CityDAO
import com.example.weatherreport.data.CityItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor(val cityDAO: CityDAO) : ViewModel() {
    // Add city
    fun addCity(cityItem: CityItem) {
        viewModelScope.launch{
            cityDAO.insert(cityItem)
        }
    }

    // fetch all cities
    fun getAllCities(): Flow<List<CityItem>> {
        return cityDAO.getAllCities()
    }

    // delete a city
    fun removeCity(cityItem: CityItem){
        viewModelScope.launch {
            cityDAO.delete(cityItem)
        }
    }
}